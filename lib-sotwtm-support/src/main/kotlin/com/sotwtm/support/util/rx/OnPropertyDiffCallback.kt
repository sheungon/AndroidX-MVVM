package com.sotwtm.support.util.rx

import androidx.databinding.Observable
import androidx.databinding.ObservableField

abstract class OnPropertyDiffCallback<ObservableFieldClass> :
    Observable.OnPropertyChangedCallback() {

    var firstTimeCallback = true
    var oldValue: ObservableFieldClass? = null

    override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
        (sender as? ObservableField<ObservableFieldClass>)?.get()?.let { newValue ->
            if (firstTimeCallback || newValue != oldValue) {
                firstTimeCallback = false
                oldValue = newValue
                onPropertyDiff(newValue)
            }
        }
    }

    abstract fun onPropertyDiff(newDiff: ObservableFieldClass)
}