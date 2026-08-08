package org.springframework.extension.context.event.handler;

import org.junit.Test;
import org.springframework.extension.context.event.EnhancedEvent;
import org.springframework.extension.context.event.ExceptionEvent;
import org.springframework.extension.context.event.aspect.*;

import static org.junit.Assert.*;

/**
 * Tests for event handler classes.
 */
public class EventHandlersTest {

    @Test
    public void shouldCreateJoinPointBeforeEventHandler() {
        JoinPointBeforeEventHandler handler = new JoinPointBeforeEventHandler();
        assertNotNull(handler);
        assertTrue(handler instanceof EventHandler);
    }

    @Test
    public void shouldCreateJoinPointAfterEventHandler() {
        JoinPointAfterEventHandler handler = new JoinPointAfterEventHandler();
        assertNotNull(handler);
        assertTrue(handler instanceof EventHandler);
    }

    @Test
    public void shouldCreateJoinPointAroundEventHandler() {
        JoinPointAroundEventHandler handler = new JoinPointAroundEventHandler();
        assertNotNull(handler);
        assertTrue(handler instanceof EventHandler);
    }

    @Test
    public void shouldCreateJoinPointThrowingEventHandler() {
        JoinPointThrowingEventHandler handler = new JoinPointThrowingEventHandler();
        assertNotNull(handler);
        assertTrue(handler instanceof EventHandler);
    }

    @Test
    public void shouldCreateExceptionEventHandler() {
        ExceptionEventHandler handler = new ExceptionEventHandler();
        assertNotNull(handler);
        assertTrue(handler instanceof EventHandler);
    }

    @Test
    public void shouldHandleJoinPointBeforeEvent() {
        JoinPointBeforeEventHandler handler = new JoinPointBeforeEventHandler();
        org.springframework.extension.context.event.EventInvocation invocation =
                new org.springframework.extension.context.event.EventInvocation(null);
        JoinPointBeforeEvent event = new JoinPointBeforeEvent("source", invocation);
        // Should not throw
        handler.handle(event);
    }

    @Test
    public void shouldHandleJoinPointAfterEvent() {
        JoinPointAfterEventHandler handler = new JoinPointAfterEventHandler();
        org.springframework.extension.context.event.EventInvocation invocation =
                new org.springframework.extension.context.event.EventInvocation(null);
        JoinPointAfterEvent event = new JoinPointAfterEvent("source", invocation);
        handler.handle(event);
    }

    @Test
    public void shouldHandleJoinPointAroundEvent() {
        JoinPointAroundEventHandler handler = new JoinPointAroundEventHandler();
        org.springframework.extension.context.event.EventInvocation invocation =
                new org.springframework.extension.context.event.EventInvocation(null);
        JoinPointAroundEvent event = new JoinPointAroundEvent("source", invocation);
        handler.handle(event);
    }

    @Test
    public void shouldHandleJoinPointThrowingEvent() {
        JoinPointThrowingEventHandler handler = new JoinPointThrowingEventHandler();
        org.springframework.extension.context.event.EventInvocation invocation =
                new org.springframework.extension.context.event.EventInvocation(null);
        JoinPointThrowingEvent event = new JoinPointThrowingEvent("source", invocation);
        handler.handle(event);
    }

    @Test
    public void shouldHandleExceptionEvent() {
        ExceptionEventHandler handler = new ExceptionEventHandler();
        ExceptionEvent event = new ExceptionEvent("source", new RuntimeException("test"));
        handler.handle(event);
    }
}
