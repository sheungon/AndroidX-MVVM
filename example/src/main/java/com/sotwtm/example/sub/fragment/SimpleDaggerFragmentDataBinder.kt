package com.sotwtm.example.sub.fragment

import android.app.Application
import com.sotwtm.support.fragment.AppHelpfulFragmentDataBinder
import com.sotwtm.support.fragment.FragmentMessenger
import com.sotwtm.support.scope.FragmentScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data binder for [SimpleDaggerFragment]
 * @author sheungon
 * */
@FragmentScope
class SimpleDaggerFragmentDataBinder
@Inject
constructor(
    application: Application,
    private val messenger: FragmentMessenger
) : AppHelpfulFragmentDataBinder(application) {

    fun onClickLoading() {
        messenger.showLoadingDialog("")
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            messenger.dismissLoadingDialog()
        }
    }
}
