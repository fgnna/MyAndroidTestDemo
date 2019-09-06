package someday.com.testdagger2.mvp;

import android.os.Bundle;
import android.util.Log;

import someday.com.testdagger2.R;
import someday.com.testdagger2.mvp.base.BaseMvpActivity;
import someday.com.testdagger2.mvp.contract.MainContract;
import someday.com.testdagger2.mvp.presenter.MainPresenter;

public class Main2Activity extends BaseMvpActivity<MainPresenter>
{

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main2;
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        basePresenter.loadData();
    }

    @Override
    protected void initInject()
    {
        getActivityComponent().inject(this);
    }


}
