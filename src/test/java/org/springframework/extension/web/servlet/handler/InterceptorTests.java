package org.springframework.extension.web.servlet.handler;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.*;

/**
 * Tests for servlet handler interceptor classes.
 */
public class InterceptorTests {

    // SpringMVCCORSInterceptor tests
    @Test
    public void shouldCreateCORSInterceptor() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        assertNotNull(interceptor);
    }

    @Test
    public void shouldHaveDefaultAllowOrigin() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        assertEquals("*", interceptor.getAllowOrigin());
    }

    @Test
    public void shouldSetAllowOrigin() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        interceptor.setAllowOrigin("http://example.com");
        assertEquals("http://example.com", interceptor.getAllowOrigin());
    }

    @Test
    public void shouldHaveDefaultAllowMethods() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        assertNotNull(interceptor.getAllowMethods());
        assertTrue(interceptor.getAllowMethods().contains("GET"));
    }

    @Test
    public void shouldSetAllowMethods() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        interceptor.setAllowMethods("GET, POST");
        assertEquals("GET, POST", interceptor.getAllowMethods());
    }

    @Test
    public void shouldHaveDefaultAllowHeaders() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        assertNotNull(interceptor.getAllowHeaders());
    }

    @Test
    public void shouldSetAllowHeaders() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        interceptor.setAllowHeaders("Content-Type");
        assertEquals("Content-Type", interceptor.getAllowHeaders());
    }

    @Test
    public void shouldHaveDefaultMaxAge() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        assertEquals("3600", interceptor.getMaxAge());
    }

    @Test
    public void shouldSetMaxAge() {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        interceptor.setMaxAge("7200");
        assertEquals("7200", interceptor.getMaxAge());
    }

    @Test
    public void shouldSetCorsHeadersOnAfterCompletion() throws Exception {
        SpringMVCCORSInterceptor interceptor = new SpringMVCCORSInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        interceptor.afterCompletion(request, response, null, null);
        assertEquals("*", response.getHeader("Access-Control-Allow-Origin"));
        assertNotNull(response.getHeader("Access-Control-Allow-Methods"));
        assertNotNull(response.getHeader("Access-Control-Allow-Headers"));
        assertEquals("3600", response.getHeader("Access-Control-Max-Age"));
    }

    // SpringMVCInterceptor tests
    @Test
    public void shouldCreateSpringMVCInterceptor() {
        SpringMVCInterceptor interceptor = new SpringMVCInterceptor();
        assertNotNull(interceptor);
    }

    @Test
    public void shouldReturnFalseFromPreHandle() throws Exception {
        SpringMVCInterceptor interceptor = new SpringMVCInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertFalse(interceptor.preHandle(request, response, null));
    }

    @Test
    public void shouldHandlePostHandle() throws Exception {
        SpringMVCInterceptor interceptor = new SpringMVCInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Should not throw
        interceptor.postHandle(request, response, null, null);
    }

    @Test
    public void shouldHandleAfterCompletion() throws Exception {
        SpringMVCInterceptor interceptor = new SpringMVCInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Should not throw
        interceptor.afterCompletion(request, response, null, null);
    }

    // ModuleChangeInterceptor tests
    @Test
    public void shouldCreateModuleChangeInterceptor() {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        assertNotNull(interceptor);
    }

    @Test
    public void shouldHaveDefaultParamName() {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        assertEquals("module", interceptor.getParamName());
    }

    @Test
    public void shouldSetParamName() {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        interceptor.setParamName("m");
        assertEquals("m", interceptor.getParamName());
    }

    @Test
    public void shouldSetAndGetHttpMethods() {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        interceptor.setHttpMethods("GET", "POST");
        String[] methods = interceptor.getHttpMethods();
        assertNotNull(methods);
        assertEquals(2, methods.length);
    }

    @Test
    public void shouldReturnNullHttpMethodsByDefault() {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        assertNull(interceptor.getHttpMethods());
    }

    @Test
    public void shouldPreHandleReturnTrue() throws Exception {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void shouldSetModuleFromParameter() throws Exception {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("module", "admin");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void shouldCheckHttpMethod() throws Exception {
        ModuleChangeInterceptor interceptor = new ModuleChangeInterceptor();
        interceptor.setHttpMethods("POST");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("GET");
        request.addParameter("module", "admin");
        MockHttpServletResponse response = new MockHttpServletResponse();
        assertTrue(interceptor.preHandle(request, response, null));
    }

    @Test
    public void shouldHaveConstantModuleName() {
        assertNotNull(ModuleChangeInterceptor.MODULE_SESSION_ATTRIBUTE_NAME);
        assertTrue(ModuleChangeInterceptor.MODULE_SESSION_ATTRIBUTE_NAME.contains("MODULE"));
    }

    @Test
    public void shouldHaveDefaultParamConstant() {
        assertEquals("module", ModuleChangeInterceptor.DEFAULT_PARAM_NAME);
    }
}
