package com.sotwtm.support.activity

import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.StringRes
import com.sotwtm.support.base.BaseMessenger
import com.sotwtm.support.scope.ActivityScope
import com.sotwtm.support.util.SnackbarDuration
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Messenger for [AppHelpfulActivity]
 * @author sheungon
 */

@ActivityScope
class ActivityMessenger(private val activityRef: WeakReference<out AppHelpfulActivity>) :
    BaseMessenger() {
    @Inject
    constructor(_activity: AppHelpfulActivity) : this(WeakReference(_activity))

    override val activity: AppHelpfulActivity?
        get() = activityRef.get()

    override fun showLoadingDialog(@StringRes msgRes: Int) {
        activity?.showLoadingDialog(msgRes)
    }

    override fun showLoadingDialog(msg: String) {
        activity?.showLoadingDialog(msg)
    }

    override fun dismissLoadingDialog() {
        activity?.dismissLoadingDialog()
    }

    override fun showSnackBar(
        @StringRes messageRes: Int,
        @SnackbarDuration duration: Int
    ) {
        activity?.showSnackBar(messageRes, duration)
    }

    override fun showSnackBar(
        message: String,
        @SnackbarDuration duration: Int
    ) {
        activity?.showSnackBar(message, duration)
    }

    fun checkSelfPermission(permission: String): Int? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.checkSelfPermission(permission)
        } else {
            // In previous version always has permission
            PackageManager.PERMISSION_GRANTED
        }

    fun shouldShowRequestPermissionRationale(permission: String): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.shouldShowRequestPermissionRationale(permission) ?: false
        } else {
            // In previous version always has permission. So, no need to show request permission rationale
            false
        }

    fun requestPermissions(
        permissions: Array<String>,
        requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.requestPermissions(permissions, requestCode)
        }
    }
}
