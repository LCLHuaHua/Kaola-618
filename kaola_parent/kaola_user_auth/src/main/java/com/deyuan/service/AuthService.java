package com.deyuan.service;

import com.deyuan.util.AuthToken;

/**
 * @User LiChengLong
 * @Date 2021/8/1910:46
 * @Jia Jia开开心心
 */
public interface AuthService {
    //用户登录  输入用户名和密码 clientId cleintSercurt
    AuthToken applyToken(String username, String password, String clientId, String clientSecret);
}
