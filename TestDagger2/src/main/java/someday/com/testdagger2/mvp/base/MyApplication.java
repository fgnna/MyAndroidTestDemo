package someday.com.testdagger2.mvp.base;

import android.app.Application;

import someday.com.testdagger2.mvp.di.AppComponent;
import someday.com.testdagger2.mvp.di.DaggerAppComponent;

public class MyApplication extends Application
{
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }
    private  static  AppComponent appComponent;

    public static synchronized AppComponent getAppComponent() {
        if (null == appComponent) {
            appComponent = DaggerAppComponent.builder().build();
        }
        return appComponent;
    }

    private void setInstance(MyApplication instance) {
        MyApplication.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        initLeakCanary();
    }

    /**
     * 初始化内存检测工具
     */
    private void initLeakCanary() {
    }
}
