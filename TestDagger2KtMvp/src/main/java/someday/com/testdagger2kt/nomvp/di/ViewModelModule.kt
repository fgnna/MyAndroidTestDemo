package someday.com.testdagger2kt.nomvp.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import someday.com.testdagger2kt.nomvp.modeView.PaoViewModel
import someday.com.testdagger2kt.nomvp.modeView.PaoViewModelFactory


@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(PaoViewModel::class)//自定义的mapKey
    abstract fun bindPaoViewModel(viewModel: PaoViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(OtherViewModel::class)
//    abstract fun bindOtherViewModel(viewModel: OtherViewModel):ViewModel
//    ...

    @Binds
    abstract fun bindViewModelFactory(factory: PaoViewModelFactory): ViewModelProvider.Factory
}


