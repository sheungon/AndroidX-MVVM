package com.sotwtm.support.util.databinding

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [ProgressBar]
 *
 * @author sheungon
 */
object ProgressBarHelpfulBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["setProgressColorRes"])
    fun setProgressBarColorRes(
        view: ProgressBar,
        @ColorRes colorRes: Int
    ) {
        setProgressBarColor(view, ContextCompat.getColor(view.context, colorRes))
    }

    @JvmStatic
    @BindingAdapter(value = ["setProgressColor"])
    fun setProgressBarColor(
        view: ProgressBar,
        @ColorInt color: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.progressTintList = ColorStateList.valueOf(color)
        } else {
            view.progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["setProgress", "animateProgress"],
        requireAll = false
    )
    fun setProgress(
        view: ProgressBar,
        progress: Int,
        animateProgress: Boolean
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.setProgress(progress, animateProgress)
        } else {
            view.progress = progress
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["setProgressMax"],
        requireAll = false
    )
    fun setProgressMax(
        view: ProgressBar,
        progressMax: Int
    ) {
        view.max = progressMax
    }
}