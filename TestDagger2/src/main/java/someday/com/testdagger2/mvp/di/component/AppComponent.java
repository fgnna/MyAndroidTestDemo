package someday.com.testdagger2.mvp.di.component;

import javax.inject.Singleton;

import dagger.Component;
import someday.com.testdagger2.mvp.di.module.AppModule;
import someday.com.testdagger2.mvp.di.module.HttpModule;

/**
 * Author: 海晨忆
 * Date: 2018/2/23
 * Desc:
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent
{


}