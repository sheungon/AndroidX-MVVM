package com.sotwtm.support.util.databinding

import androidx.databinding.BindingAdapter
import androidx.drawerlayout.widget.DrawerLayout

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [DrawerLayout]
 *
 * @author sheungon
 */
object DrawerLayoutHelpfulBindingAdapter {

    @JvmStatic
    @BindingAdapter("addDrawerListener")
    fun addDrawerListener(
        drawerLayout: androidx.drawerlayout.widget.DrawerLayout,
        listener: androidx.drawerlayout.widget.DrawerLayout.DrawerListener?
    ) {
        if (listener != null) {
            drawerLayout.addDrawerListener(listener)
        }
    }
}