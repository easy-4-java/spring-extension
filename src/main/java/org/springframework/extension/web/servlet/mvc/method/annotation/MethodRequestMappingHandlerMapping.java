package org.springframework.extension.web.servlet.mvc.method.annotation;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * http://blog.csdn.net/home_zhang/article/details/71156093
 */
@RequestMapping
public class MethodRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private boolean useSuffixPatternMatch = true;

    private boolean useTrailingSlashMatch = true;

    private ContentNegotiationManager contentNegotiationManager = new ContentNegotiationManager();

    private final List<String> fileExtensions = new ArrayList<String>();

    private static RequestMapping requestMapping = (RequestMapping) MethodRequestMappingHandlerMapping.class
            .getAnnotation(RequestMapping.class);

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = null;
        if (requestMapping != null) {
            info = createRequestMappingInfo(requestMapping, getCustomMethodCondition(method), method);
            RequestMapping typeAnnotation = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
            if (typeAnnotation != null) {
                RequestCondition<?> typeCondition = getCustomTypeCondition(handlerType);
                info = createRequestMappingInfo(typeAnnotation, typeCondition, method).combine(info);
            }
        }
        return info;
    }

    protected RequestMappingInfo createRequestMappingInfo(RequestMapping annotation,
            RequestCondition<?> customCondition, Method method) {

        String className = method.getDeclaringClass().getSimpleName();
        String classNameExceptAction = StringUtils.uncapitalize(className.substring(0, className.length() - 10));
        String[] patterns = resolveEmbeddedValuesInPatterns(annotation.value());
        if ((patterns != null) && (patterns.length == 0)) {//
            patterns = new String[] { classNameExceptAction + "!" + method.getName() };
        }

        List<String> compatiblePatterns = new ArrayList<>();
        for (String pattern : patterns) {
            compatiblePatterns.add(pattern);
            if (useTrailingSlashMatch && !pattern.endsWith("/")) {
                compatiblePatterns.add(pattern + "/");
            }
            if (useSuffixPatternMatch && !pattern.contains("*")) {
                if (fileExtensions.isEmpty()) {
                    compatiblePatterns.add(pattern + ".*");
                } else {
                    for (String extension : fileExtensions) {
                        compatiblePatterns.add(pattern + "." + extension);
                    }
                }
            }
        }

        return new RequestMappingInfo(
                new PatternsRequestCondition(compatiblePatterns.toArray(new String[0])),
                new RequestMethodsRequestCondition(annotation.method()),
                new ParamsRequestCondition(annotation.params()), new HeadersRequestCondition(annotation.headers()),
                new ConsumesRequestCondition(annotation.consumes(), annotation.headers()), new ProducesRequestCondition(
                        annotation.produces(), annotation.headers(), this.contentNegotiationManager),
                customCondition);
    }

}
