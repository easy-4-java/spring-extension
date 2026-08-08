package org.springframework.extension.web.servlet.theme;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.theme.CookieThemeResolver;
import org.springframework.web.servlet.theme.SessionThemeResolver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests for {@link NestedThemeResolver}.
 */
public class NestedThemeResolverTest {

    @Test
    public void shouldCreateNestedThemeResolver() {
        NestedThemeResolver resolver = new NestedThemeResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldReturnDefaultThemeWhenNoResolvers() {
        NestedThemeResolver resolver = new NestedThemeResolver();
        resolver.setDefaultThemeName("myTheme");
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertEquals("myTheme", resolver.resolveThemeName(request));
    }

    @Test
    public void shouldSetAndGetResolvers() {
        NestedThemeResolver resolver = new NestedThemeResolver();
        List<ThemeResolver> resolvers = new ArrayList<>();
        resolvers.add(new SessionThemeResolver());
        resolver.setResolvers(resolvers);
        assertNotNull(resolver.getResolvers());
        assertEquals(1, resolver.getResolvers().size());
    }

    @Test
    public void shouldResolveWithNestedResolvers() {
        NestedThemeResolver resolver = new NestedThemeResolver();
        SessionThemeResolver sessionResolver = new SessionThemeResolver();
        List<ThemeResolver> resolvers = new ArrayList<>();
        resolvers.add(sessionResolver);
        resolver.setResolvers(resolvers);
        resolver.setDefaultThemeName("default");

        MockHttpServletRequest request = new MockHttpServletRequest();
        String theme = resolver.resolveThemeName(request);
        assertNotNull(theme);
    }

    @Test
    public void shouldSetThemeWithResolvers() {
        NestedThemeResolver resolver = new NestedThemeResolver();
        SessionThemeResolver sessionResolver = new SessionThemeResolver();
        List<ThemeResolver> resolvers = new ArrayList<>();
        resolvers.add(sessionResolver);
        resolver.setResolvers(resolvers);

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        resolver.setThemeName(request, response, "newTheme");
        // Should not throw
    }

    @Test
    public void shouldNotSetThemeWithoutResolvers() {
        NestedThemeResolver resolver = new NestedThemeResolver();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Should not throw
        resolver.setThemeName(request, response, "newTheme");
    }

    @Test
    public void shouldHaveDefaultThemeName() {
        NestedThemeResolver resolver = new NestedThemeResolver();
        assertNotNull(resolver.getDefaultThemeName());
    }
}
