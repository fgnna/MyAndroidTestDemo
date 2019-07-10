package someday.com.testdagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
{

    @Inject
    ClassA mClassA;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerClassComponent.create().inject(this);
        mClassA.toClassB();
    }
}
