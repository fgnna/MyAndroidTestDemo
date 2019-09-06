package someday.com.testdagger2;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ssssssssssss","去获取用户");
        MyViewModel model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getUsers().observe(this, users -> {
            Log.d("ssssssssssss", Looper.getMainLooper().getThread().getId() +" : "+ Thread.currentThread().getId() );
            Log.d("ssssssssssss","取用户完成");

        });


        List<BaseAsyncTask> list = new ArrayList();
        list.add(new BaseAsyncTask(1));
        list.add(new BaseAsyncTask(2));
        list.add(new BaseAsyncTask(3));
        list.add(new BaseAsyncTask(4));
        list.add(new BaseAsyncTask(5));
        list.add(new BaseAsyncTask(6));
        list.add(new BaseAsyncTask(7));
        list.add(new BaseAsyncTask(8));
        list.add(new BaseAsyncTask(9));
        list.add(new BaseAsyncTask(10));
        list.add(new BaseAsyncTask(12));
        list.add(new BaseAsyncTask(13));
        list.add(new BaseAsyncTask(14));
        list.add(new BaseAsyncTask(15));
        list.add(new BaseAsyncTask(16));

        int CPU_COUNT = Runtime.getRuntime().availableProcessors();
        int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
        int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
        int KEEP_ALIVE_SECONDS = 5;

        ThreadFactory sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
            }
        };

        BlockingQueue<Runnable> sPoolWorkQueue =
                new LinkedBlockingQueue<Runnable>(128);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                sPoolWorkQueue, sThreadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);


        for(int i=0 ; i < list.size();i++)
        {
            list.get(i).executeOnExecutor(threadPoolExecutor);

        }




        Log.d("dddd","cpu 数量:"+CPU_COUNT + "  核心 池 数："+CORE_POOL_SIZE + "   最大线程数："+MAXIMUM_POOL_SIZE + "   保持活动数："+ KEEP_ALIVE_SECONDS);


    }



    class BaseAsyncTask extends AsyncTask
    {
        private int id;


        public BaseAsyncTask(int id)
        {
            this.id = id;
        }

        @Override
        protected Object doInBackground(Object[] objects)
        {
            for(int i = 0 ;i<10;i++)
            {
                try
                {
                    Thread.sleep(1000);
                    Log.d("dddd","id:"+id+"  num:"+i);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }




}
