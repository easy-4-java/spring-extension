package org.springframework.extension.web.servlet.mvc.method.annotation;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.util.UrlPathHelper;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

public class MethodRequestMappingHandlerMappingTest {

    @Test
    public void acceptsConfiguredPathAndTrailingSlash() throws Exception {
        Method method = OrderAction.class.getDeclaredMethod("list");
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        RequestMappingInfo info = new MethodRequestMappingHandlerMapping()
                .createRequestMappingInfo(annotation, null, method);

        assertNotNull(info.getMatchingCondition(request("/orders")));
        assertNotNull(info.getMatchingCondition(request("/orders/")));
        assertNotNull(info.getMatchingCondition(request("/orders.json")));
    }

    private MockHttpServletRequest request(String path) {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", path);
        new UrlPathHelper().resolveAndCacheLookupPath(request);
        return request;
    }

    private static class OrderAction {
        @RequestMapping("/orders")
        public void list() {
        }
    }
}
