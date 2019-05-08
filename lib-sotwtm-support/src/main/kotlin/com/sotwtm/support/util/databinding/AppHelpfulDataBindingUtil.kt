package com.sotwtm.support.util.databinding

import androidx.databinding.BindingConversion
import com.sotwtm.util.Log

/**
 * DataBinding BindingAdapter and BindingConversion created for easier implementation for Android DataBinding.
 *
 * @author sheungon
 */
object AppHelpfulDataBindingUtil {

    @JvmStatic
    @BindingConversion
    fun stringToDouble(charSequence: CharSequence?): Double =
        try {
            if (charSequence?.isEmpty() != false) {
                0.0
            } else {
                charSequence.toString().toDouble()
            }
        } catch (e: Exception) {
            Log.e("Error on convert charSequence to int : $charSequence", e)
            0.0
        }
}
