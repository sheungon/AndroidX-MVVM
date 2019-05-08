package com.sotwtm.example

import com.sotwtm.support.SotwtmSupportLib
import com.sotwtm.util.Log
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import java.util.*

/**
 * An example application to show how to make use of Dagger, Android Databinding and SotwtmSupportLib
 * @author sheungon
 * */
class ExampleApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()

        Log.logLevel = if (BuildConfig.DEBUG) Log.VERBOSE else Log.NONE
        Log.defaultLogTag = "SotwtmExample"

        SotwtmSupportLib.init(this, DEFAULT_SUPPORTED_LOCALES)
    }

    override fun applicationInjector(): AndroidInjector<ExampleApplication> =
        DaggerExampleApplicationComponent.builder()
            .application(this)
            .build()

    companion object {
        val DEFAULT_SUPPORTED_LOCALES = listOf<Locale>(
            Locale.ENGLISH
        )
    }
}