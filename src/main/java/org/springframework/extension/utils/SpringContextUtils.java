package org.springframework.extension.utils;

import org.springframework.extension.context.SpringClassPathXmlInstanceContext;
import org.springframework.extension.context.SpringContext;

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
