package com.sotwtm.support

import android.app.Application
import com.sotwtm.support.scope.LibScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import java.util.*
import javax.inject.Qualifier

@LibScope
@Component(
    modules = [SotwtmSupportBindsModule::class,
        SotwtmSupportBaseModule::class]
)
interface SotwtmSupportComponent : AndroidInjector<SotwtmSupportLib> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        /**
         * Set the default supported locales list.
         * Empty list means supported all locales.
         * */
        @BindsInstance
        fun defaultSupportLocale(@DefaultSupportedLocales defaultSupportedLocales: List<Locale>): Builder

        fun build(): SotwtmSupportComponent
    }

    @Qualifier
    @Retention(AnnotationRetention.SOURCE)
    annotation class DefaultSupportedLocales
}
