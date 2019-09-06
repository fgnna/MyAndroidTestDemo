package someday.com.testdagger2.mvp.contract;

import someday.com.testdagger2.mvp.base.BasePresenter;
import someday.com.testdagger2.mvp.base.BaseView;

public interface MainContract {
    interface IView extends BaseView
    {
        void onLoadData();
    }
    interface Presenter extends BasePresenter<IView>
    {
        void loadData();
    }
}