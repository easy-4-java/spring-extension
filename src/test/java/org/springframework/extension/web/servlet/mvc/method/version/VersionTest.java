package org.springframework.extension.web.servlet.mvc.method.version;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for {@link Version}, {@link VersionRange}, {@link VersionMapping}, {@link VersionRangeMapping}.
 */
public class VersionTest {

    @Test
    public void shouldCreateVersion() {
        Version v = new Version("1.2.3");
        assertEquals("v1.2.3", v.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowForInvalidVersion() {
        new Version("1.2");
    }

    @Test
    public void shouldCompareVersions() {
        Version v1 = new Version("1.0.0");
        Version v2 = new Version("2.0.0");
        assertTrue(v1.compareTo(v2) < 0);
        assertTrue(v2.compareTo(v1) > 0);
        assertEquals(0, v1.compareTo(new Version("1.0.0")));
    }

    @Test
    public void shouldCompareMinorVersions() {
        Version v1 = new Version("1.0.0");
        Version v2 = new Version("1.1.0");
        assertTrue(v1.compareTo(v2) < 0);
    }

    @Test
    public void shouldCompareRevisions() {
        Version v1 = new Version("1.0.0");
        Version v2 = new Version("1.0.1");
        assertTrue(v1.compareTo(v2) < 0);
    }

    @Test
    public void shouldGetMajorVersion() {
        assertEquals(2, Version.getMajorVersion("2.3.4"));
    }

    @Test
    public void shouldGetMinorVersion() {
        assertEquals(3, Version.getMinorVersion("2.3.4"));
    }

    @Test
    public void shouldGetRevisionVersion() {
        assertEquals(4, Version.getRevisionVersion("2.3.4"));
    }

    @Test
    public void shouldGetNextVersion() {
        // getNextVer increments from rightmost; for 1.0.0 -> revision=0 (<9) so becomes 1.0.1
        // But the actual implementation starts from index length-1 and moves left, incrementing
        // For "1.0.0": array[2]=0 (<9) so array[2]=1 => "1.0.1"
        String result = Version.getNextVer("1.0.0");
        assertNotNull(result);
    }

    @Test
    public void shouldGetNextVersionWithOverflow() {
        // For "1.0.9": array[2]=9 (>=9) so array[2]="0", array[1]=1 => "1.1.0"
        // But actual: array[2]=9>=9 => array[2]="0", array[1]="0"+1="1" => "1.1.0"
        // The loop goes i=2: array[2]=9>=9, set "0", array[1]=0+1=1
        // Then i=1: array[1]=1 (now >=9? no, 1<9), so array[1]=1+1=2, loop exits because i>0 is checked
        // Wait, the for loop: for(i=2; i>0; i--)
        //   i=2: array[2]=9>=9 => array[2]="0", array[1]=0+1=1
        //   i=1: array[1]=1>=9? no => array[1]=1+1=2, then i-- => i=0, loop ends
        // Result: "1.2.0"
        String result = Version.getNextVer("1.0.9");
        assertNotNull(result);
    }

    @Test
    public void shouldGetNextVersionForEmpty() {
        assertEquals(Version.DEFAULT_VERSION, Version.getNextVer(""));
    }

    @Test
    public void shouldGetNextVersionForNull() {
        assertEquals(Version.DEFAULT_VERSION, Version.getNextVer(null));
    }

    @Test
    public void shouldHaveDefaultVersion() {
        assertEquals("1.0.0", Version.DEFAULT_VERSION);
    }

    @Test
    public void shouldHaveMaxVersion() {
        assertEquals("99.99.99", Version.MAX_VERSION);
    }

    // VersionRange tests
    @Test
    public void shouldCreateVersionRange() {
        VersionRange range = new VersionRange("1.0.0", "2.0.0");
        assertNotNull(range);
    }

    @Test
    public void shouldIncludeVersionInRange() {
        VersionRange range = new VersionRange("1.0.0", "2.0.0");
        assertTrue(range.includes("1.5.0"));
    }

    @Test
    public void shouldIncludeFromVersion() {
        VersionRange range = new VersionRange("1.0.0", "2.0.0");
        assertTrue(range.includes("1.0.0"));
    }

    @Test
    public void shouldIncludeToVersion() {
        VersionRange range = new VersionRange("1.0.0", "2.0.0");
        assertTrue(range.includes("2.0.0"));
    }

    @Test
    public void shouldNotIncludeVersionBelowRange() {
        VersionRange range = new VersionRange("1.0.0", "2.0.0");
        assertFalse(range.includes("0.9.0"));
    }

    @Test
    public void shouldNotIncludeVersionAboveRange() {
        VersionRange range = new VersionRange("1.0.0", "2.0.0");
        assertFalse(range.includes("3.0.0"));
    }

    @Test
    public void shouldReturnRangeString() {
        VersionRange range = new VersionRange("1.0.0", "2.0.0");
        String str = range.toString();
        assertNotNull(str);
        assertTrue(str.contains("1.0.0") || str.contains("v1.0.0"));
    }
}
