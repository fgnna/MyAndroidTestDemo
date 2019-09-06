package someday.com.testdagger2kt.nomvp.modeView

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import someday.com.testdagger2kt.nomvp.base.People
import someday.com.testdagger2kt.nomvp.base.People2
import javax.inject.Inject

class PaoViewModel @Inject constructor(@JvmField @Inject var people: People) : BaseViewModel(){


    val testLiveData: MutableLiveData<People2> = MutableLiveData()

    fun doSomething(){

        people.hello()
        Log.d("ssssss","doSomething()")
        testLiveData.postValue(People2())

    }


}