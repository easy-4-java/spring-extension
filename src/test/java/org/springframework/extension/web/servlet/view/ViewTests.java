package org.springframework.extension.web.servlet.view;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.*;

/**
 * Tests for view classes.
 */
public class ViewTests {

    // StaticResourceView tests
    @Test
    public void shouldCreateStaticResourceView() {
        StaticResourceView view = new StaticResourceView("content");
        assertNotNull(view);
    }

    @Test
    public void shouldCreateWithContentType() {
        StaticResourceView view = new StaticResourceView("content", "text/html");
        assertEquals("text/html", view.getContentType());
    }

    @Test
    public void shouldSetContentType() {
        StaticResourceView view = new StaticResourceView("content");
        view.setContentType("text/plain");
        assertEquals("text/plain", view.getContentType());
    }

    @Test
    public void shouldReturnNullContentTypeByDefault() {
        StaticResourceView view = new StaticResourceView("content");
        assertNull(view.getContentType());
    }

    @Test
    public void shouldRenderContent() throws Exception {
        StaticResourceView view = new StaticResourceView("hello", "text/plain");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        view.render(null, request, response);
        assertEquals("text/plain", response.getContentType());
        assertEquals("hello", response.getContentAsString());
    }

    // HtmlResourceView tests
    @Test
    public void shouldCreateHtmlResourceView() {
        HtmlResourceView view = new HtmlResourceView("<html></html>");
        assertNotNull(view);
        assertEquals("text/html", view.getContentType());
    }

    @Test
    public void shouldRenderHtmlContent() throws Exception {
        HtmlResourceView view = new HtmlResourceView("<h1>Hello</h1>");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        view.render(null, request, response);
        assertTrue(response.getContentAsString().contains("<h1>Hello</h1>"));
    }

    // JSONResourceView tests
    @Test
    public void shouldCreateJsonResourceView() {
        JSONResourceView view = new JSONResourceView("{\"key\":\"value\"}");
        assertNotNull(view);
        assertNotNull(view.getContentType());
    }

    // TextResourceView tests
    @Test
    public void shouldCreateTextResourceView() {
        TextResourceView view = new TextResourceView("plain text");
        assertNotNull(view);
        assertEquals("text/plain", view.getContentType());
    }

    // XMLResourceView tests
    @Test
    public void shouldCreateXmlResourceView() {
        XMLResourceView view = new XMLResourceView("<root/>");
        assertNotNull(view);
        assertEquals("application/xml", view.getContentType());
    }
}
