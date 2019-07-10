package someday.com.testdagger2.mvp;

import android.os.Bundle;
import android.util.Log;

import someday.com.testdagger2.R;
import someday.com.testdagger2.mvp.base.BaseMvpActivity;

public class Main2Activity extends BaseMvpActivity
{

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main2;
    }

    public void setText(String ssss)
    {
        Log.d("ssssssss","设置了一个SSS");
    }

    @Override
    protected void initInject()
    {

    }
}
