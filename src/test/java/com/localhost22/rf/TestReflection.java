package com.localhost22.rf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

/**
 * Reflection tests.
 */
public class TestReflection {

    /**
     * Test the comparison of signatures.
     * @throws NoSuchMethodException if the method to create a signature
     *                               for does not exist.
     */
    @Test
    public void testSignatureCompare() throws NoSuchMethodException {
        Method method = Class.class.getMethod("getName");
        String getName$Sig = Meta.getSignature(method);
        Assertions.assertEquals(getName$Sig, "getName()Ljava/lang/String;");
    }

    /**
     * Test the retrieval of a field.
     * @throws NoSuchFieldException if the requested field does not exist.
     */
    @Test
    public void testFieldGet() throws NoSuchFieldException {
        Field expected = Class.class.getDeclaredField("name");
        Field retrieved = Retrievers.FIELD.get(Class.class, "name");
        Assertions.assertEquals(expected, retrieved);
    }

    /**
     * Test the retrieval of a subclass.
     */
    @Test
    public void testSubclassGet() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // The first assertion checks that we can retrieve a class
        // that we can access from user-land.
        Class<?> expected = Base64.Decoder.class;
        Class<?> retrieved = Retrievers.SUBCLASS.get(Base64.class, "Decoder");
        Assertions.assertEquals(expected, retrieved);

        // The second assertion checks the retrieval of a
        // private static class.
        retrieved = Retrievers.SUBCLASS.get(Base64.class, "EncOutputStream");
        Assertions.assertNotNull(retrieved);

        // Test that we can get a valid constructor.
        Constructor<?> constr = retrieved.getDeclaredConstructor(
                OutputStream.class, char[].class, byte[].class, int.class, boolean.class
        );
        constr.setAccessible(true);
        OutputStream os = new ByteArrayOutputStream();
        String testString = "hello world";
        // Test an instance.
        Object instance = constr.newInstance(os, testString.toCharArray(), null, 30, false);
        Assertions.assertNotNull(instance);

        // Test method retrieval on the class.
        Method write = Retrievers.METHOD.get(retrieved, "write([BII)V");
        Assertions.assertNotNull(write);
    }

    @Test
    public void testClassGet() {
        String className = "sun/misc/Unsafe";
        String classObjectSignature = "L" + className + ";";
        Class<?> clazz = Retrievers.getClassBySignature(className);
        Assertions.assertNotNull(clazz);
        Assertions.assertEquals(classObjectSignature, Meta.getClassSignature(clazz));
    }

}
