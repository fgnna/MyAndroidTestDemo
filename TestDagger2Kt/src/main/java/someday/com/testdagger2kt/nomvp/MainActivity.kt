package someday.com.testdagger2kt.nomvp

import android.os.Bundle
import android.util.Log
import someday.com.testdagger2kt.R
import javax.inject.Inject

class MainActivity : BaseActivity()
{
    @Inject
    lateinit var people: People
    @Inject
    lateinit var people2: People2
    @Inject
    lateinit var mMainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        people.hello();
        Log.d("ssssss",((null == mMainFragment).toString()))
        people2.hello();
    }


}
