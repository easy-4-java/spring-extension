package org.springframework.extension.web.servlet.module;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.*;

/**
 * Tests for module resolver classes.
 */
public class ModuleResolverTests {

    // AbstractModuleResolver tests via FixedModuleResolver
    @Test
    public void shouldHaveDefaultModuleName() {
        FixedModuleResolver resolver = new FixedModuleResolver();
        assertEquals("default", resolver.getDefaultModule());
    }

    @Test
    public void shouldSetDefaultModule() {
        FixedModuleResolver resolver = new FixedModuleResolver();
        resolver.setDefaultModule("admin");
        assertEquals("admin", resolver.getDefaultModule());
    }

    @Test
    public void shouldHaveOriginalDefaultConstant() {
        assertEquals("default", AbstractModuleResolver.ORIGINAL_DEFAULT_MODULE_NAME);
    }

    // FixedModuleResolver tests
    @Test
    public void shouldCreateFixedModuleResolver() {
        FixedModuleResolver resolver = new FixedModuleResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldResolveToDefaultModule() {
        FixedModuleResolver resolver = new FixedModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals("default", resolver.resolveModule(request));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowOnSetModule() {
        FixedModuleResolver resolver = new FixedModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolver.setModule(request, response, "admin");
    }

    // AcceptHeaderModuleResolver tests
    @Test
    public void shouldCreateAcceptHeaderModuleResolver() {
        AcceptHeaderModuleResolver resolver = new AcceptHeaderModuleResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldResolveFromAcceptModuleHeader() {
        AcceptHeaderModuleResolver resolver = new AcceptHeaderModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Module", "admin");
        assertEquals("admin", resolver.resolveModule(request));
    }

    @Test
    public void shouldResolveToDefaultWhenNoHeader() {
        AcceptHeaderModuleResolver resolver = new AcceptHeaderModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals("default", resolver.resolveModule(request));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowOnSetModuleForAcceptHeader() {
        AcceptHeaderModuleResolver resolver = new AcceptHeaderModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolver.setModule(request, response, "admin");
    }

    // SessionModuleResolver tests
    @Test
    public void shouldCreateSessionModuleResolver() {
        SessionModuleResolver resolver = new SessionModuleResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldResolveFromSession() {
        SessionModuleResolver resolver = new SessionModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute(SessionModuleResolver.MODULE_SESSION_ATTRIBUTE_NAME, "admin");
        assertEquals("admin", resolver.resolveModule(request));
    }

    @Test
    public void shouldResolveToDefaultWhenNoSessionAttribute() {
        SessionModuleResolver resolver = new SessionModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals("default", resolver.resolveModule(request));
    }

    @Test
    public void shouldSetModuleInSession() {
        SessionModuleResolver resolver = new SessionModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolver.setModule(request, response, "admin");
        assertEquals("admin", request.getSession().getAttribute(SessionModuleResolver.MODULE_SESSION_ATTRIBUTE_NAME));
    }

    @Test
    public void shouldClearModuleWhenEmpty() {
        SessionModuleResolver resolver = new SessionModuleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolver.setModule(request, response, "");
        assertNull(request.getSession().getAttribute(SessionModuleResolver.MODULE_SESSION_ATTRIBUTE_NAME));
    }

    @Test
    public void shouldHaveSessionAttributeNameConstant() {
        assertNotNull(SessionModuleResolver.MODULE_SESSION_ATTRIBUTE_NAME);
        assertTrue(SessionModuleResolver.MODULE_SESSION_ATTRIBUTE_NAME.contains("MODULE"));
    }
}
