package someday.com.testdagger2.mvp.base;

public interface BasePresenter <T extends BaseView>
{

    void attachView(T baseView);

    void detachView();
}
