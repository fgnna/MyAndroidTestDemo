package someday.com.testdagger2kt.nomvp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import someday.com.testdagger2kt.nomvp.MainActivity
import someday.com.testdagger2kt.nomvp.MyApplication
import javax.inject.Singleton

@Module
abstract class ActivityBuilderModule {

    @Singleton
    @Binds
    abstract fun application(app: MyApplication): Context

//    @ContributesAndroidInjector(modules = arrayOf(MainModule::class))
    @ContributesAndroidInjector()
    abstract fun bindMainActivity(): MainActivity

}

/**
 *  Activity里的每一个Fragment，要在module里面如下声明
 *  并且Fragment还有用@Inject标注其无参构造方法
 */

//@Module
//abstract class MainModule {
//
//    @Binds
//    abstract fun bindMainFragment(mainFragment: MainFragment): Fragment
//
//    @ContributesAndroidInjector
//    abstract fun bindMainFragment(): MainFragment
//
//}