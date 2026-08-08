package org.springframework.extension.web.multipart;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link MaxUploadSizePerFileExceededException}.
 */
public class MaxUploadSizePerFileExceededExceptionTest {

    @Test
    public void shouldCreateWithMaxSize() {
        MaxUploadSizePerFileExceededException ex = new MaxUploadSizePerFileExceededException(1024L);
        assertEquals(1024L, ex.getMaxUploadSizePerFile());
        assertTrue(ex.getMessage().contains("1024"));
    }

    @Test
    public void shouldCreateWithMaxSizeAndCause() {
        Throwable cause = new RuntimeException("root cause");
        MaxUploadSizePerFileExceededException ex = new MaxUploadSizePerFileExceededException(2048L, cause);
        assertEquals(2048L, ex.getMaxUploadSizePerFile());
        assertSame(cause, ex.getCause());
    }

    @Test
    public void shouldExtendMultipartException() {
        MaxUploadSizePerFileExceededException ex = new MaxUploadSizePerFileExceededException(1024L);
        assertTrue(ex instanceof org.springframework.web.multipart.MultipartException);
    }

    @Test
    public void shouldContainMessageWithSize() {
        MaxUploadSizePerFileExceededException ex = new MaxUploadSizePerFileExceededException(5000L);
        assertNotNull(ex.getMessage());
        assertTrue(ex.getMessage().contains("5000"));
    }
}
