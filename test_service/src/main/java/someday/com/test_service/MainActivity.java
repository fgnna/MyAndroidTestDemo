package someday.com.test_service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{
    {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");
    }


    protected ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {

        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyService.startService(getApplicationContext());
//        MyService2.startService(getApplicationContext());


//        bindService(MyService2.getStartIntent(this),mServiceConnection,
//                Context.BIND_AUTO_CREATE);
//
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                Log.d("MyService2","Handler().postDelayed");
//                unbindService(mServiceConnection);
//                bindService(MyService2.getStartIntent(MainActivity.this),mServiceConnection,
//                        Context.BIND_AUTO_CREATE);
//            }
//        },90*1000);
    }
}
