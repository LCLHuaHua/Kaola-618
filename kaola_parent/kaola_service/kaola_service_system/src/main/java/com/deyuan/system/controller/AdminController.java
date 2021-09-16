package com.deyuan.system.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.pojo.Admin;
import com.deyuan.system.service.AdminService;
import com.deyuan.system.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    //添加用户
    @RequestMapping("/add")
    public Result add(@RequestBody Admin admin){
        adminService.add(admin);
        return  new Result(true, StatusCode.OK,"添加成功");
    }
    //查询用户
    @RequestMapping("/login")
    public Result login(@RequestBody Admin admin){
        if(StringUtil.isEmpty(admin.getLoginName())){
            return new Result(false,StatusCode.LOGINERROR,"用户名不能为空");
        }
        if(StringUtil.isEmpty(admin.getPassword())){
            return new Result(false,StatusCode.LOGINERROR,"密码不能为空");
        }
        boolean login = adminService.login(admin);
        if(login){
            Map<String,String> info=new HashMap<>();
            info.put("loginName",admin.getLoginName());
            info.put("token", JwtUtil.createJWT(UUID.randomUUID().toString(),admin.getLoginName(),null));
            return  new Result(true,StatusCode.OK,"登录成功",info);
        }else{
            return new Result(false,StatusCode.LOGINERROR,"登录失败");
        }
    }
}
