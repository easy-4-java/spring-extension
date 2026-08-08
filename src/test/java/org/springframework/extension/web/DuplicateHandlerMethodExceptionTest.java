package org.springframework.extension.web;

import org.junit.Test;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for {@link DuplicateHandlerMethodException}.
 */
public class DuplicateHandlerMethodExceptionTest {

    @Test
    public void shouldCreateWithMappingAndMethods() {
        List<HandlerMethod> methods = new ArrayList<>();
        DuplicateHandlerMethodException ex = new DuplicateHandlerMethodException("/api/test", methods);
        assertEquals("/api/test", ex.getMapping());
        assertSame(methods, ex.getHandlerMethods());
        assertTrue(ex.getMessage().contains("/api/test"));
    }

    @Test
    public void shouldCreateWithMappingMethodsAndCause() {
        List<HandlerMethod> methods = new ArrayList<>();
        Throwable cause = new RuntimeException("root");
        DuplicateHandlerMethodException ex = new DuplicateHandlerMethodException("/api/test", methods, cause);
        assertEquals("/api/test", ex.getMapping());
        assertSame(methods, ex.getHandlerMethods());
        assertSame(cause, ex.getCause());
    }

    @Test
    public void shouldExtendServletException() {
        List<HandlerMethod> methods = new ArrayList<>();
        DuplicateHandlerMethodException ex = new DuplicateHandlerMethodException("/api/test", methods);
        assertTrue(ex instanceof javax.servlet.ServletException);
    }
}
