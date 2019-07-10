package someday.com.testdagger2;

import android.util.Log;

import javax.inject.Inject;

public class ClassA
{
    @Inject
    protected ClassB b;

    public ClassA()
    {
        Log.d("ssssssss","创建了   A");
    }

    public ClassA(ClassB b) {
        this.b = b;
        Log.d("ssssssss","创建了 带参  A");
    }


    public void toClassB()
    {
        Log.d("ssssssss","打印了B"+b);
    }
}
