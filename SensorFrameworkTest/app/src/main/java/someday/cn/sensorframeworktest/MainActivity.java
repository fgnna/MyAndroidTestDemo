package someday.cn.sensorframeworktest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{

    private SensorManager mSensorManager;
    private Sensor sensor;
    private TriggerEventListener mTriggerEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        checkSensorList();
    }

    private void checkSensorList()
    {
        List sensorList = new ArrayList();
        checkSingleSensor("加速度传感：",Sensor.TYPE_ACCELEROMETER );
        checkSingleSensor("磁力感应",Sensor.TYPE_MAGNETIC_FIELD );
        checkSingleSensor("陀螺仪",Sensor.TYPE_GYROSCOPE );
        checkSingleSensor("光度传感",Sensor.TYPE_LIGHT );
        checkSingleSensor("压力传感",Sensor.TYPE_PRESSURE );
        checkSingleSensor("屏幕距离传感",Sensor.TYPE_PROXIMITY );
        checkSingleSensor("重力传感",Sensor.TYPE_GRAVITY );
        checkSingleSensor("线性加速传感",Sensor.TYPE_LINEAR_ACCELERATION );
        checkSingleSensor("旋转矢量传感",Sensor.TYPE_ROTATION_VECTOR );
        checkSingleSensor("相对湿度传感",Sensor.TYPE_RELATIVE_HUMIDITY );
        checkSingleSensor("环境温度传感",Sensor.TYPE_AMBIENT_TEMPERATURE );
        checkSingleSensor("未校准的磁场传感器",Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED );
        checkSingleSensor("未校准的旋转矢量传感器",Sensor.TYPE_GAME_ROTATION_VECTOR );
        checkSingleSensor("未校准的陀螺仪传感器",Sensor.TYPE_GYROSCOPE_UNCALIBRATED );
        checkSingleSensor("重要运动触发传感器/唤醒传感器",Sensor.TYPE_SIGNIFICANT_MOTION );
        checkSingleSensor("单步传感",Sensor.TYPE_STEP_DETECTOR );
        checkSingleSensor("计步传感，从开机累计",Sensor.TYPE_STEP_COUNTER );
        checkSingleSensor("地磁旋转矢量",Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR );

        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION );

        mTriggerEventListener = new TriggerEventListener(){

            @Override
            public void onTrigger(TriggerEvent event) {
                Log.d("sensortest","onTrigger");
            }
        };
       if(mSensorManager.requestTriggerSensor(mTriggerEventListener,sensor))
       {
           Log.d("sensortest","成功启动触发传感------------");
       }
    }

    private void checkSingleSensor(String name,int sensortype)
    {
        Sensor defaultSensor = mSensorManager.getDefaultSensor(sensortype);
        Log.d("sensortest",name + " : " + (null == defaultSensor?"否":"是"));
    }


    protected void onResume() {
        super.onResume();
        //mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    protected void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener(this);
       // mSensorManager.cancelTriggerSensor(mTriggerEventListener,sensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Log.d("sensortest","onSensorChanged");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

        Log.d("sensortest","onAccuracyChanged");
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
