package someday.com.testdagger2kt.nomvp;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import someday.com.testdagger2kt.nomvp.di.DaggerAppComponent;

public class MyApplication extends Application implements HasActivityInjector
{

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate()
    {
        super.onCreate();


        DaggerAppComponent.builder().application(this)
                .build().inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector()
    {
        return dispatchingActivityInjector;
    }
}
