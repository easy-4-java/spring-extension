package org.springframework.extension.web.servlet.mvc.method.version;

import org.junit.Test;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import static org.junit.Assert.assertTrue;

public class VersionRequestMappingHandlerMappingTest {

    @Test
    public void createsPathForEachDeclaredVersion() {
        VersionMapping annotation = VersionedHandler.class.getAnnotation(VersionMapping.class);
        RequestMappingInfo info = new VersionRequestMappingHandlerMapping("/v")
                .createRequestMappingInfo(annotation, null);

        assertTrue(info.getPatternValues().contains("/v1"));
        assertTrue(info.getPatternValues().contains("/v2"));
    }

    @VersionMapping({1, 2})
    private static class VersionedHandler {
    }
}
