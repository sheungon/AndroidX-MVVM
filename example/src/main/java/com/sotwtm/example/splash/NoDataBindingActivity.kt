package com.sotwtm.example.splash

import com.sotwtm.example.R
import com.sotwtm.support.activity.AppHelpfulActivity
import javax.inject.Inject

/**
 * This is a simple activity without using android data binding
 * @author sheungon
 */

class NoDataBindingActivity : AppHelpfulActivity() {

    override val layoutResId: Int = R.layout.activity_no_data_binder
    // Here we set the enter animation and exit animation for this activity
    override val startEnterAnim: Int? = R.anim.fade_in
    override val startExitAnim: Int? = R.anim.fade_out
    @Inject
    override lateinit var viewModel: NoDataBindingActivityViewModel
}
