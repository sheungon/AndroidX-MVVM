package com.sotwtm.support.util.databinding

import android.widget.Button
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods


/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [Button]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(type = Button::class, attribute = "selected", method = "setSelected")
)
object ButtonBindingAdapter