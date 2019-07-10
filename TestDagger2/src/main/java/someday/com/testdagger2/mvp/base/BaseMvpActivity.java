package someday.com.testdagger2.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import javax.inject.Inject;

public abstract class BaseMvpActivity <T extends BasePresenter>  extends BaseActivity
{
    @Inject
    protected T basePresenter;


    @Override
    public void onCreate( Bundle savedInstanceState,  PersistableBundle persistentState)
    {
        super.onCreate(savedInstanceState, persistentState);
        initInject();
        if (null != basePresenter) {
            basePresenter.attachView(this);
        }
    }


    protected abstract void initInject();

    @Override
    protected void onDestroy() {
        if (null != basePresenter) {
            basePresenter.detachView();
            basePresenter = null;
        }
        super.onDestroy();
    }


}
