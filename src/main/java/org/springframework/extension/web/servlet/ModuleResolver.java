package org.springframework.extension.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ModuleResolver.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see ModuleResolver
 */
public interface ModuleResolver {

    String resolveModule(HttpServletRequest request);

    void setModule(HttpServletRequest request, HttpServletResponse response, String moduleName);


}
