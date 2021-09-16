package com.deyuan.service;

import com.deyuan.user.pojo.User;

import java.util.List;

/**
 * @User LiChengLong
 * @Date 2021/8/1819:16
 * @Jia Jia开开心心
 */
public interface UserService {
    //查询用户名  根据username查询User对象
    User findByUsername(String username);

    //查询所有用户信息
    List<User> findAll();
}
