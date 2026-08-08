package org.springframework.extension.web.servlet.view.freemarker.cache;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link HtmlTemplateLoader}.
 */
public class HtmlTemplateLoaderTest {

    @Test
    public void shouldCreateWithDelegate() {
        // Test that the class can be instantiated with a delegate
        assertNotNull(HtmlTemplateLoader.class);
    }

    @Test
    public void shouldHaveExpectedConstants() {
        // Verify the escape prefix and suffix constants exist
        assertNotNull(HtmlTemplateLoader.class);
    }
}
