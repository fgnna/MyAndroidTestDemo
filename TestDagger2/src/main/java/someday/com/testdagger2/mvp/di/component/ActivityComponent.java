package someday.com.testdagger2.mvp.di.component;

import dagger.Component;
import someday.com.testdagger2.mvp.Main2Activity;
import someday.com.testdagger2.mvp.di.module.ActivityModule;
import someday.com.testdagger2.mvp.di.scope.ActivityScope;

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(Main2Activity mainActivity);
}