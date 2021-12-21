package com.localhost22.rf;

import java.util.function.BiFunction;

/**
 * Enumeration containing retrievers for objects
 * out of classes.
 */
public enum Retrievers {

    /**
     * Field retriever.
     */
    FIELD(RetrieverMethods::getField),

    /**
     * Method retriever.
     */
    METHOD(RetrieverMethods::getMethod),

    /**
     * Subclass retriever.
     */
    SUBCLASS(RetrieverMethods::getSubclass);

    /**
     * Retriever instance.
     */
    private final Retriever<Class<?>, Object> retriever;

    /**
     * Retrievers enumeration to retrieve an object from a class.
     * @param function function.
     */
    Retrievers(final BiFunction<Class<?>, String, Object> function) {
        this.retriever = function::apply;
    }

    /**
     * Get something from a class.
     * @param clazz      class
     * @param identifier identifier to retrieve
     * @param <T>        mutable type constraint
     * @return value from class
     */
    public <T> T get(final Class<?> clazz, final String identifier) {
        return (T) this.retriever.get(clazz, identifier);
    }

    /**
     * Get object by class name.
     * @param className  name of the class.
     * @param identifier identifier of the item to retrieve.
     * @param <T>        mutable type constraint
     * @return value
     */
    public <T> T get(final String className, final String identifier) {
        try {
            Class<?> clazz = Class.forName(className);
            return get(clazz, identifier);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Get class by a signature such as {@code java/lang/Object} or {@code java.lang.Object}.
     * @param signature signature
     * @param <T>       type constraint
     * @return class
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassBySignature(final String signature) {
        try {
            return (Class<T>) Class.forName(signature.replace('/', '.'));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
