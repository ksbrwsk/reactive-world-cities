package de.ksbrwsk.citiies;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.RequestPath;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class RequestLogger implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        RequestPath path = serverWebExchange.getRequest().getPath();
        log.info("Serving request {}", path.value());
        return webFilterChain.filter(serverWebExchange);
    }
}
