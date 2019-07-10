package someday.com.testdagger2.mvp.base;

public class BaseMvpPresenter<T extends BaseView> implements BasePresenter<T>
{

    protected T baseView;
    @Override
    public void attachView(T baseView)
    {
        this.baseView = baseView;
    }

    @Override
    public void detachView()
    {
        this.baseView = null;
    }
}
