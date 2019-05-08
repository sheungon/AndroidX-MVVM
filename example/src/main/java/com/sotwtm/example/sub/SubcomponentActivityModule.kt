package com.sotwtm.example.sub

import androidx.fragment.app.FragmentPagerAdapter
import com.sotwtm.example.sub.fragment.NoDataBindingFragment
import com.sotwtm.example.sub.fragment.SimpleDaggerFragment
import com.sotwtm.example.sub.fragment.sub.SubcomponentFragment
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Here we bind the [SubcomponentActivity] to [AppHelpfulActivity].
 * Because in SotwtmSupportLib [com.sotwtm.support.activity.SimpleActivityNavigator] and
 * [com.sotwtm.support.activity.ActivityMessenger] setMagic [AppHelpfulActivity] to work.
 *
 *
 * @author sheungon
 * */
@Module(
    // We can install other modules needed by SubcomponentActivity here
    includes = []
)
class SubcomponentActivityModule {
    @Provides
    fun activity(activity: SubcomponentActivity): AppHelpfulActivity = activity

    @Provides
    @ActivityScope
    fun adapter(activity: SubcomponentActivity): FragmentPagerAdapter =
        object : FragmentPagerAdapter(activity.supportFragmentManager) {

            override fun getItem(page: Int): androidx.fragment.app.Fragment =
                when (page) {
                    0 -> SimpleDaggerFragment()
                    1 -> SubcomponentFragment()
                    else -> NoDataBindingFragment()
                }

            override fun getCount(): Int = 3

            override fun getPageTitle(position: Int): CharSequence? = position.toString()
        }
}