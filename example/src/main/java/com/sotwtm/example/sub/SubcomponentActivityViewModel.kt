package com.sotwtm.example.sub

import android.app.Application
import androidx.databinding.ObservableInt
import androidx.fragment.app.FragmentPagerAdapter
import com.sotwtm.support.activity.AppHelpfulActivityViewModel
import com.sotwtm.support.scope.ActivityScope
import javax.inject.Inject

/**
 * This databinder get its own data from subcomponent
 * @see com.sotwtm.example.contributor.ActivitiesClassMapModule
 * @see com.sotwtm.example.sub.SubcomponentActivitySubcomponent
 * @author sheungon
 * */
@ActivityScope
class SubcomponentActivityViewModel
@Inject
constructor(
    application: Application,
    @SubcomponentActivitySubcomponent.MagicNumber val magicNumber: Long,
    val pagerAdapter: FragmentPagerAdapter
) : AppHelpfulActivityViewModel(application) {
    val selectTabAt = ObservableInt(0)
}