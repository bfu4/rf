package com.localhost22.rf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

/**
 * The BiKeyMap test, tests functions related to
 * {@link Pair} and essential map functions.
 */
public class TestBiKeyMap {

    /**
     * A field map.
     */
    private static BiKeyMap<Class<?>, String, Field> fieldMap;

    /**
     * Tasks before running tests.
     */
    @BeforeAll
    public static void setup() {
        fieldMap = new BiKeyMap<>();
    }

    /**
     * Test that two different pair objects equal each other.
     */
    @Test
    public void testPairEquals() {
        Pair<Class<?>, String> first = new Pair<>(Object.class, "equals");
        Pair<Class<?>, String> second = new Pair<>(Object.class, "equals");
        Assertions.assertEquals(first, second);
    }

    /**
     * Test that a map is able to grab given two entries and a pair object.
     * @throws NoSuchFieldException if the value input in the map
     *                              could not be found.
     */
    @Test
    public void testMapGrab() throws NoSuchFieldException {
        Field field = Class.class.getDeclaredField("name");
        field.setAccessible(true);
        fieldMap.add(Class.class, "name", field);
        Assertions.assertEquals(fieldMap.size(), 1);

        Field found = fieldMap.get(Class.class, "name");
        Assertions.assertEquals(found, field);
    }

}
