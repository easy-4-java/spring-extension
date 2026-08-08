package org.springframework.extension.context;

import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Tests for {@link NestedMessageSource}.
 */
public class NestedMessageSourceTest {

    @Test
    public void shouldCreateEmpty() {
        NestedMessageSource source = new NestedMessageSource();
        assertNotNull(source);
    }

    @Test
    public void shouldCreateWithSources() {
        StaticMessageSource s1 = new StaticMessageSource();
        s1.addMessage("key1", Locale.US, "value1");
        NestedMessageSource source = new NestedMessageSource(s1);
        assertNotNull(source);
    }

    @Test
    public void shouldGetMessageFromDelegate() {
        StaticMessageSource s1 = new StaticMessageSource();
        s1.addMessage("key1", Locale.US, "value1");
        NestedMessageSource source = new NestedMessageSource(s1);
        String msg = source.getMessage("key1", null, Locale.US);
        assertEquals("value1", msg);
    }

    @Test
    public void shouldReturnNullWhenNotFound() {
        StaticMessageSource s1 = new StaticMessageSource();
        NestedMessageSource source = new NestedMessageSource(s1);
        String msg = source.getMessage("nonexistent", null, "default", Locale.US);
        assertEquals("default", msg);
    }

    @Test
    public void shouldTryMultipleDelegates() {
        StaticMessageSource s1 = new StaticMessageSource();
        StaticMessageSource s2 = new StaticMessageSource();
        s2.addMessage("key2", Locale.US, "value2");
        NestedMessageSource source = new NestedMessageSource(s1, s2);
        String msg = source.getMessage("key2", null, Locale.US);
        assertEquals("value2", msg);
    }

    @Test
    public void shouldGetDelegates() {
        StaticMessageSource s1 = new StaticMessageSource();
        NestedMessageSource source = new NestedMessageSource(s1);
        MessageSource[] delegates = source.getDelegates();
        assertNotNull(delegates);
        assertEquals(1, delegates.length);
    }

    @Test
    public void shouldAddMessageSource() {
        NestedMessageSource source = new NestedMessageSource();
        StaticMessageSource s1 = new StaticMessageSource();
        source.addMessageSource(s1);
        MessageSource[] delegates = source.getDelegates();
        assertNotNull(delegates);
        assertEquals(1, delegates.length);
    }

    @Test
    public void shouldGetMessageWithArgs() {
        StaticMessageSource s1 = new StaticMessageSource();
        s1.addMessage("key1", Locale.US, "Hello {0}");
        NestedMessageSource source = new NestedMessageSource(s1);
        String msg = source.getMessage("key1", new Object[]{"World"}, Locale.US);
        assertEquals("Hello World", msg);
    }

    @Test
    public void shouldGetMessageWithResolvable() {
        StaticMessageSource s1 = new StaticMessageSource();
        s1.addMessage("key1", Locale.US, "value1");
        NestedMessageSource source = new NestedMessageSource(s1);
        MessageSourceResolvable resolvable = new org.springframework.context.support.DefaultMessageSourceResolvable("key1");
        String msg = source.getMessage(resolvable, Locale.US);
        assertEquals("value1", msg);
    }
}
