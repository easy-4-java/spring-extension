package org.springframework.extension.context.event;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link EnhancedEvent} and {@link ExceptionEvent}.
 */
public class EnhancedEventTest {

    @Test
    public void shouldCreateEnhancedEvent() {
        EnhancedEvent<String> event = new EnhancedEvent<>("source", "bindValue");
        assertEquals("source", event.getSource());
        assertEquals("bindValue", event.getBind());
    }

    @Test
    public void shouldCreateWithNullBind() {
        EnhancedEvent<String> event = new EnhancedEvent<>("source", null);
        assertNull(event.getBind());
    }

    @Test
    public void shouldCreateExceptionEvent() {
        Throwable cause = new RuntimeException("test error");
        ExceptionEvent event = new ExceptionEvent("source", cause);
        assertEquals("source", event.getSource());
        assertSame(cause, event.getBind());
    }

    @Test
    public void shouldGetBindAsThrowable() {
        Throwable cause = new RuntimeException("test");
        ExceptionEvent event = new ExceptionEvent("source", cause);
        Throwable bind = event.getBind();
        assertNotNull(bind);
        assertEquals("test", bind.getMessage());
    }
}
