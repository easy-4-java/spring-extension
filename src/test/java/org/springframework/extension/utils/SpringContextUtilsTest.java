package org.springframework.extension.utils;

import org.junit.Test;
import org.springframework.extension.context.SpringContext;

import static org.junit.Assert.*;

/**
 * Tests for {@link SpringContextUtils}.
 */
public class SpringContextUtilsTest {

    @Test
    public void shouldGetContext() {
        SpringContext context = SpringContextUtils.getContext();
        assertNotNull(context);
    }

    @Test
    public void shouldSetContext() {
        SpringContext original = SpringContextUtils.getContext();
        try {
            SpringContext newContext = new org.springframework.extension.context.SpringClassPathXmlInstanceContext(new String[0]);
            SpringContextUtils.setContext(newContext);
            assertSame(newContext, SpringContextUtils.getContext());
        } finally {
            SpringContextUtils.setContext(original);
        }
    }

    @Test
    public void shouldHaveDefaultConstructor() {
        SpringContextUtils utils = new SpringContextUtils();
        assertNotNull(utils);
    }
}
