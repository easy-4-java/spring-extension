package org.springframework.extension.config;

import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests for {@link Ini}.
 */
public class IniTest {

    @Test
    public void shouldCreateEmptyIni() {
        Ini ini = new Ini();
        assertNotNull(ini);
        assertTrue(ini.isEmpty());
    }

    @Test
    public void shouldCreateIniWithDefaults() {
        Ini defaults = new Ini();
        Ini ini = new Ini(defaults);
        assertNotNull(ini);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowWhenDefaultsIsNull() {
        new Ini(null);
    }

    @Test
    public void shouldLoadFromString() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\nkey2=value2\n");
        Ini.Section section = ini.getSection("section1");
        assertNotNull(section);
        assertEquals("value1", section.get("key1"));
        assertEquals("value2", section.get("key2"));
    }

    @Test
    public void shouldLoadFromReader() throws IOException {
        String content = "[section1]\nkey1=value1\nkey2=value2\n";
        Ini ini = new Ini();
        ini.load(new StringReader(content));
        Ini.Section section = ini.getSection("section1");
        assertNotNull(section);
        assertEquals("value1", section.get("key1"));
    }

    @Test
    public void shouldLoadWithComments() throws IOException {
        Ini ini = new Ini();
        ini.load("# comment\n; another comment\n[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        assertNotNull(section);
        assertEquals("value1", section.get("key1"));
    }

    @Test
    public void shouldGetSectionNames() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n[section2]\nkey2=value2\n");
        Set<String> names = ini.getSectionNames();
        assertNotNull(names);
        assertTrue(names.contains("section1"));
        assertTrue(names.contains("section2"));
    }

    @Test
    public void shouldGetSections() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n[section2]\nkey2=value2\n");
        Collection<Ini.Section> sections = ini.getSections();
        assertNotNull(sections);
        assertEquals(2, sections.size());
    }

    @Test
    public void shouldGetSection() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        assertNotNull(section);
        assertEquals("value1", section.get("key1"));
    }

    @Test
    public void shouldReturnNullForNonExistentSection() {
        Ini ini = new Ini();
        Ini.Section section = ini.getSection("nonexistent");
        assertNull(section);
    }

    @Test
    public void shouldAddSection() {
        Ini ini = new Ini();
        Ini.Section section = ini.addSection("section1");
        assertNotNull(section);
        assertEquals("section1", section.getName());
    }

    @Test
    public void shouldSetSectionProperty() throws IOException {
        Ini ini = new Ini();
        ini.setSectionProperty("section1", "key1", "value1");
        assertEquals("value1", ini.getSectionProperty("section1", "key1"));
    }

    @Test
    public void shouldGetSectionProperty() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        assertEquals("value1", ini.getSectionProperty("section1", "key1"));
    }

    @Test
    public void shouldGetSectionPropertyWithDefault() throws IOException {
        Ini ini = new Ini();
        assertEquals("default", ini.getSectionProperty("section1", "key1", "default"));
    }

    @Test
    public void shouldSupportMapOperations() {
        Ini ini = new Ini();
        assertTrue(ini.isEmpty());
        assertEquals(0, ini.size());
        assertTrue(ini.keySet().isEmpty());
        assertTrue(ini.values().isEmpty());
        assertTrue(ini.entrySet().isEmpty());
    }

    @Test
    public void shouldCheckContainsKey() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        assertTrue(ini.containsKey("section1"));
        assertFalse(ini.containsKey("nonexistent"));
    }

    @Test
    public void shouldCheckContainsValue() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        assertTrue(ini.containsValue(section));
    }

    @Test
    public void shouldGetSectionFromMap() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.get("section1");
        assertNotNull(section);
    }

    @Test
    public void shouldPutSection() {
        Ini ini = new Ini();
        Ini.Section section = ini.addSection("test");
        ini.put("test", section);
        assertEquals(1, ini.size());
    }

    @Test
    public void shouldRemoveSection() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        ini.remove("section1");
        assertTrue(ini.isEmpty());
    }

    @Test
    public void shouldRemoveSectionByName() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        ini.removeSection("section1");
        assertTrue(ini.isEmpty());
    }

    @Test
    public void shouldPutAll() {
        Ini ini = new Ini();
        Map<String, Ini.Section> map = new LinkedHashMap<>();
        Ini.Section section = ini.addSection("test");
        map.put("test", section);
        ini.putAll(map);
        assertEquals(1, ini.size());
    }

    @Test
    public void shouldClear() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        ini.clear();
        assertTrue(ini.isEmpty());
    }

    @Test
    public void shouldLoadFromInputStream() throws IOException {
        String content = "[section1]\nkey1=value1\n";
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        Ini ini = new Ini();
        ini.load(is);
        assertNotNull(ini.getSection("section1"));
    }

    @Test
    public void shouldLoadFromScanner() throws IOException {
        String content = "[section1]\nkey1=value1\n";
        Ini ini = new Ini();
        ini.load(new Scanner(content));
        assertNotNull(ini.getSection("section1"));
    }

    @Test
    public void shouldReturnDefaultSectionName() {
        assertEquals("", Ini.DEFAULT_SECTION_NAME);
    }

    @Test
    public void shouldReturnDefaultCharset() {
        assertEquals("UTF-8", Ini.DEFAULT_CHARSET_NAME);
    }

    @Test
    public void shouldReturnCommentConstants() {
        assertEquals("#", Ini.COMMENT_POUND);
        assertEquals(";", Ini.COMMENT_SEMICOLON);
    }

    @Test
    public void shouldReturnSectionConstants() {
        assertEquals("[", Ini.SECTION_PREFIX);
        assertEquals("]", Ini.SECTION_SUFFIX);
    }

    // Section tests
    @Test
    public void shouldGetSectionName() throws IOException {
        Ini ini = new Ini();
        ini.load("[test]\nkey=value\n");
        Ini.Section section = ini.getSection("test");
        assertNotNull(section);
        assertEquals("test", section.getName());
    }

    @Test
    public void shouldGetSectionSize() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\nkey2=value2\n");
        Ini.Section section = ini.getSection("section1");
        assertEquals(2, section.size());
    }

    @Test
    public void shouldCheckSectionIsEmpty() {
        Ini ini = new Ini();
        Ini.Section section = ini.addSection("test");
        assertTrue(section.isEmpty());
    }

    @Test
    public void shouldGetSectionKeys() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\nkey2=value2\n");
        Ini.Section section = ini.getSection("section1");
        Set<String> keys = section.keySet();
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
    }

    @Test
    public void shouldGetSectionValues() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\nkey2=value2\n");
        Ini.Section section = ini.getSection("section1");
        Collection<String> values = section.values();
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    public void shouldGetSectionEntrySet() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\nkey2=value2\n");
        Ini.Section section = ini.getSection("section1");
        Set<Map.Entry<String, String>> entries = section.entrySet();
        assertEquals(2, entries.size());
    }

    @Test
    public void shouldPutInSection() {
        Ini ini = new Ini();
        Ini.Section section = ini.addSection("test");
        section.put("key1", "value1");
        assertEquals("value1", section.get("key1"));
    }

    @Test
    public void shouldRemoveFromSection() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        section.remove("key1");
        assertTrue(section.isEmpty());
    }

    @Test
    public void shouldPutAllInSection() {
        Ini ini = new Ini();
        Ini.Section section = ini.addSection("test");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        section.putAll(map);
        assertEquals(2, section.size());
    }

    @Test
    public void shouldClearSection() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        section.clear();
        assertTrue(section.isEmpty());
    }

    @Test
    public void shouldCheckSectionContainsKey() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        assertTrue(section.containsKey("key1"));
        assertFalse(section.containsKey("nonexistent"));
    }

    @Test
    public void shouldCheckSectionContainsValue() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        assertTrue(section.containsValue("value1"));
        assertFalse(section.containsValue("nonexistent"));
    }

    @Test
    public void shouldGetSectionPropertyFromSection() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        assertEquals("value1", section.get("key1"));
    }

    @Test
    public void shouldReturnNullForMissingSectionKey() throws IOException {
        Ini ini = new Ini();
        ini.load("[section1]\nkey1=value1\n");
        Ini.Section section = ini.getSection("section1");
        assertNull(section.get("nonexistent"));
    }
}
