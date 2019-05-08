package com.sotwtm.support.rx

import android.content.SharedPreferences
import androidx.databinding.ObservableBoolean

/**
 * For observing [Boolean] value in [SharedPreferences]
 * @author sheungon
 * */
class ObservableSharedPreferencesBoolean(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val preferenceKey: String,
    private val defaultValue: Boolean
) : ObservableBoolean() {

    private val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == preferenceKey) {
                notifyChange()
            }
        }
    private var registeredListener = true

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    @Synchronized
    fun restartPrefListener() {
        if (registeredListener) return
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
        registeredListener = true
    }

    @Synchronized
    fun endPrefListener() {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
        registeredListener = false
    }

    @Synchronized
    override fun get(): Boolean =
        sharedPreferences.getBoolean(preferenceKey, defaultValue)

    @Synchronized
    override fun set(value: Boolean) {
        if (get() == value) {
            return
        }

        editor.putBoolean(preferenceKey, value)
        editor.apply()
        notifyChange()
    }
}