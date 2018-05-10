package com.sotwtm.support.activity

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Bundle
import android.view.MenuItem

/**
 * View model abstract class for [AppHelpfulDataBindingActivity]
 * @author John
 */
abstract class AppHelpfulActivityViewModel(app : Application) : AndroidViewModel(app) {

    /* Don't name this as "isResumed" because "isResumed" is a hidden final method in parent class. */
    @Volatile
    var isActivityPaused = true
        private set
    @Volatile
    var isActivityDestroyed = false
        private set

    @Synchronized
    internal fun syncStatus(viewModel: AppHelpfulActivityViewModel): AppHelpfulActivityViewModel {
        isActivityPaused = viewModel.isActivityPaused
        isActivityDestroyed = viewModel.isActivityDestroyed
        return this
    }

    internal fun onCreateInternal(savedInstanceState: Bundle?) {
        isActivityDestroyed = false
        onCreate(savedInstanceState)
    }

    open fun onCreate(savedInstanceState: Bundle?) {
    }

    open fun onStart() {
    }

    internal fun onResumeInternal() {
        isActivityPaused = false
        onResume()
    }

    open fun onResume() {
    }

    internal fun onPauseInternal() {
        isActivityPaused = true
        onPause()
    }

    open fun onPause() {
    }

    open fun onStop() {
    }

    internal fun onDestroyInternal() {
        isActivityDestroyed = true
        onDestroy()
    }

    open fun onDestroy() {
    }

    open fun onSaveInstanceState(outState: Bundle) {
    }

    open fun onRequestPermissionsResult(requestCode: Int,
                                        permissions: Array<out String>,
                                        grantResults: IntArray) {
    }

    open fun onOptionsItemSelected(item: MenuItem): Boolean = false
}
