package someday.com.test_sharememory;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{

    private static native FileDescriptor native_open(String name, int length) throws IOException;

    private MemoryFile mMemoryFile;
    private MemoryFile mMemoryFile2;
    private final int MEMORY_SIZE = 3133440 + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        Log.d("sssssssssssssss","onCreate(Bundle savedInstanceState)");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        new Timer().schedule(new TimerTask(){
//
//
//            @Override
//            public void run()
//            {
//                Log.d("ssss","1111111111111");
//            }
//        },1000,1000);

        final View test_bg = findViewById(R.id.test_bg);

        final Drawable dddd = test_bg.getBackground();

        test_bg.setBackgroundColor(0xff1a1a1a);

        test_bg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                test_bg.setBackground(dddd);
            }
        });




    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        Log.d("sssssssssssssss","onConfigurationChanged(Configuration newConfig)");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        Log.d("sssssssssssssss","onPostCreate(@Nullable Bundle savedInstanceState)");
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        Log.d("sssssssssssssss","onPostResume()");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("sssssssssssssss","onStart()");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("sssssssssssssss","onStop()");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("sssssssssssssss","onDestroy()");
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("sssssssssssssss","onResume()");
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("sssssssssssssss","onPause()");
    }
}
