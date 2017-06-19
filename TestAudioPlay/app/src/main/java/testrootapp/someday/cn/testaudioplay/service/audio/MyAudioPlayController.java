package testrootapp.someday.cn.testaudioplay.service.audio;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import testrootapp.someday.cn.testaudioplay.util.Util;

/**
 * 处理 播放服务 的业务逻辑 提取
 * 注意：
 *  必须要在Activity中生成实体
 *  必须要在activity的三个生命周期中调用对应的方法
 *          Activity -> onResume   调用  MyAudioPlayController -> onResume
 *          Activity -> onPause    调用  MyAudioPlayController -> onPause
 *          Activity -> onDestroy  调用  MyAudioPlayController -> onDestroy
 *
 * @author shaojunjie on 17-6-16
 * @Email fgnna@qq.com
 *
 */
public class MyAudioPlayController  {
    public final String TAG = "MyAudioPlayController";
    private final MyPlayingListener mMyPlayingListener;

    private Intent intent;
    private Activity mActivity;
    private MyAudioPlayService.MyAudioPlayServiceBinder binder;

    private HashSet<PlayingListener> listeners = new HashSet<>();


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.d("MyAudioPlayService","onServiceConnected");
            binder = (MyAudioPlayService.MyAudioPlayServiceBinder) service;
            binder.setPlayingListener(mMyPlayingListener);
            if(null != waitingMusics)
                playMusic(waitingMusics);
            waitingMusics = null;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("MyAudioPlayService","onServiceDisconnected");
            binder=null;
        }
    };

    public MyAudioPlayController(Activity activity)
    {
        mActivity = activity;
        intent = new Intent(mActivity,MyAudioPlayService.class);
        mMyPlayingListener  = new MyPlayingListener();
    }

    public void addPlayingListener(PlayingListener playingListener)
    {
        listeners.add(playingListener);
    }
    public void removePlayingListener(PlayingListener playingListener)
    {
        listeners.remove(playingListener);
    }

    private List<WalkmanGroupsBean.WalkManBean> waitingMusics;


    /**
     * 播放单首歌
     * @param walkManBean
     */
    public void playMusic(WalkmanGroupsBean.WalkManBean walkManBean)
    {
        if(null == walkManBean)
            return;
        ArrayList<WalkmanGroupsBean.WalkManBean> walkManBeanList = new ArrayList<>();
        walkManBeanList.add(walkManBean);
        playMusic(walkManBeanList);
    }
    /**
     * 播放单多首歌
     * @param walkManBeanList
     */
    public void playMusic(List<WalkmanGroupsBean.WalkManBean> walkManBeanList)
    {
        if(null == walkManBeanList || 0 == walkManBeanList.size())
            return;

        if(!Util.isServiceWork(mActivity,MyAudioPlayService.class.getName()));
            mActivity.startService(intent);
        if(binder == null)
        {
            mActivity.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            waitingMusics = walkManBeanList;
        }
        else
        {
            binder.cannel();
            for(WalkmanGroupsBean.WalkManBean walkManBean : walkManBeanList)
            {
                binder.addMuics(walkManBean.id,walkManBean.content,walkManBean.audio_url);
            }
            binder.play();
        }
    }


    public boolean stopMusic()
    {
        if(null != binder)
            binder.stop();
        else
            return false;
        return true;
    }

    public boolean clearMusic()
    {
        if(null != binder)
            binder.cannel();
        else
            return false;
        return true;
    }

    public boolean startMusic()
    {
        if(null != binder)
            binder.play();
        else
            return false;
        return true;
    }

    public boolean stopService()
    {
        if(null != binder)
            binder.stopService();
        else
            return false;
        return true;
    }


    public void onResume()
    {
        if(Util.isServiceWork(mActivity,MyAudioPlayService.class.getName()))
            mActivity.bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onPause()
    {
        if(null != binder)
        {
            try
            {
                binder.removePlayingListener();
                mActivity.unbindService(serviceConnection);
                binder = null;
            }
            catch (Exception e)
            {
                binder = null;
                //如果 service已结束，这里可能会出现异常
            }
        }
    }

    public void onDestroy()
    {
        listeners.clear();
    }

/** service 中的事件监听 ************************************************************************/

    class MyPlayingListener implements PlayingListener
    {
        @Override
        public void onPlayingMusic(Music music)
        {
            if(null != listeners)
                for(PlayingListener playingListener : listeners)
                    playingListener.onPlayingMusic(music);
        }

        @Override
        public void onPlayingProgress(int duration, int currentPosition)
        {
            if(null != listeners)
                for(PlayingListener playingListener : listeners)
                    playingListener.onPlayingProgress(duration,currentPosition);

        }

        @Override
        public void onMusicStop()
        {
            if(null != listeners)
                for(PlayingListener playingListener : listeners)
                    playingListener.onMusicStop();
        }

        @Override
        public void onMusicError()
        {
            if(null != listeners)
                for(PlayingListener playingListener : listeners)
                    playingListener.onMusicError();
        }

        @Override
        public void onAllPlaySuccess()
        {
            if(null != listeners)
                for(PlayingListener playingListener : listeners)
                    playingListener.onAllPlaySuccess();

            onPause();
        }

        @Override
        public void onCancel()
        {
            if(null != listeners)
                for(PlayingListener playingListener : listeners)
                    playingListener.onCancel();
        }
    }
/************************************************************************* service 中的事件监听 */
}
