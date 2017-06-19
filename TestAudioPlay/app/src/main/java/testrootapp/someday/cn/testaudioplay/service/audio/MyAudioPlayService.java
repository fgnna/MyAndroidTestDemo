package testrootapp.someday.cn.testaudioplay.service.audio;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import testrootapp.someday.cn.testaudioplay.BuildConfig;

/**
 * 音频播放服务
 * Created by je on 17-6-12.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MyAudioPlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {
    private MyAudioPlayServiceBinder mBinder;

    private LinkedList<Music> mMediaPlayerList = new LinkedList<>();
    private PlayingListener mPlayingListener;
    private Timer mLoopTimer;
    private TimerTask mLoopTimerTask;

    private MediaPlayer mCurrentMediaPlayer;

    /**
     * APP内公开的音频播放接口
     */
    class MyAudioPlayServiceBinder extends Binder
    {
        public void addMuics(String id, String name, String path)
        {
            MediaPlayer mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(path);
            } catch (IOException e) {
                if(null != mPlayingListener)
                    mPlayingListener.onMusicError();
            }
            mediaPlayer.setOnCompletionListener(MyAudioPlayService.this);
            mediaPlayer.setOnPreparedListener(MyAudioPlayService.this);
            mediaPlayer.setOnErrorListener(MyAudioPlayService.this);
            mediaPlayer.setOnBufferingUpdateListener(MyAudioPlayService.this);
            Music music = new Music(id,name,path);
            music.setMediaPlayer(mediaPlayer);
            mMediaPlayerList.addLast(music);
        }

        public void removePlayingListener()
        {
            mPlayingListener = null;
        }

        public void setPlayingListener(PlayingListener playingListener )
        {
            mPlayingListener = playingListener;
            if(null != mCurrentMediaPlayer && 0 != mMediaPlayerList.size())
            {
                mPlayingListener.onPlayingMusic(mMediaPlayerList.getFirst());
            }
        }

        public void play()
        {
            if(null != mCurrentMediaPlayer)
            {
                if(mCurrentMediaPlayer.isPlaying())
                    stop();
                else
                    mCurrentMediaPlayer.prepareAsync();

            }
            else if(0 != mMediaPlayerList.size())
            {
                if(mMediaPlayerList.getFirst().getMediaPlayer().isPlaying())
                    stop();
                else
                {
                    try
                    {
                        mMediaPlayerList.getFirst().getMediaPlayer().prepareAsync();
                        if(null != mPlayingListener)
                        {
                            mPlayingListener.onPlayingMusic(mMediaPlayerList.getFirst());
                        }
                    }
                    catch (Exception e)
                    {
                        //准备过程中重复调用会出异常
                    }

                }
            }
        }

        public void stop()
        {
            if(null != mCurrentMediaPlayer)
                mCurrentMediaPlayer.stop();
            endLoop();
            if(null != mPlayingListener)
                mPlayingListener.onMusicStop();
        }

        /**
         * 停止播放及清空播放列表
         */
        public void cannel()
        {
            if(null != mCurrentMediaPlayer)
            {
                mCurrentMediaPlayer.stop();
                mCurrentMediaPlayer.release();
                mCurrentMediaPlayer = null;
            }
            if(0 != mMediaPlayerList.size())
            {
                try {
                    mMediaPlayerList.getFirst().getMediaPlayer().stop();
                    mMediaPlayerList.getFirst().getMediaPlayer().release();
                }catch (Exception e)
                {
                    //以防在异步准备，但还没完成准备阶段时调用stop()引发异常
                }
            }

            mMediaPlayerList.clear();
            endLoop();
            if(null != mPlayingListener)
                mPlayingListener.onCancel();
        }

        public void setPlayingProgress(int position)
        {
            if(null != mCurrentMediaPlayer)
            {
                mCurrentMediaPlayer.seekTo(position);
            }
        }


        public void stopService()
        {
            cannel();
            stopSelf();
        }
    }



/*******************************************************************************************/
/** 播放器事件监听 ***************************************************************************/
/*******************************************************************************************/
    @Override
    public void onCompletion(MediaPlayer mp)
    {
        Log.d(this.getClass().getSimpleName(),"onCompletion");
        mp.stop();
        mp.release();

        if(0 != mMediaPlayerList.size())
            mMediaPlayerList.removeFirst();
        mCurrentMediaPlayer = null;
        if(0 != mMediaPlayerList.size())
        {
            if(null != mPlayingListener)
            {
                mPlayingListener.onPlayingMusic(mMediaPlayerList.getFirst());
            }
            mMediaPlayerList.getFirst().getMediaPlayer().prepareAsync();
            Log.d(this.getClass().getSimpleName(),"nextPlay");
        }
        else
        {
            if(null != mPlayingListener)
            {
                mPlayingListener.onAllPlaySuccess();
                Log.d(this.getClass().getSimpleName(),"onAllPlaySuccess");
                stopSelf();
            }
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log.d(this.getClass().getSimpleName(),"onPrepared");
        if(null != mPlayingListener && 0 != mMediaPlayerList.size())
        {
            mPlayingListener.onPlayingMusic(mMediaPlayerList.getFirst());
        }

        if(BuildConfig.DEBUG)//直接 跳到最后10秒，节省调试时间
            mp.seekTo(mp.getDuration()-20000);

        mCurrentMediaPlayer = mp;
        mp.start();
        openLoop();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        Log.d(this.getClass().getSimpleName(),"onError:what="+what+"   extra="+extra);
        mCurrentMediaPlayer = null;
        mMediaPlayerList.removeFirst();
        if(null != mPlayingListener)
            mPlayingListener.onMusicError();
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        //Log.d(this.getClass().getSimpleName(),"onBufferingUpdate:percent="+percent);
    }
/**-------------------------------------------------------------------------------------播放器事件监听*/



    /** 处理循环通知播放进度 ********************************************************************************/
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            if(null != mCurrentMediaPlayer && null != mPlayingListener)
            {
                mPlayingListener.onPlayingProgress(mCurrentMediaPlayer.getDuration(),mCurrentMediaPlayer.getCurrentPosition());
            }
            super.handleMessage(msg);
        }
    };

    public void openLoop()
    {
        if(null == mLoopTimer)
            mLoopTimer = new Timer();

        if(null != mLoopTimerTask)
        {
            mLoopTimerTask.cancel();
            mLoopTimer.purge();
        }
        mLoopTimerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                mHandler.sendEmptyMessage(0);
            }
        };
        mLoopTimer.schedule(mLoopTimerTask, 1000, 1000);
    }
    public void endLoop()
    {
        if(mLoopTimer != null)
            mLoopTimer.cancel();
        mLoopTimerTask = null;
        mLoopTimer = null;
    }
    /********************************************************************************* 处理循环通知播放进度 */

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        if(null == mBinder)
            mBinder = new MyAudioPlayServiceBinder();
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getSimpleName(),"onDestroy");
        super.onDestroy();
        endLoop();
    }
}
