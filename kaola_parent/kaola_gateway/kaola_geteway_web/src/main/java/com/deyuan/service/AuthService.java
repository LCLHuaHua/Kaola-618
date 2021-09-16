package com.deyuan.service;

import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @User LiChengLong
 * @Date 2021/8/1911:32
 * @Jia Jia开开心心
 */
public interface AuthService {
    //判断cookie是否携带jti
    public String getJtiFromCookie(ServerHttpRequest request);
    //根据jti获取长令牌
    public String getTokenFromRedis(String jti);
}
