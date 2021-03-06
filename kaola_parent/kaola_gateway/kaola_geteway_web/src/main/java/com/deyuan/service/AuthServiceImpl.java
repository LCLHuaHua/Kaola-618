package com.deyuan.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
/**
 * @User LiChengLong
 * @Date 2021/8/1911:32
 * @Jia Jia开开心心
 */
@Service
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 判断cookie中jti是否存在
     * @param request
     * @return
     */
    public String getJtiFromCookie(ServerHttpRequest request) {

        HttpCookie cookie = request.getCookies().getFirst("uid");
        if (cookie!=null){
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 根据Jti短令牌到redis中获取长令牌也就是JWT串
     * @param jti
     * @return
     */
    public String getTokenFromRedis(String jti) {
        String token = stringRedisTemplate.boundValueOps(jti).get();
        return token;
    }
}
