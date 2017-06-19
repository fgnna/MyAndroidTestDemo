package testrootapp.someday.cn.testaudioplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import testrootapp.someday.cn.testaudioplay.service.TestService;
import testrootapp.someday.cn.testaudioplay.service.audio.MyAudioPlayController;
import testrootapp.someday.cn.testaudioplay.service.audio.WalkmanGroupsBean;
import testrootapp.someday.cn.testaudioplay.util.Util;
import testrootapp.someday.cn.testaudioplay.widget.PlayMusicBar;

public class PlaySimpleActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView nameTextView;
    private FloatingActionButton fab;
    private SeekBar seekbar;
    private PlayMusicBar playMusicBar;

    private MyAudioPlayController mMyAudioPlayController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_simple);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.music1).setOnClickListener(this);
        findViewById(R.id.music2).setOnClickListener(this);
        findViewById(R.id.music3).setOnClickListener(this);
        findViewById(R.id.music4).setOnClickListener(this);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);
        seekbar = (SeekBar)findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        nameTextView = (TextView)findViewById(R.id.name);

        playMusicBar = (PlayMusicBar)findViewById(R.id.playMusicBar);

        mMyAudioPlayController = new MyAudioPlayController(this);
        playMusicBar.setMyAudioPlayController(mMyAudioPlayController);

    }

    /** 播放监听 ***************************************************************************/

    /********************************************************************************播放监听*/

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.fab:
               // binder.play();
                //fab.setImageResource(R.mipmap.stop);
                if(!Util.isServiceWork(this, TestService.class.getName()))
                {
                    startService(new Intent(this,TestService.class));
                }
                else
                {
                    stopService(new Intent(this,TestService.class));
                }
                break;
            case R.id.music1:
                mMyAudioPlayController.playMusic(new WalkmanGroupsBean.WalkManBean("1",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495705290.mp3"));
                break;
            case R.id.music2:
                mMyAudioPlayController.playMusic(new WalkmanGroupsBean.WalkManBean("2",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1484707801.mp3"));
                break;
            case R.id.music3:
                mMyAudioPlayController.playMusic(new WalkmanGroupsBean.WalkManBean("3",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495705242.mp3"));
                break;
            case R.id.music4:
                mMyAudioPlayController.playMusic(new WalkmanGroupsBean.WalkManBean("4",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495705273.mp3"));
                break;


        }

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        mMyAudioPlayController.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mMyAudioPlayController.onPause();
    }

    @Override
    protected void onDestroy()
    {
        mMyAudioPlayController.onDestroy();
        super.onDestroy();
    }
}
