package com.sotwtm.example.sub

import android.os.Bundle
import com.sotwtm.example.R
import com.sotwtm.example.databinding.ActivitySubcomponentBinding
import com.sotwtm.support.activity.AppHelpfulDataBindingActivity
import javax.inject.Inject

/**
 * Example to show how to create subcomponent in Activity with dagger
 * @author sheungon
 * */
class SubcomponentActivity : AppHelpfulDataBindingActivity<ActivitySubcomponentBinding>() {

    override val layoutResId: Int = R.layout.activity_subcomponent
    @Inject
    override lateinit var viewModel: SubcomponentActivityViewModel
    override val coordinatorLayoutId: Int = R.id.coordinator_layout


    override fun initDataBinding(dataBinding: ActivitySubcomponentBinding, savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
        setActionBarTitle(getString(R.string.title_subcomponent, viewModel.magicNumber.toString()))
    }
}