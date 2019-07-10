package someday.com.testdagger2.mvp.di;

import dagger.Component;
import someday.com.testdagger2.MainActivity;

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
}