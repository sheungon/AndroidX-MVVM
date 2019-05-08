package com.sotwtm.support.util.databinding

import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.adapters.ListenerUtil
import androidx.recyclerview.widget.RecyclerView
import com.sotwtm.support.R

/**
 * DataBinding methods and BindingMethods created for easier implementation for Android DataBinding.
 * Implementation for [RecyclerView]
 *
 * @author sheungon
 */
@BindingMethods(
    BindingMethod(
        type = androidx.recyclerview.widget.RecyclerView::class,
        attribute = "setAdapter",
        method = "setAdapter"
    )
)
object RecyclerViewHelpfulBindingAdapter {

    @JvmStatic
    @BindingAdapter(value = ["setLayoutManagerOrientation"])
    fun setLayoutManager(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        orientation: Int
    ) {
        val reverseLayout =
            ListenerUtil.getListener<Boolean?>(recyclerView, R.id.setLayoutManagerReverseLayout)
        val autoMeasureEnabled =
            ListenerUtil.getListener<Boolean?>(recyclerView, R.id.setAutoMeasureEnabled)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            recyclerView.context,
            orientation,
            reverseLayout == true
        )
        if (autoMeasureEnabled != null) {
            layoutManager.isAutoMeasureEnabled = autoMeasureEnabled
        }
        recyclerView.layoutManager = layoutManager
    }

    @JvmStatic
    @BindingAdapter(value = ["setLayoutManagerReverseLayout"])
    fun setLayoutManagerReverseLayout(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        reverseLayout: Boolean?
    ) {
        ListenerUtil.trackListener(recyclerView, reverseLayout, R.id.setLayoutManagerReverseLayout)
        (recyclerView.layoutManager as? androidx.recyclerview.widget.LinearLayoutManager)?.reverseLayout =
            reverseLayout == true
    }

    @JvmStatic
    @BindingAdapter(value = ["setAutoMeasureEnabled"])
    fun setAutoMeasureEnabled(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        autoMeasureEnabled: Boolean?
    ) {
        ListenerUtil.trackListener(recyclerView, autoMeasureEnabled, R.id.setAutoMeasureEnabled)
        if (autoMeasureEnabled != null) {
            recyclerView.layoutManager?.isAutoMeasureEnabled = autoMeasureEnabled
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["setNestedScrollingEnabled"])
    fun setNestedScrollingEnabled(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        nestedScrollingEnabled: Boolean?
    ) {
        if (nestedScrollingEnabled != null) {
            recyclerView.isNestedScrollingEnabled = nestedScrollingEnabled
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["setGridNumberOfColumn"])
    fun setGridNumberOfColumn(
        recyclerView: androidx.recyclerview.widget.RecyclerView,
        numOfCol: Int?
    ) {
        if (numOfCol == null) return
        recyclerView.layoutManager =
            androidx.recyclerview.widget.GridLayoutManager(recyclerView.context, numOfCol)
    }
}