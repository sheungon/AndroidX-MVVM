package com.sotwtm.support.util.singleton

/**
 * A singleton holder for object with two arguments in constructor.
 *
 * Usage, extent this class in companion object
 *
 * `companion object : SingletonHolder<Arg0Class, Arg1Class, InstanceClass>(::InstanceClass)`
 *
 * Then, `InstanceClass.getInstance(arg0, arg1)`
 *
 * @author sheungon
 * */
open class SingletonHolder2<InstanceClass, Arg0Class, Arg1Class>(_constructor: (Arg0Class, Arg1Class) -> InstanceClass) {

    private val instanceConstructor: ((Arg0Class, Arg1Class) -> InstanceClass) = _constructor

    @Volatile
    private var instance: InstanceClass? = null

    fun getInstance(arg0: Arg0Class, arg1: Arg1Class): InstanceClass =
        instance ?: synchronized(this) {
            instance ?: instanceConstructor(arg0, arg1).also {
                instance = it
            }
        }

    fun getInstance(): InstanceClass =
        instance ?: throw ExceptionInInitializerError("This class not yet initialized")

    /**Initialize an instance for simple [getInstance]*/
    fun init(arg0: Arg0Class, arg1: Arg1Class): InstanceClass =
        getInstance(arg0, arg1)
}
