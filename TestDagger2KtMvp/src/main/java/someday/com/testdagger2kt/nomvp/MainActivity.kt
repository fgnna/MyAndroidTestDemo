package someday.com.testdagger2kt.nomvp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import someday.com.testdagger2kt.R
import someday.com.testdagger2kt.nomvp.base.BaseActivity
import someday.com.testdagger2kt.nomvp.base.People
import someday.com.testdagger2kt.nomvp.base.People2
import someday.com.testdagger2kt.nomvp.modeView.PaoViewModel
import javax.inject.Inject

class MainActivity : BaseActivity()
{
    @Inject
    lateinit var people: People
    @Inject
    lateinit var people2: People2
    @Inject
    lateinit var mMainFragment: MainFragment
    @Inject
    lateinit var mPaoViewModel: PaoViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        people.hello();
        Log.d("ssssss",((null == mMainFragment).toString()))
        people2.hello();

//        val model = ViewModelProviders.of(this).get<PaoViewModel>(PaoViewModel::class.java!!)
//        model.getUsers().observe(this, Observer {
//
//                Log.d("ssssss","加载完数据")
//
//        })

        mPaoViewModel.testLiveData.observe(this, Observer {
            Log.d("ssssss","加载完数据")
        })
        mPaoViewModel.doSomething();

    }


}

