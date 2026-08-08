package org.springframework.extension.context.event.aspect;

import org.junit.Test;
import org.springframework.extension.context.event.EventInvocation;

import static org.junit.Assert.*;

/**
 * Tests for JoinPoint event classes.
 */
public class JoinPointEventsTest {

    @Test
    public void shouldCreateJoinPointBeforeEvent() {
        EventInvocation invocation = new EventInvocation(null);
        JoinPointBeforeEvent event = new JoinPointBeforeEvent("source", invocation);
        assertEquals("source", event.getSource());
        assertSame(invocation, event.getBind());
    }

    @Test
    public void shouldCreateJoinPointAfterEvent() {
        EventInvocation invocation = new EventInvocation(null);
        JoinPointAfterEvent event = new JoinPointAfterEvent("source", invocation);
        assertEquals("source", event.getSource());
        assertSame(invocation, event.getBind());
    }

    @Test
    public void shouldCreateJoinPointAroundEvent() {
        EventInvocation invocation = new EventInvocation(null);
        JoinPointAroundEvent event = new JoinPointAroundEvent("source", invocation);
        assertEquals("source", event.getSource());
        assertSame(invocation, event.getBind());
    }

    @Test
    public void shouldCreateJoinPointThrowingEvent() {
        EventInvocation invocation = new EventInvocation(null);
        JoinPointThrowingEvent event = new JoinPointThrowingEvent("source", invocation);
        assertEquals("source", event.getSource());
        assertSame(invocation, event.getBind());
    }

    @Test
    public void shouldExtendApplicationEvent() {
        EventInvocation invocation = new EventInvocation(null);
        JoinPointBeforeEvent event = new JoinPointBeforeEvent("source", invocation);
        assertTrue(event instanceof org.springframework.context.ApplicationEvent);
    }

    @Test
    public void shouldReturnBindFromGetBind() {
        EventInvocation invocation = new EventInvocation(null);
        invocation.setTarget("target");
        JoinPointBeforeEvent event = new JoinPointBeforeEvent("source", invocation);
        EventInvocation bind = event.getBind();
        assertNotNull(bind);
        assertEquals("target", bind.getTarget());
    }
}
