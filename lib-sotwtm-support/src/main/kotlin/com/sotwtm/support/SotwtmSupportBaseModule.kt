package com.sotwtm.support

import android.content.Context
import android.content.SharedPreferences
import androidx.core.os.LocaleListCompat
import androidx.databinding.ObservableField
import com.sotwtm.support.scope.LibScope
import com.sotwtm.support.util.locale.AppHelpfulLocaleUtil
import com.sotwtm.support.util.locale.unify
import dagger.Module
import dagger.Provides
import java.util.*
import kotlin.collections.ArrayList

@Module
internal class SotwtmSupportBaseModule {

    @LibScope
    @Provides
    fun sharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(DEFAULT_SHARED_PREF_FILE, Context.MODE_PRIVATE)

    @LibScope
    @Provides
    fun editor(sharedPreferences: SharedPreferences): SharedPreferences.Editor =
        sharedPreferences.edit()

    @LibScope
    @Provides
    fun appLocale(
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor,
        @SotwtmSupportComponent.DefaultSupportedLocales defaultSupportedLocales: List<Locale>
    ): ObservableField<Locale> =
        object : ObservableField<Locale>() {
            @Synchronized
            override fun get(): Locale =
                sharedPreferences.getString(SotwtmSupportLib.PREF_KEY_APP_LOCALE, null)?.let {
                    val languageTags = LocaleListCompat.forLanguageTags(it)
                    if (languageTags.isEmpty) AppHelpfulLocaleUtil.getDefaultLangFromSystemSetting(
                        supportedLocales(
                            sharedPreferences,
                            editor,
                            defaultSupportedLocales
                        ).get()
                    )
                    else languageTags[0]
                }
                    ?: AppHelpfulLocaleUtil.getDefaultLangFromSystemSetting(
                        supportedLocales(
                            sharedPreferences,
                            editor,
                            defaultSupportedLocales
                        ).get()
                    )

            @Synchronized
            override fun set(value: Locale) {
                if (get() == value) {
                    return
                }

                editor.putString(
                    SotwtmSupportLib.PREF_KEY_APP_LOCALE,
                    LocaleListCompat.create(value.unify()).toLanguageTags()
                )
                editor.apply()
                notifyChange()
            }
        }

    @LibScope
    @Provides
    fun supportedLocales(
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor,
        @SotwtmSupportComponent.DefaultSupportedLocales defaultSupportedLocales: List<Locale>
    ): ObservableField<List<Locale>> =
        object : ObservableField<List<Locale>>(emptyList()) {
            @Synchronized
            override fun get(): List<Locale> {
                val supportedLocalesString =
                    sharedPreferences.getString(SotwtmSupportLib.PREF_KEY_SUPPORTED_LOCALES, null)
                        ?: return defaultSupportedLocales
                return supportedLocalesString.split(SotwtmSupportLib.SEPARATOR_LOCALE).map {
                    LocaleListCompat.forLanguageTags(it)
                }.flatMap {
                    ArrayList<Locale>().apply {
                        for (i in 0 until it.size()) {
                            add(it.get(i))
                        }
                    }
                }
            }

            @Synchronized
            override fun set(newLocales: List<Locale>?) {

                if (newLocales == null || newLocales.isEmpty()) {
                    // Remove the list means supported all locale
                    editor.remove(SotwtmSupportLib.PREF_KEY_SUPPORTED_LOCALES)
                    editor.apply()
                    notifyChange()
                } else {
                    val currentAppLocale =
                        requireNotNull(
                            appLocale(
                                sharedPreferences,
                                editor,
                                defaultSupportedLocales
                            ).get()
                        )
                    // Check if the current locale is in the new supported locale list
                    if (!newLocales.any { AppHelpfulLocaleUtil.equals(currentAppLocale, it) }) {
                        // If the current locale is not supported anymore, update current locale to system best matched
                        SotwtmSupportLib.getInstance().resetLocaleToSystemBestMatched(newLocales)
                    }
                    val localeListString =
                        newLocales.joinToString(separator = SotwtmSupportLib.SEPARATOR_LOCALE,
                            transform = { LocaleListCompat.create(it.unify()).toLanguageTags() })
                    if (localeListString != sharedPreferences.getString(
                            SotwtmSupportLib.PREF_KEY_SUPPORTED_LOCALES,
                            null
                        )
                    ) {
                        editor.putString(
                            SotwtmSupportLib.PREF_KEY_SUPPORTED_LOCALES,
                            localeListString
                        )
                        editor.apply()
                        notifyChange()
                    }
                }
            }
        }

    companion object {
        const val DEFAULT_SHARED_PREF_FILE = "sotwtm-androidx-mvvm"
    }
}