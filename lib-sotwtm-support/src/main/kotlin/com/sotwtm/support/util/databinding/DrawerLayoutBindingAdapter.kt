package com.sotwtm.support.util.databinding

import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [DrawerLayout]
 *
 * @author sheungon
 */
object DrawerLayoutBindingAdapter {

    @JvmStatic
    @BindingAdapter("addDrawerListener")
    fun DrawerLayout.addDrawerListener(listener: DrawerLayout.DrawerListener?) {
        if (listener != null) {
            addDrawerListener(listener)
        }
    }
}