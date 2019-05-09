package com.sotwtm.example.contributor

import com.sotwtm.example.sub.fragment.sub.SubcomponentFragment
import com.sotwtm.example.sub.fragment.sub.SubcomponentFragmentSubcomponent
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import java.util.*
import kotlin.random.Random

/**
 * All fragments with subcomponent are registered here
 * @see FragmentsContributorModule
 * @author sheungon
 * */
@Module(
    // Register activity subcomponents here
    subcomponents = [SubcomponentFragmentSubcomponent::class]
)
class FragmentsClassMapModule {

    /**
     * @see SubcomponentFragmentSubcomponent
     * @see com.sotwtm.example.sub.fragment.sub.SubcomponentFragmentViewModel
     * */
    @Provides
    @IntoMap
    @ClassKey(SubcomponentFragment::class)
    fun bindActivityInjectorFactory(builder: SubcomponentFragmentSubcomponent.Builder): AndroidInjector.Factory<*> =
    // Set any needed values for the subcomponent here
        builder.apply {
            setMagic(Random(Date().time).nextLong().rem(1000L).toString())
        }
}
