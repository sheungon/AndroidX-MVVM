package com.sotwtm.support.fragment.dialog

import android.content.Context
import androidx.annotation.StringRes
import androidx.databinding.ObservableField
import com.sotwtm.util.Log

class StringOrStringRes(private val context: Context) : ObservableField<String?>() {

    constructor(
        context: Context,
        @StringRes msgRes: Int
    ) : this(context) {
        this.msgRes = msgRes
    }

    constructor(
        context: Context,
        msg: String
    ) : this(context) {
        set(msg)
    }

    var msgRes: Int? = null
        @Synchronized
        set(value) {
            field = value
            if (value != null) {
                set(null)
            }
        }

    @Synchronized
    override fun get(): String? = msgRes?.let {
        try {
            context.getString(it)
        } catch (th: Throwable) {
            Log.e("Error on get resources $it", th)
            null
        }
    } ?: super.get()
    ?: ""

    @Synchronized
    override fun set(value: String?) {
        if (value != null) {
            msgRes = null
        }
        super.set(value)
    }

    @Synchronized
    fun sync(value: StringOrStringRes) {
        msgRes = value.msgRes
        if (msgRes == null) {
            set(value.get())
        }
    }
}