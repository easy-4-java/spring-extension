package org.springframework.extension.web.servlet.mvc.bind;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

/**
 * Tests for {@link BindMap}.
 */
public class BindMapTest {

    @Test
    public void shouldCreateWithNullData() {
        BindMap map = new BindMap();
        assertNull(map.getData());
    }

    @Test
    public void shouldSetAndGetData() {
        BindMap map = new BindMap();
        Map<String, Object> data = new HashMap<>();
        data.put("key", "value");
        map.setData(data);
        assertNotNull(map.getData());
        assertEquals("value", map.getData().get("key"));
    }

    @Test
    public void shouldSetDataToNull() {
        BindMap map = new BindMap();
        Map<String, Object> data = new HashMap<>();
        data.put("key", "value");
        map.setData(data);
        map.setData(null);
        assertNull(map.getData());
    }
}
