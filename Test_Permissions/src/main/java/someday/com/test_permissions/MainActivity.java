package someday.com.test_permissions;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.MenuInflater;
import android.view.Window;
import android.view.WindowManager;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("bbbbbb", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
        Log.d("bbbbbb", "shouldShow:"+ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE));
        TelephonyManager telephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        if (telephonyMgr != null)
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
//                if(!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE))
//                {
//                    Log.d("bbbbbb", "没有权限，用户勾了不再询问，跳 过");
//                    return;
//
//                }
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
                Log.d("bbbbbb", "没有权限，开始申请");
                return;
            }
            String imei = telephonyMgr.getDeviceId();
            Log.d("bbbbbb", "imei="+imei);
        }




        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);

        String packageName = info.topActivity.getPackageName();
        String topclassName = info.topActivity.getClassName();
        String baseclassname = info.baseActivity.getClassName();

//        try
//        {
//            Log.d("eeee", "acitivitynum="+Class.forName("baseActivityacitivitynum"));
            Log.d("eeee", "topclassName="+topclassName);
//            Log.d("eeee", "acitivitynum="+Class.forName("baseActivityacitivitynum"));
//        } catch (ClassNotFoundException e)
//        {
//            e.printStackTrace();
//        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("bbbbbb", "权限申请结果:"+(grantResults[0] == PackageManager.PERMISSION_GRANTED));
        Log.d("bbbbbb", "shouldShow:"+ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
        {
            TelephonyManager telephonyMgr = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
            String imei = telephonyMgr.getDeviceId();
            Log.d("bbbbbb", "imei="+imei);
            return;
        }
    }


}
