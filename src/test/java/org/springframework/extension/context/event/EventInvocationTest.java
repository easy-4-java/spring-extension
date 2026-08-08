package org.springframework.extension.context.event;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Tests for {@link EventInvocation}.
 */
public class EventInvocationTest {

    @Test
    public void shouldCreateWithPoint() {
        EventInvocation invocation = new EventInvocation(null);
        assertNotNull(invocation);
        assertNull(invocation.getPoint());
    }

    @Test
    public void shouldCreateWithPointAndReturnValue() {
        EventInvocation invocation = new EventInvocation(null, "result");
        assertEquals("result", invocation.getReturnValue());
    }

    @Test
    public void shouldCreateWithPointAndThrowable() {
        Throwable t = new RuntimeException("test");
        EventInvocation invocation = new EventInvocation(null, t);
        assertSame(t, invocation.getThrowable());
    }

    @Test
    public void shouldSetAndGetTarget() {
        EventInvocation invocation = new EventInvocation(null);
        invocation.setTarget("target");
        assertEquals("target", invocation.getTarget());
    }

    @Test
    public void shouldSetAndGetMethod() throws Exception {
        EventInvocation invocation = new EventInvocation(null);
        Method method = String.class.getMethod("length");
        invocation.setMethod(method);
        assertSame(method, invocation.getMethod());
    }

    @Test
    public void shouldSetAndGetArgNames() {
        EventInvocation invocation = new EventInvocation(null);
        String[] names = {"a", "b"};
        invocation.setArgNames(names);
        assertArrayEquals(names, invocation.getArgNames());
    }

    @Test
    public void shouldSetAndGetArgs() {
        EventInvocation invocation = new EventInvocation(null);
        Object[] args = {1, "two"};
        invocation.setArgs(args);
        assertArrayEquals(args, invocation.getArgs());
    }

    @Test
    public void shouldSetAndGetReturnValue() {
        EventInvocation invocation = new EventInvocation(null);
        invocation.setReturnValue("result");
        assertEquals("result", invocation.getReturnValue());
    }

    @Test
    public void shouldSetAndGetThrowable() {
        EventInvocation invocation = new EventInvocation(null);
        Throwable t = new RuntimeException("test");
        invocation.setThrowable(t);
        assertSame(t, invocation.getThrowable());
    }

    @Test
    public void shouldSetAndGetPoint() {
        EventInvocation invocation = new EventInvocation(null);
        invocation.setPoint(null);
        assertNull(invocation.getPoint());
    }
}
