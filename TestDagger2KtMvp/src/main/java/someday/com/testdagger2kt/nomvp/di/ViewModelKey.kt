package someday.com.testdagger2kt.nomvp.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import someday.com.testdagger2kt.nomvp.modeView.PaoViewModel
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)