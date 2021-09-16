package com.deyuan.filter;

import com.deyuan.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    public static final String Authorization = "Authorization";
    @Autowired
    private AuthService authService;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //检验认证管理器
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //得到请求url
        String path = request.getURI().getPath();//http://localhost:9029/oauth/interface/login
        if(("/api/oauth/interface/login").equals(path)||"/api/oauth/toLogin".equals(path)||"/api/oauth/login".equals(path)){
            return chain.filter(exchange);
        }
        //判断cookie上是否存在jti也就是短令牌
        String jti = authService.getJtiFromCookie(request);
        if (StringUtils.isEmpty(jti)){
            //拒绝访问,请求跳转
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //根据短令牌获取长令牌, 并且判断是否有长令牌
        String token = authService.getTokenFromRedis(jti);
        if (StringUtils.isEmpty(token)){
            //拒绝访问，请求跳转
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //校验通过 , 请求头增强，放行(将长令牌放入请求头)
        request.mutate().header(Authorization,"Bearer "+token);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
