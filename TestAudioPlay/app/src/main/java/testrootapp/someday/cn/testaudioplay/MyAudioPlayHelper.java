package testrootapp.someday.cn.testaudioplay;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

public class MyAudioPlayHelper
{
    public static void bindService(Context context,ServiceConnection connection)
    {
        Intent intent = new Intent(context.getApplicationContext(),MyAudioPlayService.class);
        context.getApplicationContext().startService(intent);
        context.bindService(intent,connection,Context.BIND_AUTO_CREATE);
    }
    public static void unbindService(Context context,ServiceConnection connection,MyAudioPlayService.MyAudioPlayServiceBinder binder)
    {
        binder.removePlayingListener();
        context.unbindService(connection);
    }
}
