package testrootapp.someday.cn.testaudioplay;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 底部显示的播放控制栏
 * 无跳帧控制栏
 * Created by shaojunjie on 17-6-14.
 */

public class PlayMusicBar extends RelativeLayout implements View.OnClickListener
{

    public PlayMusicBar(Context context) {super(context);initalize();}
    public PlayMusicBar(Context context, AttributeSet attrs) {super(context, attrs);initalize();}
    public PlayMusicBar(Context context, AttributeSet attrs, int defStyleAttr) {super(context, attrs, defStyleAttr);initalize();}

    private PlayMusicButton playMusicButton;
    private ImageView closeButton;
    private TextView titleText;
    private TextView palyingTimeText;

    private String maxTimeStr;

    private void initalize()
    {
        inflate(getContext(),R.layout.view_play_music_bar,this);
        playMusicButton = (PlayMusicButton)findViewById(R.id.playMusicButton);
        closeButton = (ImageView)findViewById(R.id.close);
        titleText = (TextView)findViewById(R.id.title);
        palyingTimeText = (TextView)findViewById(R.id.playingTime);

        playMusicButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
        titleText.setOnClickListener(this);
        palyingTimeText.setOnClickListener(this);
    }

    public void setMax(int max)
    {
        maxTimeStr = "/" + timeToStr(max);
        playMusicButton.setMax(max);
    }

    public void setProgress(int progress)
    {
        palyingTimeText.setText(timeToStr(progress) + maxTimeStr);
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
                break;
            case R.id.close:
                break;
            case R.id.title:
            case R.id.playingTime:
                break;
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
}
