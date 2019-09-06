package someday.com.testdagger2kt.nomvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import someday.com.testdagger2kt.R
import someday.com.testdagger2kt.nomvp.base.BaseFragment
import javax.inject.Inject

class MainFragment @Inject constructor(): BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater!!.inflate(R.layout.activity_main, container, false)
    }
}