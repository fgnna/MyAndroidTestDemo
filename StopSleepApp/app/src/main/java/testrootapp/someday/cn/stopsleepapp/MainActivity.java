package testrootapp.someday.cn.stopsleepapp;

import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static android.os.PowerManager.FULL_WAKE_LOCK;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private TextView on;
    private TextView off;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        on = (TextView)findViewById(R.id.on);
        off = (TextView)findViewById(R.id.off);
        off.setEnabled(false);
        on.setOnClickListener(this);
        off.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.on:
                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
                wakeLock = powerManager.newWakeLock(FULL_WAKE_LOCK,"我的锁屏");
                wakeLock.acquire();
                on.setEnabled(false);
                off.setEnabled(true);
                break;
            case R.id.off:
                wakeLock.release();
                on.setEnabled(true);
                off.setEnabled(false);
                break;


        }
    }
}
