package someday.com.testdagger2kt.nomvp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import someday.com.testdagger2kt.nomvp.MyApplication

@Singleton2
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        ViewModelModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }

    fun inject(instance: MyApplication)
}