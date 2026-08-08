package org.springframework.extension.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link StringUtils}.
 */
public class StringUtilsTest {

    @Test
    public void shouldTokenizeWithDefaultDelimiters() {
        String[] result = StringUtils.tokenizeToStringArray("a,b;c d");
        assertNotNull(result);
        assertEquals(4, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
        assertEquals("d", result[3]);
    }

    @Test
    public void shouldTokenizeEmptyString() {
        String[] result = StringUtils.tokenizeToStringArray("");
        assertNotNull(result);
        assertEquals(0, result.length);
    }

    @Test
    public void shouldTokenizeWithCustomDelimiters() {
        String[] result = StringUtils.tokenizeToStringArray("a|b|c", "|");
        assertNotNull(result);
        assertEquals(3, result.length);
    }

    @Test
    public void shouldHaveDefaultDelimiters() {
        assertNotNull(StringUtils.CONFIG_LOCATION_DELIMITERS);
        assertTrue(StringUtils.CONFIG_LOCATION_DELIMITERS.contains(","));
        assertTrue(StringUtils.CONFIG_LOCATION_DELIMITERS.contains(";"));
    }
}
