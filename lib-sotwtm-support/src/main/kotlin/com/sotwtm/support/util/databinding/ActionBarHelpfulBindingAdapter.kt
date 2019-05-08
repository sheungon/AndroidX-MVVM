package com.sotwtm.support.util.databinding

import androidx.appcompat.app.ActionBar
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [ActionBar]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(
        type = ActionBar::class,
        attribute = "setDisplayShowHomeEnabled",
        method = "setDisplayShowHomeEnabled"
    ),
    BindingMethod(
        type = ActionBar::class,
        attribute = "setDisplayHomeAsUpEnabled",
        method = "setDisplayHomeAsUpEnabled"
    )
)
object ActionBarHelpfulBindingAdapter