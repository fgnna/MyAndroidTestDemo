package someday.com.testdagger2.mvp.base;

import android.support.annotation.StringRes;

public interface BaseView
{

    void showTipMsg(String msg);

    void showTipMsg(@StringRes int msg);

    void showLoading();

    void hideLoading();

    void invalidToken();

    void myFinish();

}
