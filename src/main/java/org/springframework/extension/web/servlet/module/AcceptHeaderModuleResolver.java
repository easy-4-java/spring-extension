package org.springframework.extension.web.servlet.module;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AcceptHeaderModuleResolver.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see AcceptHeaderModuleResolver
 */
public class AcceptHeaderModuleResolver extends AbstractModuleResolver {

    @Override
    public String resolveModule(HttpServletRequest request) {
        String module = request.getHeader("Accept-Module");
        if(module != null){
            return module;
        }
        return getDefaultModule();
    }

    @Override
    public void setModule(HttpServletRequest request, HttpServletResponse response, String moduleName) {
        throw new UnsupportedOperationException(
                "Cannot change HTTP accept header - use a different module resolution strategy");
    }

}
