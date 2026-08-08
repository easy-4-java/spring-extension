package org.springframework.extension.utils;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.Serializable;
import java.util.*;

/**
 * Tests for {@link GenericsUtils}.
 */
public class GenericsUtilsTest {

    // Helper classes for generic type testing
    static abstract class GenericParent<T> {}
    static class StringChild extends GenericParent<String> {}
    static class IntegerChild extends GenericParent<Integer> {}
    static class RawChild extends GenericParent {}

    interface GenericInterface<T> {}
    static class InterfaceChild implements GenericInterface<Long> {}

    @Test
    public void shouldGetSuperClassGenericType() {
        Class<?> type = GenericsUtils.getSuperClassGenricType(StringChild.class);
        assertEquals(String.class, type);
    }

    @Test
    public void shouldGetSuperClassGenericTypeByIndex() {
        Class<?> type = GenericsUtils.getSuperClassGenricType(StringChild.class, 0);
        assertEquals(String.class, type);
    }

    @Test
    public void shouldReturnObjectClassForRawType() {
        Class<?> type = GenericsUtils.getSuperClassGenricType(RawChild.class);
        assertEquals(Object.class, type);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowForNegativeIndex() {
        GenericsUtils.getSuperClassGenricType(StringChild.class, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowForIndexOutOfBounds() {
        GenericsUtils.getSuperClassGenricType(StringChild.class, 5);
    }

    @Test
    public void shouldGetInterfaceGenericType() {
        Class<?> type = GenericsUtils.getSuperClassGenricType(InterfaceChild.class, GenericInterface.class);
        assertEquals(Long.class, type);
    }

    @Test
    public void shouldGetInterfaceGenericTypeByIndex() {
        Class<?> type = GenericsUtils.getSuperClassGenricType(InterfaceChild.class, GenericInterface.class, 0);
        assertEquals(Long.class, type);
    }

    @Test
    public void shouldReturnObjectClassWhenInterfaceNotParameterized() {
        // For a class that doesn't implement the given interface as parameterized
        Class<?> type = GenericsUtils.getSuperClassGenricType(StringChild.class, Serializable.class);
        assertEquals(Object.class, type);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowForInterfaceNegativeIndex() {
        GenericsUtils.getSuperClassGenricType(InterfaceChild.class, GenericInterface.class, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowForInterfaceIndexOutOfBounds() {
        GenericsUtils.getSuperClassGenricType(InterfaceChild.class, GenericInterface.class, 5);
    }
}
