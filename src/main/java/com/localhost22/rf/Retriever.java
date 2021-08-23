package com.localhost22.rf;

/**
 * Retrieve a value from a class.
 * @param <T> class
 * @param <V> value type
 */
@FunctionalInterface
public interface Retriever<T extends Class<?>, V> {

    /**
     * Function used to retrieve a value from a class.
     * @param clazz      class
     * @param identifier identifier of the object
     * @return object
     */
    V get(T clazz, String identifier);

}
