package testrootapp.someday.cn.testaudioplay.widget;

import android.content.Context;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import testrootapp.someday.cn.testaudioplay.R;
import testrootapp.someday.cn.testaudioplay.service.audio.Music;
import testrootapp.someday.cn.testaudioplay.service.audio.MyAudioPlayController;
import testrootapp.someday.cn.testaudioplay.service.audio.PlayingListener;

import static android.R.attr.duration;

/**
 * 底部显示的播放控制栏
 * 无跳帧控制栏
 * Created by shaojunjie on 17-6-14.
 */

public class PlayMusicBar extends RelativeLayout implements View.OnClickListener,PlayingListener
{

    public PlayMusicBar(Context context) {super(context);initalize();}
    public PlayMusicBar(Context context, AttributeSet attrs) {super(context, attrs);initalize();}
    public PlayMusicBar(Context context, AttributeSet attrs, int defStyleAttr) {super(context, attrs, defStyleAttr);initalize();}

    private PlayMusicButton playMusicButton;
    private ImageView closeButton;
    private TextView titleText;
    private TextView palyingTimeText;
    private MyAudioPlayController mMyAudioPlayController;

    private String maxTimeStr;

    private boolean isPlaying = false;

    private void initalize()
    {
        inflate(getContext(), R.layout.view_play_music_bar,this);
        playMusicButton = (PlayMusicButton)findViewById(R.id.playMusicButton);
        closeButton = (ImageView)findViewById(R.id.close);
        titleText = (TextView)findViewById(R.id.title);
        palyingTimeText = (TextView)findViewById(R.id.playingTime);

        playMusicButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        titleText.setOnClickListener(this);
        palyingTimeText.setOnClickListener(this);
    }

    public void setMyAudioPlayController(MyAudioPlayController myAudioPlayController)
    {
        mMyAudioPlayController = myAudioPlayController;
        mMyAudioPlayController.addPlayingListener(this);
    }

    public void setMax(int max)
    {
        maxTimeStr = "/" + timeToStr(max);
        playMusicButton.setMax(max);
    }

    public void setProgress(int progress)
    {
        palyingTimeText.setText(timeToStr(progress) + (TextUtils.isEmpty(maxTimeStr)?"/00:00":maxTimeStr));
        playMusicButton.setProgress(progress);
    }

    public void setTitle(String title)
    {
        titleText.setText(title);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.playMusicButton:
                playMusicButtonOnClick();
                break;
            case R.id.close:
                closeOnClick();
                break;
            case R.id.title:
            case R.id.playingTime:
                break;
        }
    }

    private void closeOnClick()
    {
        mMyAudioPlayController.stopMusic();
        hide();
        mMyAudioPlayController.stopService();
        mMyAudioPlayController.onPause();
    }

    private void playMusicButtonOnClick()
    {
        if(isPlaying)
        {
            if(!mMyAudioPlayController.stopMusic())
                return;
            playMusicButton.stop();
            isPlaying = false;
        }
        else
        {
            if(!mMyAudioPlayController.startMusic())
                return;
            playMusicButton.play();
            isPlaying = true;
        }


    }


    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("mm:ss");
    private Calendar calendar = Calendar.getInstance();

    /**
     * 把毫秒数转能 mm:ss 格式字符串
     * @param millisecond
     * @return
     */
    private String timeToStr(int millisecond)
    {
        int minute = millisecond / 1000 / 60;
        int second = millisecond / 1000 % 60;
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MINUTE,minute);
        return mSimpleDateFormat.format(calendar.getTime());
    }


    @Override
    public void onPlayingMusic(Music music)
    {
        maxTimeStr = null;
        setTitle(music.getName());
        show();
        isPlaying = true;
        playMusicButton.play();
    }

    @Override
    public void onPlayingProgress(int duration, int currentPosition)
    {
        if(TextUtils.isEmpty(maxTimeStr))
            setMax(duration);
        setProgress(currentPosition);
    }

    @Override
    public void onMusicStop()
    {
        playMusicButton.stop();
        isPlaying = false;
    }

    @Override
    public void onMusicError()
    {
        maxTimeStr = null;
        playMusicButton.stop();
        isPlaying = false;
    }

    @Override
    public void onAllPlaySuccess()
    {
        maxTimeStr = null;
        playMusicButton.stop();
        isPlaying = false;
        setProgress(0);
        hide();
    }

    @Override
    public void onCancel()
    {
        maxTimeStr = null;
        setProgress(0);
    }


    /**
     * 带过渡动画隐藏
     */
    public void show()
    {
        animate().alpha(1f).setInterpolator(new FastOutSlowInInterpolator()).start();
    }

    /**
     * 带过渡动画显示
     */
    public void hide()
    {
        animate().alpha(0f).setInterpolator(new FastOutSlowInInterpolator()).start();
    }

}
