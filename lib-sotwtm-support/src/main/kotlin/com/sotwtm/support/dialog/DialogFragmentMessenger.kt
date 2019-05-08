package com.sotwtm.support.dialog

import android.widget.Toast
import androidx.annotation.StringRes
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.base.BaseMessenger
import com.sotwtm.support.scope.FragmentScope
import com.sotwtm.util.Log
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * @author sheungon
 */

@FragmentScope
class DialogFragmentMessenger(private val fragmentRef: WeakReference<out AppHelpfulDialogFragment>) :
    BaseMessenger() {
    @Inject
    constructor(_fragment: AppHelpfulDialogFragment) : this(WeakReference(_fragment))

    private val fragment: AppHelpfulDialogFragment?
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

    override fun showSnackBar(messageRes: Int, duration: Int) =
        throw UnsupportedOperationException("Show snack bar is not supported in dialog fragment")

    override fun showSnackBar(message: String, duration: Int) =
        throw UnsupportedOperationException("Show snack bar is not supported in dialog fragment")

    fun dismiss() {
        try {
            fragment?.dismiss()
        } catch (e: Exception) {
            Log.e("Error on dismiss", e)
        }
    }

    fun showToast(stringRes: Int, duration: Int) {
        Toast.makeText(
            fragment?.context ?: return,
            stringRes,
            duration
        ).show()
    }

    fun showToast(string: String, duration: Int) {
        Toast.makeText(
            fragment?.context ?: return,
            string,
            duration
        ).show()
    }
}
