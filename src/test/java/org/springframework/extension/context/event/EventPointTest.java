package org.springframework.extension.context.event;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests for {@link EventPoint}.
 */
public class EventPointTest {

    @Test
    public void shouldCreateWithUidAndMessage() {
        EventPoint point = new EventPoint("uid1", "test message");
        assertEquals("uid1", point.getUid());
        assertEquals("test message", point.getMessage());
        assertNotNull(point.getData());
        assertTrue(point.getData().isEmpty());
    }

    @Test
    public void shouldCreateWithPrevUidAndMessage() {
        EventPoint prev = new EventPoint("prev", "prev msg");
        EventPoint point = new EventPoint(prev, "uid1", "test message");
        assertSame(prev, point.getPrev());
        assertEquals("uid1", point.getUid());
    }

    @Test
    public void shouldCreateWithUidTimestampAndMessage() {
        EventPoint point = new EventPoint("uid1", 12345L, "test message");
        assertEquals("uid1", point.getUid());
        assertEquals(12345L, point.getTimestamp());
        assertEquals("test message", point.getMessage());
    }

    @Test
    public void shouldCreateFullConstructor() {
        Map<String, Object> data = new HashMap<>();
        data.put("key", "value");
        EventPoint point = new EventPoint(null, "uid1", 12345L, "msg", data);
        assertEquals("uid1", point.getUid());
        assertEquals(12345L, point.getTimestamp());
        assertEquals("msg", point.getMessage());
        assertEquals("value", point.getData().get("key"));
    }

    @Test
    public void shouldCreateWithUidMessageAndData() {
        Map<String, Object> data = new HashMap<>();
        data.put("k", "v");
        EventPoint point = new EventPoint("uid1", "msg", data);
        assertEquals("v", point.getData().get("k"));
    }

    @Test
    public void shouldCreateWithPrevUidMessageAndData() {
        Map<String, Object> data = new HashMap<>();
        EventPoint point = new EventPoint(null, "uid1", "msg", data);
        assertEquals("uid1", point.getUid());
    }

    @Test
    public void shouldCreateWithNullData() {
        EventPoint point = new EventPoint(null, "uid1", 12345L, "msg", null);
        assertNotNull(point.getData());
        assertTrue(point.getData().isEmpty());
    }

    @Test
    public void shouldSetAndGetPrev() {
        EventPoint point = new EventPoint("uid1", "msg");
        EventPoint prev = new EventPoint("prev", "prev msg");
        point.setPrev(prev);
        assertSame(prev, point.getPrev());
    }

    @Test
    public void shouldSetAndGetUid() {
        EventPoint point = new EventPoint("uid1", "msg");
        point.setUid("newUid");
        assertEquals("newUid", point.getUid());
    }

    @Test
    public void shouldSetAndGetTimestamp() {
        EventPoint point = new EventPoint("uid1", "msg");
        point.setTimestamp(999L);
        assertEquals(999L, point.getTimestamp());
    }

    @Test
    public void shouldSetAndGetMessage() {
        EventPoint point = new EventPoint("uid1", "msg");
        point.setMessage("new msg");
        assertEquals("new msg", point.getMessage());
    }

    @Test
    public void shouldSetAndGetData() {
        EventPoint point = new EventPoint("uid1", "msg");
        Map<String, Object> data = new HashMap<>();
        data.put("k", "v");
        point.setData(data);
        assertEquals("v", point.getData().get("k"));
    }

    @Test
    public void shouldPutData() {
        EventPoint point = new EventPoint("uid1", "msg");
        point.put("key", "value");
        assertEquals("value", point.getData().get("key"));
    }

    @Test
    public void shouldHaveRootConstant() {
        assertNotNull(EventPoint.ROOT);
        assertEquals("root", EventPoint.ROOT.getUid());
    }
}
