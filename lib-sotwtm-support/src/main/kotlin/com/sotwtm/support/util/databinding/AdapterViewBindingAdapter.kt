package com.sotwtm.support.util.databinding

import android.content.Context
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.ArrayRes
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingMethod
import androidx.databinding.InverseBindingMethods
import androidx.databinding.adapters.ListenerUtil

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [AdapterView]
 *
 * @author sheungon
 */
@InverseBindingMethods(
    InverseBindingMethod(
        type = AdapterView::class,
        attribute = "selectedItemString",
        method = "getSelectedItem",
        event = "android:selectedItemPositionAttrChanged"
    )
)
object AdapterViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("itemsStringArrayRes")
    fun AdapterView<*>.setItemsStringArrayRes(@ArrayRes stringArrayRes: Int?) {
        val arrayAdapter = stringArrayRes?.let { arrayRes ->
            ListenerUtil.getListener<ArrayAdapter<String>>(this, arrayRes)
                ?: run {
                    arrayRes.toResStringArray(context)
                        .toArrayAdapter(context).apply {
                            ListenerUtil.trackListener(this@setItemsStringArrayRes, this, arrayRes)
                        }
                }
        }
        if (adapter != arrayAdapter) {
            adapter = arrayAdapter
        }
    }

    @JvmStatic
    @BindingAdapter("selectedItemString")
    fun AdapterView<*>.selectedItemString(selectedString: String?) {
        val selectedPos = (adapter as? ArrayAdapter<String>)
            ?.run {
                for (i in 0 until count) {
                    if (getItem(i) == selectedString) {
                        return@run i
                    }
                }
                -1
            } ?: -1
        if (selectedItemPosition != selectedPos) {
            setSelection(selectedPos)
        }
    }

    @JvmStatic
    @BindingAdapter("itemsStringArrayRes", "selectedItemString")
    fun AdapterView<*>.selectedItemString(stringArrayRes: Int?, selectedString: String?) {
        setItemsStringArrayRes(stringArrayRes)
        selectedItemString(selectedString)
    }

    private fun Int.toResStringArray(context: Context) =
        context.resources.getStringArray(this).asList()

    private fun List<String>.toArrayAdapter(context: Context) =
        ArrayAdapter(context, android.R.layout.simple_spinner_item, this).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
}