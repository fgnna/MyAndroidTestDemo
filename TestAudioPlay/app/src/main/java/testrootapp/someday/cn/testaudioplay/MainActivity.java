package testrootapp.someday.cn.testaudioplay;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity {


    private MediaPlayer mMediaPlayer;
    private SeekBar seekBar;
    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        findViewById(R.id.fab2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
//                countService.addMuics();
            }
        });
       // bindService(new Intent(MainActivity.this,MyAudioPlayService.class),conn,BIND_AUTO_CREATE);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                countService.play();
                Snackbar.make(view, "播放", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        // http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495705290.mp3

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    play();
            }
        });
        findViewById(R.id.pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                jumpTo50();
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.repace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    repace();
            }
        });

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMediaPlayer.seekTo(seekBar.getProgress());

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void repace()
    {
        mMediaPlayer.stop();
        mMediaPlayer.release();

        MediaPlayer mediaPlayer = new MediaPlayer();
        mMediaPlayer = mediaPlayer;
        try {
            mediaPlayer.setDataSource("http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495617070.mp3");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(mp.getDuration());
                    mp.start();
                    Log.d("testpaly","start = " + mMediaPlayer.isPlaying());
                }
            });
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void jumpTo50()
    {
    }

    private void add() throws IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mMediaPlayer = mediaPlayer;
        mediaPlayer.setDataSource("http://hxsapp-media-out-oss.hxsapp.com/hxs_audio/audio_1495617070.mp3");
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                //seekBar.setProgress(mMediaPlayer.getCurrentPosition());
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
                mp.start();
                Log.d("testpaly","start = " + mMediaPlayer.isPlaying());
            }
        });
    }

    private void stop() {
        mMediaPlayer.stop();
        Log.d("testpaly","stop = " + mMediaPlayer.isPlaying());
    }

    private void play()  {
        mMediaPlayer.prepareAsync();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    public MyAudioPlayService.MyAudioPlayServiceBinder countService;
    private ServiceConnection conn = new ServiceConnection() {

        /** 获取服务对象时的操作 */
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            countService = (MyAudioPlayService.MyAudioPlayServiceBinder) service;

        }

        /** 无法获取到服务对象时的操作 */
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            countService = null;
        }

    };





















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
