package org.springframework.extension.web.server;

import org.junit.Test;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertSame;

public class ReactiveRequestContextFilterTest {

    @Test
    public void exposesCurrentExchangeRequestAndResponseToReactiveChain() {
        ServerWebExchange exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/context").build());
        WebFilterChain chain = current -> Mono.zip(
                        ReactiveRequestContextHolder.getExchange(),
                        ReactiveRequestContextHolder.getRequest(),
                        ReactiveRequestContextHolder.getResponse())
                .doOnNext(values -> {
                    assertSame(exchange, values.getT1());
                    assertSame(exchange.getRequest(), values.getT2());
                    assertSame(exchange.getResponse(), values.getT3());
                }).then();

        new ReactiveRequestContextFilter().filter(exchange, chain).block();
    }
}
