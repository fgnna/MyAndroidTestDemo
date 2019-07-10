package someday.com.testdagger2.mvp.di;

import dagger.Module;
import someday.com.testdagger2.mvp.base.MyApplication;
@Module
public class AppModule {
    private MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }
}