package org.springframework.extension.web.servlet.theme;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

/** 在 HTTP Session 中保存主题名称。 */
public class SessionThemeResolver implements ThemeResolver {

    public static final String THEME_SESSION_ATTRIBUTE_NAME = SessionThemeResolver.class.getName() + ".THEME";

    private String defaultThemeName = "theme";

    @Override
    public String resolveThemeName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object theme = Objects.nonNull(session) ? session.getAttribute(THEME_SESSION_ATTRIBUTE_NAME) : null;
        return theme instanceof String ? (String) theme : defaultThemeName;
    }

    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
        HttpSession session = request.getSession(Objects.nonNull(themeName));
        if (Objects.nonNull(session)) {
            if (Objects.nonNull(themeName)) {
                session.setAttribute(THEME_SESSION_ATTRIBUTE_NAME, themeName);
            } else {
                session.removeAttribute(THEME_SESSION_ATTRIBUTE_NAME);
            }
        }
    }

    public String getDefaultThemeName() {
        return defaultThemeName;
    }

    public void setDefaultThemeName(String defaultThemeName) {
        this.defaultThemeName = Objects.requireNonNull(defaultThemeName, "defaultThemeName");
    }
}
