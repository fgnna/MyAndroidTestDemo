package someday.com.testdagger2.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import someday.com.testdagger2.mvp.di.ActivityComponent;
import someday.com.testdagger2.mvp.di.ActivityModule;

public  abstract class BaseActivity extends AppCompatActivity implements BaseView
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

    }
    protected abstract int getLayoutId();

    /**
     * Toast 提示用户
     * @param msg 提示内容String
     */
    @Override
    public void showTipMsg(String msg) {
    }

    /**
     * Toast 提示用户
     * @param msg 提示内容res目录下面的String的int值
     */
    @Override
    public void showTipMsg(int msg) {
    }

    /**
     * 网络请求的时候显示正在加载的对话框
     */
    @Override
    public void showLoading() {
    }

    /**
     * 网络请求完成时隐藏加载对话框
     */
    @Override
    public void hideLoading() {
    }

    @Override
    public void invalidToken() {
        //用于检测你当前用户的token是否有效，无效就返回登录界面，具体的业务逻辑你自己实现
        //如果需要做到实时检测，推荐用socket长连接，每隔10秒发送一个验证当前登录用户token是否过期的请求
    }

    /**
     * Finish当前页面，最好实现onBackPressedSupport()，这个方法会有一个退栈操作，
     * 开源框架实现的，我们不用管
     */
    @Override
    public void myFinish() {
    }


//    protected ActivityComponent getActivityComponent() {
//        return DaggerActivityComponent.builder()
//                .appComponent(MyApplication.getAppComponent())
//                .activityModule(new ActivityModule())
//                .build();
//    }

}