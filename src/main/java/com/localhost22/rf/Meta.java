package com.localhost22.rf;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Meta utility class used for handling method/class byte data.
 *
 * @author bfu4
 * @since v1.0
 */
public final class Meta {

    /**
     * Private the constructor for the utility class.
     */
    private Meta() { }

    /**
     * Get the return type internal description.
     * @param method    method to get the return type internal description of
     * @return          return type internal description
     */
    public static String getReturnTypeInternal(@NotNull final Method method) {
        return method.getReturnType().getName();
    }

    /**
     * Get the signature of a specified method.
     * @param method method
     * @return signature
     */
    public static String getSignature(@NotNull final Method method) {
        StringBuilder sb = new StringBuilder(method.getName() + "(");
        for (Class<?> paramType : method.getParameterTypes()) {
            // Create an array of the type.
            // Using the new instance, extract the signature and remove a layer of array.
            // Append a subarray, excluding the initial VM hashcode.
            // https://stackoverflow.com/a/45122250
            sb.append(getClassSignature(paramType));
        }
        // Close the parameter portion of the signature.
        sb.append(')');

        // The return portion of the signature.
        if (method.getReturnType() == void.class) {
            sb.append("V");
        } else {
            sb.append(getClassSignature(method.getReturnType()));
        }
        return sb.toString();
    }

    /**
     * Get the signature of a class.
     * @param clazz     class
     * @return          signature
     */
    public static String getClassSignature(final Class<?> clazz) {
        Object typeInstanceArray = Array.newInstance(clazz, 0);
        String instance = typeInstanceArray.toString().replace('.', '/');
        return instance.substring(1, instance.indexOf('@'));
    }

    /**
     * Get a method's exceptions as a string array.
     * @param method method
     * @return exceptions
     */
    public static String[] getExceptions(@NotNull final Method method) {
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        switch (exceptionTypes.length) {
            // Take care of the length to avoid streaming an empty array.
            case 0:
                // Return nothing.
                // This can be null over `new String[0]` since ASM will also
                // desire a null when there are no exceptions.
                return null;
            case 1:
                // There is only one value in the array. Instead of streaming,
                // a 1-value array can be created.
                return new String[]{exceptionTypes[0].getName()};
            default:
                // Stream the exceptions
                return Arrays
                        .stream(method.getExceptionTypes())
                        .map(Meta::getClassSignature)
                        .toArray(String[]::new);
        }
    }

}
