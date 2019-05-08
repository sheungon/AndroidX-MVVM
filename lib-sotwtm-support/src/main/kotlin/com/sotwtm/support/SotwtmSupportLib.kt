package com.sotwtm.support

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import androidx.databinding.ObservableField
import com.sotwtm.support.SotwtmSupportLib.Companion.getInstance
import com.sotwtm.support.SotwtmSupportLib.Companion.init
import com.sotwtm.support.activity.OnAppLocaleChangedListener
import com.sotwtm.support.util.locale.AppHelpfulLocaleUtil
import com.sotwtm.support.util.locale.setAppLocale
import com.sotwtm.support.util.locale.unify
import com.sotwtm.support.util.singleton.SingletonHolder1
import java.util.*
import javax.inject.Inject

/**
 * Call [init] before [getInstance] to use this class.
 * */
@SuppressLint("CommitPrefEdits")
class SotwtmSupportLib
private constructor(_application: Application) {

    @Inject
    internal lateinit var sharedPreferences: SharedPreferences
    @Inject
    internal lateinit var editor: SharedPreferences.Editor

    /**
     * The app locale currently using.
     * */
    @Inject
    lateinit var appLocale: ObservableField<Locale>
        internal set

    @Inject
    lateinit var supportedLocales: ObservableField<List<Locale>>
        internal set

    init {
        DaggerSotwtmSupportComponent.builder()
            .application(_application)
            .build()
            .inject(this)
        _application.setAppLocale(requireNotNull(appLocale.get()))
    }


    /**
     * Return true if the given locale is in the supported locale list, [supportedLocales].
     *
     * @param locale Check if this locale is supported
     * @see supportedLocales
     * */
    @Synchronized
    fun isSupportingLocale(locale: Locale): Boolean =
        with(supportedLocales.get()) {
            this == null
                    || isEmpty()
                    || any { AppHelpfulLocaleUtil.equals(locale, it) }
        }

    /**
     * Reset app locale to system best matched locale.
     * @param locales A list of [Locale] for matching. Default values is [supportedLocales]
     * @see supportedLocales
     * */
    fun resetLocaleToSystemBestMatched(locales: List<Locale> = requireNotNull(supportedLocales.get())) {
        appLocale.set(AppHelpfulLocaleUtil.getDefaultLangFromSystemSetting(locales))
    }

    fun registerOnAppLocaleChangedListener(listener: OnAppLocaleChangedListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterOnAppLocaleChangedListener(listener: OnAppLocaleChangedListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    companion object : SingletonHolder1<SotwtmSupportLib, Application>(::SotwtmSupportLib) {
        /**
         * Set the default supported locales list.
         * Empty list means supported all locales.
         * */
        @JvmStatic
        var defaultSupportedLocales: List<Locale> = emptyList()
            @Synchronized
            set(value) {
                field = value.map { it.unify() }
            }

        @JvmStatic
        var enableDaggerErrorLog = true

        const val PREF_KEY_APP_LOCALE = "AppLocale"
        const val PREF_KEY_SUPPORTED_LOCALES = "SupportedLocales"
        const val SEPARATOR_LOCALE = ","
    }
}