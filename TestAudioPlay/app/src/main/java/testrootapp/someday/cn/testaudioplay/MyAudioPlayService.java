package testrootapp.someday.cn.testaudioplay;

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


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.d(this.getClass().getSimpleName(),"onBind");
        if(null == mBinder)
            mBinder = new MyAudioPlayServiceBinder();
        return mBinder;
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        Log.d(this.getClass().getSimpleName(),"onCompletion");
        mp.stop();
        mp.release();
        if(null != mPlayingListener)
            mPlayingListener.onMusicStop();

        mMediaPlayerList.removeFirst();
        mCurrentMediaPlayer = null;
        if(0 != mMediaPlayerList.size())
            mMediaPlayerList.getFirst().getMediaPlayer().prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log.d(this.getClass().getSimpleName(),"onPrepared");
        if(null != mPlayingListener || 0 != mMediaPlayerList.size())
        {
            mPlayingListener.onPlayingMusic(mMediaPlayerList.getFirst());
        }

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
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(this.getClass().getSimpleName(),"onBufferingUpdate:percent="+percent);

    }

    /**
     * APP内公开的音频播放接口
     */
    class MyAudioPlayServiceBinder extends Binder
    {
        public void addMuics(String id,String name,String path)
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
                    mMediaPlayerList.getFirst().getMediaPlayer().prepareAsync();
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

        public void cannel()
        {
            if(null != mCurrentMediaPlayer)
            {
                mCurrentMediaPlayer.stop();
                mCurrentMediaPlayer.release();
                mCurrentMediaPlayer = null;
            }
            mMediaPlayerList.clear();
            endLoop();
            if(null != mPlayingListener)
                mPlayingListener.onMusicStop();
        }

        public void setPlayingProgress(int position)
        {
            if(null != mCurrentMediaPlayer)
            {
                mCurrentMediaPlayer.seekTo(position);
            }
        }
    }

    public interface PlayingListener
    {
        void onPlayingMusic(Music music);
        void onPlayingProgress(int duration, int currentPosition);
        void onMusicStop();
        void onMusicError();
    }

    /**
     * 歌曲信息实体类
     */
    public static class Music
    {
        private String id;
        private String name;
        private String path;
        private MediaPlayer mMediaPlayer;
        public Music(String id, String name, String path)
        {
            this.id = id;
            this.name = name;
            this.path = path;
        }


        void setMediaPlayer(MediaPlayer mediaPlayer)
        {
            mMediaPlayer = mediaPlayer;
        }
        MediaPlayer getMediaPlayer()
        {
            return mMediaPlayer;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPath() {
            return path;
        }
    }



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
/************************************************************************************************************* 处理循环通知播放进度 */

    @Override
    public void onDestroy() {
        Log.d(this.getClass().getSimpleName(),"onDestroy");
        super.onDestroy();
        endLoop();
    }
}
