package org.springframework.extension.context.annotation;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;

import static org.junit.Assert.*;

/**
 * Tests for {@link WebScopeMetadataResolver}.
 */
public class WebScopeMetadataResolverTest {

    @Test
    public void shouldCreateResolver() {
        WebScopeMetadataResolver resolver = new WebScopeMetadataResolver();
        assertNotNull(resolver);
    }

    @Test
    public void shouldResolveDefaultScope() {
        WebScopeMetadataResolver resolver = new WebScopeMetadataResolver();
        // Non-annotated bean definition should get prototype scope
        BeanDefinition bd = org.springframework.beans.factory.support.BeanDefinitionBuilder
                .genericBeanDefinition(String.class).getBeanDefinition();
        ScopeMetadata metadata = resolver.resolveScopeMetadata(bd);
        assertNotNull(metadata);
        assertEquals(BeanDefinition.SCOPE_PROTOTYPE, metadata.getScopeName());
    }

    @Test
    public void shouldRegisterScopeByClass() {
        WebScopeMetadataResolver resolver = new WebScopeMetadataResolver();
        resolver.registerScope(Test.class, BeanDefinition.SCOPE_SINGLETON);
        // Should not throw
    }

    @Test
    public void shouldRegisterScopeByName() {
        WebScopeMetadataResolver resolver = new WebScopeMetadataResolver();
        resolver.registerScope("my.Scope", BeanDefinition.SCOPE_PROTOTYPE);
        // Should not throw
    }
}
