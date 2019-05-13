package com.sotwtm.support.util.databinding

import android.widget.Spinner
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [Spinner]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(type = Spinner::class, attribute = "setAdapter", method = "setAdapter")
)
object SpinnerBindingAdapter