package com.sotwtm.support.util.databinding

import android.view.View
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.ListenerUtil
import com.google.android.material.snackbar.Snackbar
import com.sotwtm.support.R
import com.sotwtm.support.activity.AppHelpfulActivity
import com.sotwtm.support.util.createSnackbar
import com.sotwtm.util.Log

object ViewHelpfulBindingAdapter {

    @JvmStatic
    @BindingAdapter("menuId")
    fun showAllMenu(
        view: View,
        menuId: Int?
    ) {
        (view.context as? AppHelpfulActivity)?.menuResId = menuId
    }

    @JvmStatic
    @BindingAdapter("backgroundResource")
    fun setBackgroundRes(
        view: View,
        @DrawableRes resId: Int?
    ) {
        if (resId == null) {
            view.background = null
        } else {
            view.setBackgroundResource(resId)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["snackbarMsg",
            "snackbarDuration",
            "snackbarActionMsg",
            "snackbarAction"],
        requireAll = false
    )
    fun showSnackbar(
        view: View,
        msg: String?,
        duration: Int?,
        actionMsg: String?,
        action: View.OnClickListener?
    ) {
        ListenerUtil.getListener<Snackbar?>(view, R.id.snackbar)?.dismiss()

        if (duration != Snackbar.LENGTH_SHORT &&
            duration != Snackbar.LENGTH_LONG &&
            duration != Snackbar.LENGTH_INDEFINITE
        ) {
            Log.e("Show snackbar with an invalid time : $duration")
            return
        }

        if (msg == null) return
        val snackbar = view.createSnackbar(msg, duration)
        if (actionMsg != null && action != null) {
            snackbar.setAction(actionMsg, action)
        }
        snackbar.show()

        ListenerUtil.trackListener(view, snackbar, R.id.snackbar)
    }
}