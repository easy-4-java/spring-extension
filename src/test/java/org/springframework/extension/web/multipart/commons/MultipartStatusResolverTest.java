package org.springframework.extension.web.multipart.commons;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MultipartStatusResolverTest {

    @Test
    public void parsesFieldsAndFilesAndReportsProgress() throws Exception {
        MockHttpServletRequest request = uploadRequest();
        MultipartStatusResolver resolver = new MultipartStatusResolver();

        assertTrue(resolver.isMultipart(request));
        MultipartHttpServletRequest parsed = resolver.resolveMultipart(request);
        assertEquals("hello", parsed.getParameter("note"));
        assertNotNull(parsed.getFile("upload"));
        assertEquals("test.txt", parsed.getFile("upload").getOriginalFilename());
        assertArrayEquals("content".getBytes(StandardCharsets.UTF_8), parsed.getFile("upload").getBytes());
        assertEquals("100%", request.getSession().getAttribute("percent"));
        resolver.cleanupMultipart(parsed);
    }

    @Test
    public void rejectsRequestBeyondConfiguredLimit() {
        MultipartStatusResolver resolver = new MultipartStatusResolver();
        resolver.setMaxUploadSize(10);
        try {
            resolver.resolveMultipart(uploadRequest());
            fail("oversized request should be rejected");
        } catch (MaxUploadSizeExceededException expected) {
            assertEquals(10L, expected.getMaxUploadSize());
        }
    }

    private MockHttpServletRequest uploadRequest() {
        String boundary = "ddd4j-boundary";
        String body = "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"note\"\r\n\r\n"
                + "hello\r\n"
                + "--" + boundary + "\r\n"
                + "Content-Disposition: form-data; name=\"upload\"; filename=\"test.txt\"\r\n"
                + "Content-Type: text/plain\r\n\r\n"
                + "content\r\n"
                + "--" + boundary + "--\r\n";
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/upload");
        request.setContentType("multipart/form-data; boundary=" + boundary);
        request.setContent(body.getBytes(StandardCharsets.UTF_8));
        return request;
    }
}
