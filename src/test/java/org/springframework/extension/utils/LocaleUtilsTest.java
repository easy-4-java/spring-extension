package org.springframework.extension.utils;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Tests for {@link LocaleUtils}.
 */
public class LocaleUtilsTest {

    @Test
    public void shouldGetDefaultLocale() {
        Locale locale = LocaleUtils.getLocale();
        assertNotNull(locale);
        assertEquals(Locale.SIMPLIFIED_CHINESE, locale);
    }

    @Test
    public void shouldSetAndGetLocale() {
        Locale original = LocaleUtils.getLocale();
        try {
            LocaleUtils.setLocale(Locale.US);
            assertEquals(Locale.US, LocaleUtils.getLocale());
        } finally {
            LocaleUtils.setLocale(original);
        }
    }

    @Test
    public void shouldGetLocaleFromRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Locale locale = LocaleUtils.getLocale(request);
        assertNotNull(locale);
    }

    @Test
    public void shouldGetLocaleKeyFromRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String key = LocaleUtils.getLocaleKey(request);
        assertNotNull(key);
    }

    @Test
    public void shouldGetLocaleKeyWithSessionLocale() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute(LocaleUtils.SESSION_KEY, Locale.US);
        String key = LocaleUtils.getLocaleKey(request);
        assertEquals("en_US", key);
    }

    @Test
    public void shouldGetRequestLocale() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("language", "en_US");
        Locale locale = LocaleUtils.getRequestLocale(request);
        assertEquals("en", locale.getLanguage());
        assertEquals("US", locale.getCountry());
    }

    @Test
    public void shouldGetDefaultRequestLocale() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Locale locale = LocaleUtils.getRequestLocale(request);
        assertNotNull(locale);
    }

    @Test
    public void shouldSetSessionLocale() {
        MockHttpSession session = new MockHttpSession();
        LocaleUtils.setSessionLocale(session, Locale.US);
        assertEquals(Locale.US, session.getAttribute(LocaleUtils.SESSION_KEY));
    }

    @Test
    public void shouldSetSessionLocaleWithDefault() {
        MockHttpSession session = new MockHttpSession();
        LocaleUtils.setSessionLocale(session, null);
        assertNotNull(session.getAttribute(LocaleUtils.SESSION_KEY));
    }

    @Test
    public void shouldSetSessionLocaleFromExisting() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(LocaleUtils.SESSION_KEY, Locale.US);
        LocaleUtils.setSessionLocale(session);
        assertEquals(Locale.US, session.getAttribute(LocaleUtils.SESSION_KEY));
    }

    @Test
    public void shouldSetSessionLocaleWhenNoneExisting() {
        MockHttpSession session = new MockHttpSession();
        LocaleUtils.setSessionLocale(session);
        assertNotNull(session.getAttribute(LocaleUtils.SESSION_KEY));
    }

    @Test
    public void shouldGetSessionLocale() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(LocaleUtils.SESSION_KEY, Locale.US);
        Locale locale = LocaleUtils.getSessionLocale(session);
        assertEquals(Locale.US, locale);
    }

    @Test
    public void shouldReturnNullWhenNoSessionLocale() {
        MockHttpSession session = new MockHttpSession();
        Locale locale = LocaleUtils.getSessionLocale(session);
        assertNull(locale);
    }

    @Test
    public void shouldInterceptLocale() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Locale locale = LocaleUtils.interceptLocale(request);
        assertNotNull(locale);
    }

    @Test
    public void shouldInterceptLocaleWithSessionLocale() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute(LocaleUtils.SESSION_KEY, Locale.US);
        Locale locale = LocaleUtils.interceptLocale(request);
        assertEquals(Locale.US, locale);
    }

    @Test
    public void shouldGetLocalePath() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String path = LocaleUtils.getLocalePath(request, "/templates/test.html");
        assertNotNull(path);
    }

    @Test
    public void shouldGetLocalePathWithLocale() {
        String path = LocaleUtils.getLocalePath("en_US", "/templates/test.html");
        assertNotNull(path);
    }

    @Test
    public void shouldGetLocalePathWithNullLocale() {
        String path = LocaleUtils.getLocalePath((String) null, "/templates/test.html");
        assertEquals("/templates/test.html", path);
    }

    @Test
    public void shouldHaveConstants() {
        assertEquals("WW_TRANS_I18N_LOCALE", LocaleUtils.SESSION_KEY);
        assertEquals("zh_CN", LocaleUtils.DEFAULT_LANGUAGE);
        assertEquals(Locale.SIMPLIFIED_CHINESE, LocaleUtils.DEFAULT_LOCALE);
        assertEquals("language", LocaleUtils.STATCK_KEY);
    }
}
