package org.springframework.extension.web.multipart.cos;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CosMultipartRequestTest {

    @Rule
    public TemporaryFolder temporary = new TemporaryFolder();

    @Test
    public void parsesJakartaRequestIntoCosFileAndFormField() throws Exception {
        String boundary = "cos-boundary";
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

        CosMultipartRequest parsed = new CosMultipartRequest(request, temporary.getRoot().getAbsolutePath(), 1024);
        assertEquals("hello", parsed.getParameter("note"));
        MultipartFile file = parsed.getFile("upload");
        assertNotNull(file);
        assertArrayEquals("content".getBytes(StandardCharsets.UTF_8), file.getBytes());
        assertEquals("test.txt", file.getOriginalFilename());
        assertEquals("test.txt", parsed.getOriginalFileName("upload"));
        assertNotNull(parsed.getOriginalFile("upload"));
    }
}
