package com.sotwtm.support.fragment.dialog

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.sotwtm.io.Syncable
import com.sotwtm.util.Log
import java.lang.ref.WeakReference

/**
 * An observable String that support get String from String res.
 *
 * The priority is get String from String res. If failed, get from String value.
 *
 * @author sheungon
 * */
class StringOrStringRes(_context: Context) : MutableLiveData<String?>(),
    Syncable<StringOrStringRes?> {

    private val contextRef: WeakReference<Context> = WeakReference(_context)

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
        postValue(msg)
    }

    @get:Synchronized
    @set:Synchronized
    @Volatile
    var msgRes: Int? = null
        set(value) {
            if (field != value) {
                field = value
                // Notify change
                postValue(this.value)
            }
        }

    @Synchronized
    override fun getValue(): String? = msgRes?.let {
        try {
            contextRef.get()?.getString(it)
        } catch (th: Throwable) {
            Log.e("Error on get resources $it", th)
            null
        }
    } ?: super.getValue()

    @Synchronized
    override fun sync(target: StringOrStringRes?) {
        msgRes = target?.msgRes
        postValue(target?.value)
    }

    @Synchronized
    override fun syncNonNull(target: StringOrStringRes?) {
        target?.msgRes?.let {
            msgRes = it
        }
        target?.value?.let {
            postValue(it)
        }
    }

    @Synchronized
    override fun fillInNullFields(target: StringOrStringRes?) {
        if (target == null) return
        if (msgRes == null) {
            msgRes = target.msgRes
        }
        if (value == null) {
            postValue(target.value)
        }
    }
}