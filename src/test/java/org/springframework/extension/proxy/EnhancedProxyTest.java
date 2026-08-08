package org.springframework.extension.proxy;

import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.junit.Assert.*;

/**
 * Tests for {@link EnhancedProxy}.
 */
public class EnhancedProxyTest {

    interface TestInterface {
        String doSomething();
    }

    @Test
    public void shouldImplementInvocationHandler() {
        EnhancedProxy proxy = new EnhancedProxy();
        assertTrue(proxy instanceof InvocationHandler);
    }

    @Test
    public void shouldImplementSerializable() {
        EnhancedProxy proxy = new EnhancedProxy();
        assertTrue(proxy instanceof Serializable);
    }

    @Test
    public void shouldReturnNullForNonObjectMethods() throws Throwable {
        EnhancedProxy handler = new EnhancedProxy();
        // For Object methods like toString, hashCode, etc., it returns null
        Method toStringMethod = Object.class.getMethod("toString");
        Object result = handler.invoke(null, toStringMethod, null);
        assertNull(result);
    }

    @Test
    public void shouldHaveSerialVersionUID() throws Exception {
        java.lang.reflect.Field field = EnhancedProxy.class.getDeclaredField("serialVersionUID");
        field.setAccessible(true);
        long uid = field.getLong(null);
        assertEquals(1993713162421775843L, uid);
    }
}
