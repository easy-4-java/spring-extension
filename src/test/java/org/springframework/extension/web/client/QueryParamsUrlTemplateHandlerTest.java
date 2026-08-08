package org.springframework.extension.web.client;

import org.junit.Test;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests for {@link QueryParamsUrlTemplateHandler}.
 */
public class QueryParamsUrlTemplateHandlerTest {

    @Test
    public void shouldCreateHandler() {
        QueryParamsUrlTemplateHandler handler = new QueryParamsUrlTemplateHandler();
        assertNotNull(handler);
    }

    @Test
    public void shouldDefaultToNonStrictEncoding() {
        QueryParamsUrlTemplateHandler handler = new QueryParamsUrlTemplateHandler();
        assertFalse(handler.isStrictEncoding());
    }

    @Test
    public void shouldSetStrictEncoding() {
        QueryParamsUrlTemplateHandler handler = new QueryParamsUrlTemplateHandler();
        handler.setStrictEncoding(true);
        assertTrue(handler.isStrictEncoding());
    }

    @Test
    public void shouldExpandWithMapVariables() {
        QueryParamsUrlTemplateHandler handler = new QueryParamsUrlTemplateHandler();
        Map<String, String> vars = new HashMap<>();
        vars.put("key", "value");
        URI result = handler.expand("http://example.com/api", vars);
        assertNotNull(result);
    }

    @Test
    public void shouldExpandWithVarargsVariables() {
        QueryParamsUrlTemplateHandler handler = new QueryParamsUrlTemplateHandler();
        URI result = handler.expand("http://example.com/api");
        assertNull(result);
    }

    @Test
    public void shouldExpandWithMultipleParams() {
        QueryParamsUrlTemplateHandler handler = new QueryParamsUrlTemplateHandler();
        Map<String, String> vars = new HashMap<>();
        vars.put("a", "1");
        vars.put("b", "2");
        URI result = handler.expand("http://example.com/api", vars);
        assertNotNull(result);
    }
}
