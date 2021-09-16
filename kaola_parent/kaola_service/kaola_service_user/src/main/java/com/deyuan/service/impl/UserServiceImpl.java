package com.deyuan.service.impl;

import com.deyuan.dao.UserMapper;
import com.deyuan.service.UserService;
import com.deyuan.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @User LiChengLong
 * @Date 2021/8/1819:18
 * @Jia Jia开开心心
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.selectByPrimaryKey(username);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
}
