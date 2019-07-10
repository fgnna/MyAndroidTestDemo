package someday.com.testdagger2;

import dagger.Component;
import dagger.Provides;

@Component(modules = {ClassModule.class})
public interface ClassComponent
{
    void inject(MainActivity mainActivity);

}
