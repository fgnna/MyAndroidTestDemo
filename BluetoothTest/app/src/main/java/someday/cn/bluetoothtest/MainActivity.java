package someday.cn.bluetoothtest;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static android.bluetooth.BluetoothAdapter.getDefaultAdapter;

public class MainActivity extends AppCompatActivity
{

    private TextView mText;

    private BroadcastReceiver receiver;
    private ArrayList<BluetoothDevice> deviceList;
    private ArrayList<String> devices;
    /**
     * 当前已连接的设置
     */
    private String lockName;
    private BroadcastReceiver stateReceiver;
    StringBuilder stringBuilder = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mText = (TextView) findViewById(R.id.text);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!checkHasBluetooh())
                {
                    Snackbar.make(view, "没有蓝牙模块", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return ;
                }


                if(!checkIsEnable())
                {
                    Snackbar.make(view, "蓝牙没有打开", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    if(!open())
                    {
                        Snackbar.make(view, "蓝牙开启失败请手动设置", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                    else
                    {

                    }
                }
                else
                {
                    //new AcceptThread(getDefaultAdapter(),MainActivity.this).start();
                    checkBondedDevices();
                    mayRequestLocation();
                    Snackbar.make(view, "蓝牙已开启", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

            }
        });


        listenerState();
    }

    /**
     * 检查设置是否具备蓝牙模块
     * @return
     */
    private boolean checkHasBluetooh()
    {

        return null != getDefaultAdapter();
    }

    /**
     * 检查蓝牙是否已开启
     */
    private boolean checkIsEnable()
    {
        return getDefaultAdapter().isEnabled();
    }

    /**
     * 打开蓝牙
     */
    private boolean open()
    {
        /**
         * 可能不能版本有不同的开启蓝牙方式
         */

        return getDefaultAdapter().enable();
    }

    /**
     * 监听蓝牙开关的状态变化
     */
    private void listenerState()
    {
        stateReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction()))
                {
                    int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,-1);
                    stringBuilder.append("蓝牙状态改变="+state).append("\n");
                    mText.setText(stringBuilder.toString());
                }
            }
        };
        registerReceiver(stateReceiver,new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));


    }

    /**
     * 让蓝牙设备显示50秒
     */
    private void discoverable()
    {
        Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 50000); //3600为蓝牙设备可见时间
        startActivity(enable);
    }

    /**
     * 搜索设置列表
     * @return
     */
    private void deviceList()
    {
        deviceList = new ArrayList<BluetoothDevice>();
        devices = new ArrayList<String>();
        if(getDefaultAdapter().startDiscovery())
        {

            if(null == receiver)
            {
                receiver = new BroadcastReceiver()
                {

                    @Override
                    public void onReceive(Context context, Intent intent)
                    {
                        Snackbar.make(mText, "接收到设备列表广播", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        String action = intent.getAction();
                        if (BluetoothDevice.ACTION_FOUND.equals(action))
                        {
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            //BluetoothClass  紧用于查设备的类型 ，但并不是可靠的
                            //BluetoothClass bluetoothClass = intent.getParcelableExtra(BluetoothDevice.EXTRA_CLASS);
                            //if (isLock(device)) {
                            //    devices.add(device.getName());
                            //}
                            deviceList.add(device);
                        }
                        showDevices();
                    }
                };
                registerReceiver(receiver,new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
        }
        else
        {
            Snackbar.make(mText, "开始扫描失败", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }


    private void showDevices()
    {
        stringBuilder = new StringBuilder();
        for(BluetoothDevice bluetoothDevice: deviceList)
        {
            stringBuilder.append(bluetoothDevice.getName()).append(" | ").append(bluetoothDevice.getUuids()).append("\n");
        }
        mText.setText(stringBuilder.toString());
    }

    /**
     * 检查当前设备是否已经连接
     * @param device
     * @return
     */
    private boolean isLock(BluetoothDevice device)
    {
        boolean isLockName = (device.getName()).equals(lockName);
        boolean isSingleDevice = devices.indexOf(device.getName()) == -1;
        return isLockName && isSingleDevice;
    }


    /**
     * 检查已配对设置
     */
    private void checkBondedDevices()
    {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        stringBuilder = new StringBuilder();
        // If there are paired devices
        if (pairedDevices.size() > 0)
        {
            // Loop through paired devices
            stringBuilder.append("已配对的设置\n");
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                stringBuilder.append(device.getName() +" | " + device.getAddress() + "\n");
            }
        }
        else
        {
            stringBuilder.append("暂无配对的设置\n");
        }
        mText.setText(stringBuilder.toString());

    }

    /**
     * 请求开启位置信息
     * apiLevel 6.0 后须要该权限才能搜索设备
     */
    private void mayRequestLocation()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},23);
                return;
            }
        }
        deviceList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 23:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    deviceList();
                } else{
                    // The user disallowed the requested permission.
                }
                break;

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != receiver)
            unregisterReceiver(receiver);
        if(null != stateReceiver)
            unregisterReceiver(stateReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private final BluetoothAdapter mBluetoothAdapter ;
    private final Context mContext;
    public AcceptThread(BluetoothAdapter bluetoothAdapter,Context context)
    {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        mBluetoothAdapter = bluetoothAdapter;
        mContext = context;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("蓝牙服务", UUID.randomUUID());
        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                try {
                    manageConnectedSocket(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void manageConnectedSocket(BluetoothSocket socket) throws IOException
    {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        //new FileOutputStream(new File(mContext));
        byte[] b=new byte[8192];
        while ( bufferedInputStream.read(b,0,8192) != -1 )
        {

        }
        bufferedInputStream.close();
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
}