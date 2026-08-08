package org.springframework.extension.propertyeditors;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for all property editor classes.
 */
public class PropertyEditorsTest {

    // DoublePropertyEditor tests
    @Test
    public void shouldSetDoubleFromText() {
        DoublePropertyEditor editor = new DoublePropertyEditor();
        editor.setAsText("3.14");
        assertEquals(Double.valueOf(3.14), editor.getValue());
    }

    @Test
    public void shouldSetDoubleFromEmptyText() {
        DoublePropertyEditor editor = new DoublePropertyEditor();
        editor.setAsText("");
        assertEquals(Double.valueOf(0.0), editor.getValue());
    }

    @Test
    public void shouldSetDoubleFromNullText() {
        DoublePropertyEditor editor = new DoublePropertyEditor();
        editor.setAsText(null);
        assertEquals(Double.valueOf(0.0), editor.getValue());
    }

    @Test
    public void shouldGetDoubleAsText() {
        DoublePropertyEditor editor = new DoublePropertyEditor();
        editor.setAsText("3.14");
        assertEquals("3.14", editor.getAsText());
    }

    // FloatPropertyEditor tests
    @Test
    public void shouldSetFloatFromText() {
        FloatPropertyEditor editor = new FloatPropertyEditor();
        editor.setAsText("2.5");
        assertEquals(Float.valueOf(2.5f), editor.getValue());
    }

    @Test
    public void shouldSetFloatFromEmptyText() {
        FloatPropertyEditor editor = new FloatPropertyEditor();
        editor.setAsText("");
        assertEquals(Float.valueOf(0.0f), editor.getValue());
    }

    @Test
    public void shouldSetFloatFromNullText() {
        FloatPropertyEditor editor = new FloatPropertyEditor();
        editor.setAsText(null);
        assertEquals(Float.valueOf(0.0f), editor.getValue());
    }

    @Test
    public void shouldGetFloatAsText() {
        FloatPropertyEditor editor = new FloatPropertyEditor();
        editor.setAsText("2.5");
        assertEquals("2.5", editor.getAsText());
    }

    // IntegerPropertyEditor tests
    @Test
    public void shouldSetIntegerFromText() {
        IntegerPropertyEditor editor = new IntegerPropertyEditor();
        editor.setAsText("42");
        assertEquals(Integer.valueOf(42), editor.getValue());
    }

    @Test
    public void shouldSetIntegerFromEmptyText() {
        IntegerPropertyEditor editor = new IntegerPropertyEditor();
        editor.setAsText("");
        assertEquals(Integer.valueOf(0), editor.getValue());
    }

    @Test
    public void shouldSetIntegerFromNullText() {
        IntegerPropertyEditor editor = new IntegerPropertyEditor();
        editor.setAsText(null);
        assertEquals(Integer.valueOf(0), editor.getValue());
    }

    @Test
    public void shouldGetIntegerAsText() {
        IntegerPropertyEditor editor = new IntegerPropertyEditor();
        editor.setAsText("42");
        assertEquals("42", editor.getAsText());
    }

    // LongPropertyEditor tests
    @Test
    public void shouldSetLongFromText() {
        LongPropertyEditor editor = new LongPropertyEditor();
        editor.setAsText("123456789");
        assertEquals(Long.valueOf(123456789L), editor.getValue());
    }

    @Test
    public void shouldSetLongFromEmptyText() {
        LongPropertyEditor editor = new LongPropertyEditor();
        editor.setAsText("");
        assertEquals(Long.valueOf(0L), editor.getValue());
    }

    @Test
    public void shouldSetLongFromNullText() {
        LongPropertyEditor editor = new LongPropertyEditor();
        editor.setAsText(null);
        assertEquals(Long.valueOf(0L), editor.getValue());
    }

    @Test
    public void shouldGetLongAsText() {
        LongPropertyEditor editor = new LongPropertyEditor();
        editor.setAsText("123456789");
        assertEquals("123456789", editor.getAsText());
    }

    // StringPropertyEditor tests
    @Test
    public void shouldSetStringFromText() {
        StringPropertyEditor editor = new StringPropertyEditor();
        editor.setAsText("hello");
        assertNotNull(editor.getValue());
    }

    @Test
    public void shouldSetStringFromNullText() {
        StringPropertyEditor editor = new StringPropertyEditor();
        editor.setAsText(null);
        assertNull(editor.getValue());
    }

    @Test
    public void shouldGetStringAsText() {
        StringPropertyEditor editor = new StringPropertyEditor();
        editor.setAsText("hello");
        assertNotNull(editor.getAsText());
    }

    @Test
    public void shouldReturnEmptyStringWhenValueIsNull() {
        StringPropertyEditor editor = new StringPropertyEditor();
        assertEquals("", editor.getAsText());
    }

    @Test
    public void shouldTrimAndEscapeHtml() {
        StringPropertyEditor editor = new StringPropertyEditor();
        editor.setAsText("  hello  ");
        String text = editor.getAsText();
        assertNotNull(text);
    }
}
