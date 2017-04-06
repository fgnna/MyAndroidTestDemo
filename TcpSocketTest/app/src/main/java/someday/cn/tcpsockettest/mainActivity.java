package someday.cn.tcpsockettest;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.security.AccessController.getContext;

/**
 * tcp socket通信测试
 */
public class mainActivity extends AppCompatActivity
{

    public static class AcceptSocket extends Thread
    {
        private Context mContext;

        public AcceptSocket(Context context)
        {
            mContext = context;
        }

        @Override
        public void run()
        {
            super.run();

            //获取wifi服务
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            Log.d("TCPTEST","IP 地址： " + ipAddress);
            String ip = intToIp(ipAddress);
            Log.d("TCPTEST","IP 地址： " + ip);
            ServerSocket socket = null;
            try {
                socket = new ServerSocket(8999);
                Log.d("TCPTEST","socket 创建   成功 " );

                Socket client = null;
                while(true) {
                    client = socket.accept();

                    BufferedReader in = null;
                    String msg = null;
                    try
                    {
                        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        //客户端只要一连到服务器，便向客户端发送下面的信息。
                        while(true)
                        {
                            if ((msg = in.readLine()) != null)
                            {
                                //当客户端发送的信息为：exit时，关闭连接
                                if (msg.equals("exit"))
                                {
                                    in.close();
                                    Log.d("TCPTEST","socket 接收结束 " );
                                    break;
                                }
                                else
                                {
                                    Log.d("TCPTEST","socket 接收到信息 " + msg );
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        in.close();
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TCPTEST","socket 创建   失败！！！！： " + e.getMessage());
            }
            finally
            {
                if(null != socket)
                {
                    try {
                        socket.close();
                        Log.d("TCPTEST","socket 关闭   成功 " );
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("TCPTEST","socket 关闭   失败！！！！： " + e.getMessage());
                    }
                }
            }
        }
    }



    public static String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        new AcceptSocket(getApplicationContext()).start();

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        Log.d("TCPTEST","IP 地址： " + ipAddress);
        final String ip = intToIp(ipAddress);
        Log.d("TCPTEST","IP 地址： " + ip);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new AsyncTask()
                {
                    @Override
                    protected Object doInBackground(Object[] params)
                    {
                        try {


                            Socket mSocket = new Socket(ip,8999);
                            PrintWriter pout = null;
                            pout = new PrintWriter(new BufferedWriter(
                                    new OutputStreamWriter(mSocket.getOutputStream())),true);
                            pout.println("测试测试");
                            pout.println("exit");
                            pout.flush();
                            pout.close();
                            mSocket.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }
        });
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
