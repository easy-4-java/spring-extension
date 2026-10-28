package org.springframework.extension.web.servlet.theme;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 嵌套Theme解析器；解决同时设置Theme到Session和Cookie的问题
 */
public class NestedThemeResolver implements ThemeResolver {

    private String defaultThemeName = "theme";
    protected List<ThemeResolver> resolvers;

    @Override
    public String resolveThemeName(HttpServletRequest request) {
        if (isNested()) {
            String def = getDefaultThemeName();
            for (ThemeResolver localeResolver : getResolvers()) {
                // 解析theme
                String theme = localeResolver.resolveThemeName(request);
                if (Objects.isNull(theme) || theme.equals(def)) {
                    continue;
                }
                return theme;
            }
        }
        return getDefaultThemeName();
    }

    @Override
    public void setThemeName(HttpServletRequest request, HttpServletResponse response, String themeName) {
        if (isNested()) {
            for (ThemeResolver localeResolver : getResolvers()) {
                localeResolver.setThemeName(request, response, themeName);
            }
        }
    }

    protected boolean isNested() {
        return Objects.nonNull(getResolvers());
    }

    public String getDefaultThemeName() {
        return defaultThemeName;
    }

    public void setDefaultThemeName(String defaultThemeName) {
        this.defaultThemeName = Objects.requireNonNull(defaultThemeName, "defaultThemeName");
    }

    public List<ThemeResolver> getResolvers() {
        return resolvers;
    }

    public void setResolvers(List<ThemeResolver> resolvers) {
        this.resolvers = resolvers;
    }

}
