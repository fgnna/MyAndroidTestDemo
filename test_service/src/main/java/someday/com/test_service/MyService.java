package someday.com.test_service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service
{

    private final static int GRAY_SERVICE_ID = 1001;
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
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


    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MyService.class);
        return intent;
    }

    public static void startService(Context context) {
        Log.d("MyService","startService");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            context.startService(getStartIntent(context));
            Log.d("MyService","startForegroundService");

        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("MyService","onStartCommand");
//        startForeground(GRAY_SERVICE_ID, createNotificationByChannel(this));
//        new Handler().postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                bindService(MyService2.getStartIntent(getApplicationContext()),mServiceConnection,
//                        Context.BIND_AUTO_CREATE);
//            }
//        },90*1000);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                MyService2.startService(getApplicationContext());

            }
        },90*1000);



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
//        startForeground(GRAY_SERVICE_ID, createNotificationByChannel(this));
        Log.d("MyService","onCreate");
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run()
            {
                Log.d("MyService","TimerTask");

            }
        },10000,10000);
    }


    @TargetApi(Build.VERSION_CODES.O)
    public static Notification createNotificationByChannel(Context context) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, launchIntentForPackage, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 用户可以看到的通知渠道的名字.
        CharSequence name = context.getString(R.string.app_name);
        // 用户可以看到的通知渠道的描述
        String description = context.getString(R.string.app_name) + "正在运行, 确保您及时接收消息提醒";
        // 在notificationManager中创建该通知渠道
        if (mNotificationManager != null) {
            // 仅需要常驻通知栏，所以级别是low
            NotificationChannel channel =  createNotificationChannel("sg_alive", "运行服务", description, NotificationManager.IMPORTANCE_LOW);
            // 不发出系统提示音，本身收到消息会有声音
            channel.setSound(null, null);
            // 不显示角标
            channel.setShowBadge(false);
            // 不提示灯
            channel.enableLights(false);
            // 不震动
            channel.enableVibration(false);
            mNotificationManager.createNotificationChannel(channel);
        }
        return new Notification.Builder(context)
                .setContentTitle(name)
                .setTicker(name)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId("sg_alive")
                .setContentIntent(pIntent)
                .setOngoing(true)
                .build();
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static NotificationChannel createNotificationChannel(String id, String name, String description, int importance) {
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        return mChannel;
    }
}
