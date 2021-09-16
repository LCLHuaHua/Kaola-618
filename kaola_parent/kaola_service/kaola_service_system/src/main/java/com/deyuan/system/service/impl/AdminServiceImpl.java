package com.deyuan.system.service.impl;

import com.deyuan.pojo.Admin;
import com.deyuan.system.dao.AdminMapper;
import com.deyuan.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl  implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void add(Admin admin) {
        //密码加密
        String password = BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt());
        admin.setPassword(password);
        //添加状态
        admin.setStatus("1");
        adminMapper.insertSelective(admin);
    }

    @Override
    public boolean login(Admin admin) {
        Admin admin1=new Admin();
        admin1.setLoginName(admin.getLoginName());
        admin1.setStatus("1");
        Admin admin2 = adminMapper.selectOne(admin1);//根据后端的用户名 得到加密后密码
        if(admin2==null){
            return false;
        }else
         {
        return   BCrypt.checkpw(admin.getPassword(),admin2.getPassword());
        }
    }
}
