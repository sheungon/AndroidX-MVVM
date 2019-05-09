package com.sotwtm.example.contributor

import com.sotwtm.example.sub.SubcomponentActivity
import com.sotwtm.example.sub.SubcomponentActivitySubcomponent
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import java.util.*
import kotlin.random.Random

/**
 * All activities with subcomponent are registered here
 * @see ActivitiesContributorModule
 * @author sheungon
 * */
@Module(
    // Register activity subcomponents here
    subcomponents = [SubcomponentActivitySubcomponent::class]
)
class ActivitiesClassMapModule {

    /**
     * @see SubcomponentActivitySubcomponent
     * @see com.sotwtm.example.sub.SubcomponentActivityViewModel
     * */
    @Provides
    @IntoMap
    @ClassKey(SubcomponentActivity::class)
    fun bindActivityInjectorFactory(builder: SubcomponentActivitySubcomponent.Builder): AndroidInjector.Factory<*> =
    // Set any needed values for the subcomponent here
        builder.apply {
            setMagic(Random(Date().time).nextLong().rem(1000L))
        }
}
