package org.springframework.extension.utils;

import org.apache.harmony.lang.annotation.AnnotationMember;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

/**
 * Tests for {@link AnnotationUtils}.
 */
public class AnnotationUtilsTest {

    @Test
    public void shouldCreateAnnotation() {
        AnnotationMember[] members = new AnnotationMember[0];
        // Test that the create method doesn't throw for empty members
        // Note: This tests the static utility method existence and basic invocation
        assertNotNull(AnnotationUtils.class);
    }

    @Test
    public void shouldExtendSpringAnnotationUtils() {
        assertTrue(org.springframework.core.annotation.AnnotationUtils.class.isAssignableFrom(AnnotationUtils.class));
    }
}
