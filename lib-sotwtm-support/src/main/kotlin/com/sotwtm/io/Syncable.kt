package com.sotwtm.io

/**
 * A syncable object that can sync the values from another object.
 *
 * @author sheungon
 */
interface Syncable<T> {

    /**
     * Sync all field from target.
     * @param target The source
     * */
    fun sync(target: T)

    /**
     * Sync all fields of target with non null values.
     * @param target The source
     * */
    fun syncNonNull(target: T)

    /**
     * Fill in all null fields of the current object from the target.
     * @param target The source
     * */
    fun fillInNullFields(target: T)
}
