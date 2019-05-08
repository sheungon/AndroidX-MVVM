package com.sotwtm.support.util.singleton

/**
 * A singleton holder for object with one argument in constructor.
 *
 * Usage, extent this class in companion object
 *
 * `companion object : SingletonHolder<Arg0Class, InstanceClass>(::InstanceClass)`
 *
 * Then, `InstanceClass.getInstance(arg0)`
 *
 * @author sheungon
 * */
open class SingletonHolder1<InstanceClass, Arg0Class>(_constructor: (Arg0Class) -> InstanceClass) {

    private val instanceConstructor: ((Arg0Class) -> InstanceClass) = _constructor

    @Volatile
    private var instance: InstanceClass? = null

    fun getInstance(arg0: Arg0Class): InstanceClass =
        instance ?: synchronized(this) {
            instance ?: instanceConstructor(arg0).also {
                instance = it
            }
        }

    fun getInstance(): InstanceClass =
        instance ?: throw ExceptionInInitializerError("This class not yet initialized")

    /**Initialize an instance for simple [getInstance]*/
    fun init(arg0: Arg0Class): InstanceClass =
        getInstance(arg0)
}
