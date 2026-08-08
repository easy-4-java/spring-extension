package org.springframework.extension.web.servlet.support;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.Assert.*;

/**
 * Tests for {@link RequestContextUtils}.
 */
public class RequestContextUtilsTest {

    @Test
    public void shouldReturnNullModuleResolverWhenNotSet() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertNull(RequestContextUtils.getModuleResolver(request));
    }

    @Test
    public void shouldReturnNullModuleWhenNoResolver() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertNull(RequestContextUtils.getModule(request));
    }

    @Test
    public void shouldExtendSpringRequestContextUtils() {
        assertTrue(org.springframework.web.servlet.support.RequestContextUtils.class.isAssignableFrom(RequestContextUtils.class));
    }
}
