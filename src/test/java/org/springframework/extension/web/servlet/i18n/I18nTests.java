package org.springframework.extension.web.servlet.i18n;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Tests for i18n locale resolver classes.
 */
public class I18nTests {

    // AcceptHeaderLocaleResolver tests
    @Test
    public void shouldCreateAcceptHeaderResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldResolveLocaleFromAcceptHeader() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en-US");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.US, locale);
    }

    @Test
    public void shouldReturnDefaultLocaleWhenNoHeader() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.CHINA);
        MockHttpServletRequest request = new MockHttpServletRequest();
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.CHINA, locale);
    }

    @Test
    public void shouldSetLocale() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolver.setLocale(request, response, Locale.US);
        // Should not throw
    }

    @Test
    public void shouldSetNullLocale() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Should not throw
        resolver.setLocale(request, response, null);
    }

    // XHeaderLocaleResolver tests
    @Test
    public void shouldCreateXHeaderResolver() {
        XHeaderLocaleResolver resolver = new XHeaderLocaleResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldResolveLocaleFromXLanguageHeader() {
        XHeaderLocaleResolver resolver = new XHeaderLocaleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Language", "zh-CN");
        Locale locale = resolver.resolveLocale(request);
        assertNotNull(locale);
    }

    @Test
    public void shouldReturnDefaultWhenNoXLanguageHeader() {
        XHeaderLocaleResolver resolver = new XHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        MockHttpServletRequest request = new MockHttpServletRequest();
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.US, locale);
    }

    @Test
    public void shouldResolveLocaleContext() {
        XHeaderLocaleResolver resolver = new XHeaderLocaleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Language", "en-US");
        request.addHeader("X-TimeZone", "America/New_York");
        assertNotNull(resolver.resolveLocaleContext(request));
    }

    @Test
    public void shouldSetLocaleContext() {
        XHeaderLocaleResolver resolver = new XHeaderLocaleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Should not throw with null
        resolver.setLocaleContext(request, response, null);
    }

    @Test
    public void shouldHaveXLanguageConstant() {
        assertEquals("X-Language", XHeaderLocaleResolver.X_LANGUAGE);
    }

    @Test
    public void shouldHaveXTimezoneConstant() {
        assertEquals("X-TimeZone", XHeaderLocaleResolver.X_TIMEZONE);
    }

    // NestedLocaleResolver tests
    @Test
    public void shouldCreateNestedResolver() {
        NestedLocaleResolver resolver = new NestedLocaleResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldReturnDefaultWhenNoResolvers() {
        NestedLocaleResolver resolver = new NestedLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        MockHttpServletRequest request = new MockHttpServletRequest();
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.US, locale);
    }

    @Test
    public void shouldSetAndGetResolvers() {
        NestedLocaleResolver resolver = new NestedLocaleResolver();
        java.util.List<org.springframework.web.servlet.LocaleResolver> resolvers = new java.util.ArrayList<>();
        resolvers.add(new AcceptHeaderLocaleResolver());
        resolver.setResolvers(resolvers);
        assertNotNull(resolver.getResolvers());
        assertEquals(1, resolver.getResolvers().size());
    }

    @Test
    public void shouldResolveWithNestedResolvers() {
        NestedLocaleResolver resolver = new NestedLocaleResolver();
        AcceptHeaderLocaleResolver acceptResolver = new AcceptHeaderLocaleResolver();
        acceptResolver.setDefaultLocale(Locale.US);
        java.util.List<org.springframework.web.servlet.LocaleResolver> resolvers = new java.util.ArrayList<>();
        resolvers.add(acceptResolver);
        resolver.setResolvers(resolvers);
        resolver.setDefaultLocale(Locale.CHINA);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept-Language", "en-US");
        Locale locale = resolver.resolveLocale(request);
        assertEquals(Locale.US, locale);
    }

    @Test
    public void shouldSetLocaleWithResolvers() {
        NestedLocaleResolver resolver = new NestedLocaleResolver();
        java.util.List<org.springframework.web.servlet.LocaleResolver> resolvers = new java.util.ArrayList<>();
        resolvers.add(new AcceptHeaderLocaleResolver());
        resolver.setResolvers(resolvers);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolver.setLocale(request, response, Locale.US);
        // Should not throw
    }

    @Test
    public void shouldNotSetLocaleWithoutResolvers() {
        NestedLocaleResolver resolver = new NestedLocaleResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Should not throw
        resolver.setLocale(request, response, Locale.US);
    }

    // LocaleContextFilter tests
    @Test
    public void shouldCreateLocaleContextFilter() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        LocaleContextFilter filter = new LocaleContextFilter(resolver);
        assertNotNull(filter);
    }
}
