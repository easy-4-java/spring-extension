package org.springframework.extension.web.servlet;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * SpringAutowireServlet.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see SpringAutowireServlet
 */
@SuppressWarnings("serial")
public class SpringAutowireServlet extends HttpServlet {

    public void init() throws ServletException {
        super.init();
        WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getAutowireCapableBeanFactory()
                .autowireBean(this);
    }

}
