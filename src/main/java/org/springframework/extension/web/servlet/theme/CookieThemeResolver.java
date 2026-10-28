package org.springframework.extension.web.servlet.theme;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Objects;

/** 在 HTTP Cookie 中保存主题名称。 */
public class CookieThemeResolver implements ThemeResolver {

    private String cookieName = "theme";
    private String defaultThemeName = "theme";

    @Override
    public String resolveThemeName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return defaultThemeName;
    }

    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
        Cookie cookie = new Cookie(cookieName, Objects.nonNull(themeName) ? themeName : "");
        cookie.setPath("/");
        if (Objects.isNull(themeName)) {
            cookie.setMaxAge(0);
        }
        response.addCookie(cookie);
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = Objects.requireNonNull(cookieName, "cookieName");
    }

    public String getDefaultThemeName() {
        return defaultThemeName;
    }

    public void setDefaultThemeName(String defaultThemeName) {
        this.defaultThemeName = Objects.requireNonNull(defaultThemeName, "defaultThemeName");
    }
}
