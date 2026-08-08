package org.springframework.extension.context;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import static org.junit.Assert.*;

/**
 * Tests for {@link AbstractSpringInstanceContext}.
 */
public class AbstractSpringInstanceContextTest {

    @Test
    public void shouldCreateWithDefaultConstructor() {
        AbstractSpringInstanceContext ctx = new AbstractSpringInstanceContext();
        assertNotNull(ctx);
        assertNull(ctx.getApplicationContext());
    }

    @Test
    public void shouldSetAndGetApplicationContext() {
        AbstractSpringInstanceContext ctx = new AbstractSpringInstanceContext();
        ApplicationContext appCtx = new StaticApplicationContext();
        ctx.setApplicationContext(appCtx);
        assertSame(appCtx, ctx.getApplicationContext());
    }

    @Test
    public void shouldGetBeanByName() {
        StaticApplicationContext appCtx = new StaticApplicationContext();
        appCtx.registerSingleton("myBean", String.class);
        appCtx.refresh();

        AbstractSpringInstanceContext ctx = new AbstractSpringInstanceContext();
        ctx.setApplicationContext(appCtx);

        Object bean = ctx.getInstance("myBean");
        assertNotNull(bean);
    }
}
