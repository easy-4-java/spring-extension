package org.springframework.extension.web.servlet.mvc.registry;

import java.io.IOException;

/**
 * DynamicControllerRegistry.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see DynamicControllerRegistry
 */
public interface DynamicControllerRegistry {

    /**
     * 动态注册SpringMVC Controller到Spring上下文
     * @param controllerBeanName    : The name of controller
     * @param controller            : The instance of controller
     */
    public void registerController(String controllerBeanName, Object controller);

    /**
     * 动态从Spring上下文删除SpringMVC Controller
     * @param controllerBeanName        : The name of controller
     * @throws IOException if io error
     */
    public void removeController(String controllerBeanName) throws IOException;

}
