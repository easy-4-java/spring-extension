package org.springframework.extension.web.servlet.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * FixedModuleResolver.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see FixedModuleResolver
 */
public class FixedModuleResolver extends AbstractModuleResolver {

    @Override
    public String resolveModule(HttpServletRequest request) {
        return getDefaultModule();
    }

    @Override
    public void setModule(HttpServletRequest request, HttpServletResponse response, String moduleName) {
        throw new UnsupportedOperationException("Cannot change module - use a different module resolution strategy");
    }

}
