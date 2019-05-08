package com.sotwtm.support.rx

import android.content.SharedPreferences
import androidx.databinding.ObservableField

/**
 *
 * @author sheungon
 * */
class ObservableSharedPreferencesString(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val preferenceKey: String,
    private val defaultValue: String?
) : ObservableField<String?>() {

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
    override fun get(): String? =
        sharedPreferences.getString(preferenceKey, defaultValue) ?: defaultValue

    @Synchronized
    override fun set(value: String?) {
        if (get() == value) {
            return
        }

        value?.let {
            editor.putString(preferenceKey, it)
        } ?: run {
            editor.remove(preferenceKey)
        }
        editor.apply()
        notifyChange()
    }
}