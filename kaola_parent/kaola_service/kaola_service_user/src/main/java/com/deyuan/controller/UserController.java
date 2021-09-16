package com.deyuan.controller;

import com.deyuan.service.UserService;
import com.deyuan.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @User LiChengLong
 * @Date 2021/8/1819:19
 * @Jia Jia开开心心
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    //根据用户名来查询用户对象
    @RequestMapping("/load/{username}")
    public User findByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        return user;
    }
    //查询所有的用户信息
    @RequestMapping("/findAll")
    public List<User> findAll(){
        List<User> list = userService.findAll();
        return  list;
    }
}
