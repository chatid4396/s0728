package com.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Objects;

@Slf4j
@Component
@Order(value = Integer.MIN_VALUE)
public class AccessLogGlobalFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //filter前置处理
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().pathWithinApplication().value();
        InetSocketAddress address = request.getRemoteAddress();
        return chain.
                //继续调用filter
                    filter(exchange).
                    then(Mono.fromRunnable(() -> {
                        ServerHttpResponse response = exchange.getResponse();
                        HttpStatus status = response.getStatusCode();
                        log.info("请求路径:{}, 远程ip地址:{},端口号{}, 响应码:{}", path, address.getAddress(), address.getPort(), status);
                    }));
    }
}
