package someday.com.testdagger2.mvp.di;

import javax.inject.Singleton;

import dagger.Component;

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