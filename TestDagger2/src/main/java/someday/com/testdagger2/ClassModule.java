package someday.com.testdagger2;

import android.util.Log;

import dagger.Module;
import dagger.Provides;

@Module
public class ClassModule
{

    @Provides
    ClassA providesPersonParam() {
        Log.d("ssssssss","providesPersonParam()");
        return new ClassA(new ClassB());
    }

}
