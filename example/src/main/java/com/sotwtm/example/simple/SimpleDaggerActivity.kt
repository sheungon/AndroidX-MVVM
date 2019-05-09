package com.sotwtm.example.simple

import android.os.Bundle
import com.sotwtm.example.R
import com.sotwtm.example.databinding.ActivitySimpleDaggerBinding
import com.sotwtm.support.activity.AppHelpfulDataBindingActivity
import javax.inject.Inject

/**
 * A simple example showing how to inject with dagger by SotwtmSupportLib
 * @author sheungon
 * */
class SimpleDaggerActivity : AppHelpfulDataBindingActivity<ActivitySimpleDaggerBinding>() {

    override val layoutResId: Int = R.layout.activity_simple_dagger
    @Inject
    override lateinit var viewModel: SimpleDaggerActivityViewModel
    override val coordinatorLayoutId: Int = R.id.coordinator_layout

    override fun initDataBinding(dataBinding: ActivitySimpleDaggerBinding, savedInstanceState: Bundle?) {
        dataBinding.viewModel = viewModel
    }
}
