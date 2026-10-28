package org.springframework.extension.web.servlet.theme;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Spring 7 移除主题解析接口后供应用继续使用的主题存储契约。 */
public interface ThemeResolver {

    String resolveThemeName(HttpServletRequest request);

    void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName);
}
