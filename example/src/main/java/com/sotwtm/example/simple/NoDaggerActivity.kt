package com.sotwtm.example.simple

import com.sotwtm.example.R
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.activity.AppHelpfulActivityViewModel

/**
 * This example is not recommended to use.
 * This is only to show how an Activity can still run without dagger using [AppHelpfulActivity].
 * Maybe only on project migrating from [android.support.v7.app.AppCompatActivity] to [AppHelpfulActivity].
 *
 * @author sheungon
 * */
class NoDaggerActivity : AppHelpfulActivity() {

    override val daggerEnabled: Boolean = false

    override val layoutResId: Int = R.layout.activity_no_dagger

    override val viewModel: AppHelpfulActivityViewModel by lazy {
        object : AppHelpfulActivityViewModel(application) {}
    }
}