package testrootapp.someday.cn.stopsleepapp;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.os.PowerManager;
import android.service.quicksettings.TileService;
import android.util.Log;

import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.service.quicksettings.Tile.STATE_ACTIVE;

/**
 * Created by je on 17-6-10.
 */

public class MyQSTileService extends TileService
{
    private static PowerManager.WakeLock wakeLock;
    private Icon unLockIcon;
    private Icon lockIcon;


    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Log.d(this.getClass().getSimpleName(), "onTileAdded");
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        Log.d(this.getClass().getSimpleName(), "onTileRemoved");
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        getQsTile().setState(STATE_ACTIVE);
        Log.d(this.getClass().getSimpleName(), "onStartListening");
        if(wakeLock == null || !wakeLock.isHeld())
            getQsTile().setIcon(unLockIcon);
        else
            getQsTile().setIcon(lockIcon);
        getQsTile().updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.d(this.getClass().getSimpleName(), "onStopListening");
    }

    @Override
    public void onClick() {
        super.onClick();
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        Log.d(this.getClass().getSimpleName(), "onClick");
        if(null == wakeLock)
        {
            Log.d(this.getClass().getSimpleName(), "newWakeLock");
            wakeLock = powerManager.newWakeLock(FULL_WAKE_LOCK,"我的锁屏");
        }
        if(wakeLock.isHeld())
        {
            Log.d(this.getClass().getSimpleName(), "wakeLock.release()");
            getQsTile().setIcon(unLockIcon);
            getQsTile().updateTile();
            wakeLock.release();
        }
        else
        {
            getQsTile().setIcon(lockIcon);
            getQsTile().updateTile();
            wakeLock.acquire();
            Log.d(this.getClass().getSimpleName(), "wakeLock.acquire()");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(this.getClass().getSimpleName(), "onBind");
        return super.onBind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getSimpleName(), "onDestroy");
        unLockIcon = null;
        lockIcon = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(unLockIcon == null)
        {
            unLockIcon = Icon.createWithResource(this,R.mipmap.ic_un_lock);
            lockIcon = Icon.createWithResource(this,R.mipmap.ic_lock);
        }
        Log.d(this.getClass().getSimpleName(), "onCreate");
    }

}
