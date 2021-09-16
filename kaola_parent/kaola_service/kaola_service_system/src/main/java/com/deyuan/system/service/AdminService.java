package com.deyuan.system.service;

import com.deyuan.pojo.Admin;

public interface AdminService {
    //添加用户
    void add(Admin admin);

    //用户登录
    boolean login(Admin admin);

}
