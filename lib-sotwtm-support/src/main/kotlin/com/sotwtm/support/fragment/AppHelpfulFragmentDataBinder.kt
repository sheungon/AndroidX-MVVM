package com.sotwtm.support.fragment

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.lifecycle.AndroidViewModel
import com.sotwtm.support.SotwtmSupportLib

/**
 * @author sheungon
 */

abstract class AppHelpfulFragmentDataBinder(application: Application) :
    AndroidViewModel(application) {

    val locale = SotwtmSupportLib.getInstance().appLocale
    @Volatile
    var isPaused = true
        private set
    @Volatile
    var isViewDestroyed = false
        private set

    @Synchronized
    internal fun syncStatus(dataBinder: AppHelpfulFragmentDataBinder): AppHelpfulFragmentDataBinder {
        isPaused = dataBinder.isPaused
        isViewDestroyed = dataBinder.isViewDestroyed
        return this
    }

    open fun onCreate() {
    }

    internal fun onViewCreatedInternal(view: View?, savedInstanceState: Bundle?) {
        isViewDestroyed = false
        onViewCreated(view, savedInstanceState)
    }

    open fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    }

    open fun onStart() {
    }

    internal fun onResumeInternal() {
        isPaused = false
        onResume()
    }

    open fun onResume() {
    }

    internal fun onPauseInternal() {
        isPaused = true
        onPause()
    }

    open fun onPause() {
    }

    open fun onStop() {
    }

    internal fun onDestroyViewInternal() {
        isViewDestroyed = true
        onDestroyView()
    }

    open fun onDestroyView() {
    }

    open fun onDestroy() {
    }
}
