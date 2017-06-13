package testrootapp.someday.cn.testaudioplay;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.x;
import static testrootapp.someday.cn.testaudioplay.R.id.fab;
import static testrootapp.someday.cn.testaudioplay.R.id.seekbar;

public class PlaySimpleActivity extends AppCompatActivity implements MyAudioPlayService.PlayingListener ,View.OnClickListener
{
    MyAudioPlayService.MyAudioPlayServiceBinder binder;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            binder = (MyAudioPlayService.MyAudioPlayServiceBinder) service;
            binder.setPlayingListener(PlaySimpleActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private TextView nameTextView;
    private FloatingActionButton fab;
    private SeekBar seekbar;

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

                binder.setPlayingProgress(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        nameTextView = (TextView)findViewById(R.id.name);
        MyAudioPlayHelper.bindService(this, serviceConnection);
    }

    /** 播放监听 ***************************************************************************/
    @Override
    public void onPlayingMusic(MyAudioPlayService.Music music)
    {
        nameTextView.setText(music.getName());
        seekbar.setMax(music.getMediaPlayer().getDuration());
    }

    @Override
    public void onPlayingProgress(int duration, int currentPosition)
    {
        seekbar.setProgress(currentPosition);
    }

    @Override
    public void onMusicStop()
    {
        fab.setImageResource(R.mipmap.play);
        seekbar.setProgress(0);
    }

    @Override
    public void onMusicError()
    {
        fab.setImageResource(R.mipmap.play);
        Toast.makeText(this,"加载音频失败",Toast.LENGTH_SHORT).show();
    }

    /********************************************************************************播放监听*/

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.fab:
                binder.play();
                fab.setImageResource(R.mipmap.stop);
                break;
            case R.id.music1:
                binder.addMuics("1",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495705290.mp3");
                break;
            case R.id.music2:
                binder.addMuics("2",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1484707801.mp3");
                break;
            case R.id.music3:
                binder.addMuics("3",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495705242.mp3");
                break;
            case R.id.music4:
                binder.addMuics("4",((TextView)v).getText().toString(),"http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495705273.mp3");
                break;


        }

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        MyAudioPlayHelper.unbindService(this,serviceConnection,binder);
    }
}
