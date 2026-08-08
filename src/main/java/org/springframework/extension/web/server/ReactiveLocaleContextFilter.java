package org.springframework.extension.web.server;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.i18n.LocaleContextResolver;
import reactor.core.publisher.Mono;

/**
 * ReactiveLocaleContextFilter.
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 3.0.0
 * @see ReactiveLocaleContextFilter
 */
public class ReactiveLocaleContextFilter implements WebFilter {

    /** LocaleContextResolver used by this Web Filter. */
    @Nullable
    private LocaleContextResolver localeContextResolver;

    public ReactiveLocaleContextFilter(LocaleContextResolver localeContextResolver) {
        super();
        this.localeContextResolver = localeContextResolver;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        LocaleContext resolveLocaleContext = localeContextResolver.resolveLocaleContext(exchange);
        LocaleContextHolder.setLocale(resolveLocaleContext.getLocale());
        return chain.filter(exchange);
    }

}