package testrootapp.someday.cn.testaudioplay;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    public boolean isBind = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //startService(new Intent(MainActivity.this,MyAudioPlayService.class));

                if(!isBind)
                {
                    startService(new Intent(MainActivity2.this,MyAudioPlayService.class));
                    bindService(new Intent(MainActivity2.this,MyAudioPlayService.class),conn,BIND_AUTO_CREATE);
                    isBind = true;
                }
                else
                {
                    unbindService(conn);
                    isBind = false;
                }
                Snackbar.make(view, "播放", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

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
