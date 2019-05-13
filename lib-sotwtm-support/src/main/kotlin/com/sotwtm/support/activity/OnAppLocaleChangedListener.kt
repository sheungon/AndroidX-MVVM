package com.sotwtm.support.activity

import android.content.SharedPreferences
import com.sotwtm.support.SotwtmSupportLib
import com.sotwtm.util.Log

/**
 * Listener for listening app locale change by this library.
 * @author sheungon
 * */
abstract class OnAppLocaleChangedListener : SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == SotwtmSupportLib.PREF_KEY_APP_LOCALE) {
            Log.d("Locale changed")
            onAppLocalChange()
        }
    }

    abstract fun onAppLocalChange()
}