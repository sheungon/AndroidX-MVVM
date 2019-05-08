package com.sotwtm.support.fragment

import androidx.annotation.StringRes
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.base.BaseMessenger
import com.sotwtm.support.scope.FragmentScope
import com.sotwtm.support.util.SnackbarDuration
import com.sotwtm.util.Log
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * A simple messenger for [AppHelpfulFragment]
 * @author sheungon
 */
@FragmentScope
class FragmentMessenger(private val fragmentRef: WeakReference<out AppHelpfulFragment>) :
    BaseMessenger() {

    @Inject
    constructor(_fragment: AppHelpfulFragment) : this(WeakReference(_fragment))

    private val fragment: AppHelpfulFragment?
        get() = fragmentRef.get()

    override val activity: AppHelpfulActivity?
        get() = fragment?.activity as? AppHelpfulActivity

    fun showLoadingDialog() {
        try {
            fragment?.showLoadingDialog()
        } catch (e: Exception) {
            Log.e("Error on showLoadingDialog", e)
        }
    }

    override fun showLoadingDialog(@StringRes msgRes: Int) {
        try {
            fragment?.showLoadingDialog(msgRes)
        } catch (e: Exception) {
            Log.e("Error on showLoadingDialog", e)
        }
    }

    override fun showLoadingDialog(msg: String) {
        try {
            fragment?.showLoadingDialog(msg)
        } catch (e: Exception) {
            Log.e("Error on showLoadingDialog", e)
        }
    }

    override fun dismissLoadingDialog() {
        try {
            fragment?.dismissLoadingDialog()
        } catch (e: Exception) {
            Log.e("Error on dismissLoadingDialog", e)
        }
    }

    override fun showSnackBar(
        @StringRes messageRes: Int,
        @SnackbarDuration duration: Int
    ) {
        try {
            fragment?.showSnackBar(messageRes, duration)
        } catch (e: Exception) {
            Log.e("Error on showSnackBar", e)
        }
    }

    /**
     * Show snack bar with message.
     * This can be called from any thread.
     */
    override fun showSnackBar(
        message: String,
        @SnackbarDuration duration: Int
    ) {
        try {
            fragment?.showSnackBar(message, duration)
        } catch (e: Exception) {
            Log.e("Error on showSnackBar", e)
        }
    }
}
