package org.springframework.extension.utils;

import org.springframework.extension.context.SpringClassPathXmlInstanceContext;
import org.springframework.extension.context.SpringContext;

/**
 * SpringContextUtils.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see SpringContextUtils
 */
public class SpringContextUtils {

    private static SpringContext context = new SpringClassPathXmlInstanceContext(new String[0]);

    public SpringContextUtils() {
    }

    public static SpringContext getContext() {
        return SpringContextUtils.context;
    }

    public static void setContext(SpringContext context) {
        SpringContextUtils.context = context;
    }

}
