package someday.com.testdagger2.mvp.presenter;

import javax.inject.Inject;

import someday.com.testdagger2.mvp.base.BaseMvpPresenter;
import someday.com.testdagger2.mvp.contract.MainContract;

public class MainPresenter extends BaseMvpPresenter<MainContract.IView>
        implements MainContract.Presenter
{
    @Inject
    MainPresenter()
    {
    }

    @Override
    public void loadData()
    {
        baseView.onLoadData();
    }
}
