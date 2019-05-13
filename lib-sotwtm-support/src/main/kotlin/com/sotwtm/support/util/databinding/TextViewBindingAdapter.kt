package com.sotwtm.support.util.databinding

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [TextView]
 *
 * @author sheungon
 */
object TextViewBindingAdapter {

    @JvmStatic
    @Synchronized
    @BindingAdapter(
        value = ["showError"],
        requireAll = false
    )
    fun showError(
        view: TextView,
        error: String?
    ) {
        view.error = error
        if (error != null) {
            view.requestFocus()
        }
    }

    @JvmStatic
    @Synchronized
    @BindingAdapter(
        value = ["showErrorRes"],
        requireAll = false
    )
    fun showError(
        view: TextView,
        errorRes: Int?
    ) {
        showError(view, if (errorRes == null) null else view.context.getString(errorRes))
    }

    @JvmStatic
    @BindingAdapter("textColorRes")
    fun setTextColorRes(
        view: TextView,
        textColorRes: Int
    ) {
        view.setTextColor(ContextCompat.getColor(view.context, textColorRes))
    }

    @JvmStatic
    @BindingAdapter(
        value = ["drawableResLeft",
            "drawableResRight",
            "drawableResTop",
            "drawableResBottom",
            "drawableResStart",
            "drawableResEnd"],
        requireAll = false
    )
    fun setDrawablesRes(
        view: TextView,
        left: Int?,
        right: Int?,
        top: Int?,
        bottom: Int?,
        start: Int?,
        end: Int?
    ) {
        val context = view.context
        val topDrawable = top?.safeGetDrawable(context)
        val bottomDrawable = bottom?.safeGetDrawable(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val startDrawable = start?.safeGetDrawable(context)
            val endDrawable = end?.safeGetDrawable(context)
            view.setCompoundDrawablesRelative(
                startDrawable,
                topDrawable,
                endDrawable,
                bottomDrawable
            )
            if ((start == null && left != null) ||
                (end == null && right != null)
            ) {
                val leftDrawable =
                    if (start == null) left?.safeGetDrawable(context) else startDrawable
                val rightDrawable =
                    if (end == null) right?.safeGetDrawable(context) else endDrawable
                view.setCompoundDrawables(
                    leftDrawable,
                    topDrawable,
                    rightDrawable,
                    bottomDrawable
                )
            }
        } else {
            val leftDrawable = left?.safeGetDrawable(context)
            val rightDrawable = right?.safeGetDrawable(context)
            view.setCompoundDrawables(
                leftDrawable,
                topDrawable,
                rightDrawable,
                bottomDrawable
            )
        }
    }

    @JvmStatic
    @BindingAdapter("underlineText")
    fun underLineText(
        textView: TextView,
        underline: Boolean
    ) {
        if (underline) {
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        }
    }

    @JvmStatic
    @BindingAdapter(
        "typeface", "textStyle",
        requireAll = false
    )
    fun decorateText(
        view: TextView,
        typeface: Typeface?,
        textStyle: Int?
    ) {
        view.setTypeface(
            typeface ?: view.typeface,
            textStyle ?: Typeface.NORMAL
        )
    }

    @JvmStatic
    private fun Int.safeGetDrawable(context: Context): Drawable? =
        if (this == 0) null else ContextCompat.getDrawable(context, this)
}
