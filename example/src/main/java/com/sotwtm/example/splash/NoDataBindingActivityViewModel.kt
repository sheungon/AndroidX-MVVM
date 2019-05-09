package com.sotwtm.example.splash

import android.app.Application
import android.content.Intent
import com.sotwtm.example.simple.SimpleDaggerActivity
import com.sotwtm.support.activity.AppHelpfulActivityViewModel
import com.sotwtm.support.activity.SimpleActivityNavigator
import com.sotwtm.support.scope.ActivityScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data binder of [NoDataBindingActivity]
 * @author sheungon
 */

@ActivityScope
class NoDataBindingActivityViewModel
@Inject
constructor(
    application: Application,
    private val navigator: SimpleActivityNavigator
) : AppHelpfulActivityViewModel(application) {

    override fun onResume() {
        super.onResume()

        enterNextActivity()
    }

    private fun enterNextActivity() {

        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)

            navigator.finishActivity()

            val intent = Intent(getApplication(), SimpleDaggerActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
            navigator.startActivity(intent)
        }
    }
}
