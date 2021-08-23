package com.localhost22.rf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * The retriever methods class handles
 * methods to retrieve fields and methods from
 * a class.
 */
final class RetrieverMethods {

    /**
     * Restrict access to utility class constructor.
     */
    private RetrieverMethods() {
    }

    /**
     * Cached subclasses.
     */
    private static final BiKeyMap<Class<?>, String, Class<?>> CLASS_MAP = new BiKeyMap<>();

    /**
     * Cached fields.
     */
    private static final BiKeyMap<Class<?>, String, Field> FIELD_MAP = new BiKeyMap<>();

    /**
     * Cached methods.
     */
    private static final BiKeyMap<Class<?>, String, Method> METHOD_MAP = new BiKeyMap<>();

    /**
     * Get a field.
     * @param cls        class
     * @param identifier name of the field
     * @param <T>        type constraint
     * @return field
     */
    public static <T> Field getField(final Class<T> cls, final String identifier) {
        Field field = FIELD_MAP.get(cls, identifier);
        if (field != null) {
            return field;
        }
        // Try to get the field and put it in the map.
        try {
            field = cls.getDeclaredField(identifier);
            field.setAccessible(true);
            FIELD_MAP.add(cls, identifier, field);
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * Get a method in a class by a special signature.
     * <p>
     * The format for these signatures are {@code Class#getName() + sig}.
     * Where the signature for Class#getName(), would be {@code getName()Ljava/lang/String;}
     * </p>
     * @param cls       class
     * @param signature signature
     * @return method or null.
     */
    public static Method getMethod(final Class<?> cls, final String signature) {
        Method method = METHOD_MAP.get(cls, signature);
        if (method != null) {
            return method;
        }
        try {
            method = Arrays
                    .stream(cls.getDeclaredMethods())
                    .filter(m -> Meta.getSignature(m).equals(signature))
                    .findAny()
                    .orElse(null);
            if (method != null) {
                method.setAccessible(true);
                METHOD_MAP.add(cls, signature, method);
            }
            return method;
        } catch (SecurityException e) {
            return null;
        }
    }

    /**
     * Get subclass gets a subclass from a superclass with the given name.
     * Granted, there are TONS of caveats to this.
     * @param superClass the super class
     * @param name       name of the subclass
     * @return the subclass, if it could be access.
     */
    public static Class<?> getSubclass(final Class<?> superClass, final String name) {
        Class<?> clazz = CLASS_MAP.get(superClass, name);
        // If the class exists, return early.
        if (clazz != null) {
            return clazz;
        }

        try {
            // Try to stream and find the class.
            clazz = Arrays
                    // I think eventually, this could
                    // support bypassing module access restrictions
                    // (something related to 396). However,
                    // the original target for this is version 1.8
                    // and modules aren't of importance here.
                    .stream(superClass.getDeclaredClasses())
                    .filter(cls -> cls.getSimpleName().equals(name))
                    .findAny()
                    .orElse(null);
            if (clazz != null) {
                // If the class was found, we can
                // add it into the existing map.
                CLASS_MAP.add(superClass, name, clazz);
            }
            return clazz;
        } catch (SecurityException e) {
            // There was an issue, and we cannot return the class.
            return null;
        }
    }

    /**
     * Get the value of a field.
     * @param field    field
     * @param instance instance
     * @param <T>      return constraint
     * @return value or null
     */
    public static <T> T getValue(final Field field, final Object instance) {
        try {
            return (T) field.get(instance);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

}
