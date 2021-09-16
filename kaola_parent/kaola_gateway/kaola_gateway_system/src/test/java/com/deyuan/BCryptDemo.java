package com.deyuan;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * Create by yanhongbo
 * 日期 2021/7/30
 *BCrypt 加密
 * @Version 1.0
 */
public class BCryptDemo {

    @Test
    public void Test01(){
        for(int i=1;i<=10;i++) {
            String password = BCrypt.hashpw("majin", BCrypt.gensalt());//29个字符随机排序
            System.out.println(password);
            /*
            $2a$10$PsjRr4o/EHnTlQXGpXUgzOi82LFHuZZAeJdHflI9s1/fDSodrYDBK
            $2a$10$F75fpMWlV.wLIp7iEUkt0OnFmtMWea9xZ5kGzWhVmcRaZJg2e5lnu
            $2a$10$fLAjlotTvAxJQDaLAepEluW9j6u4hF.Y12ueTqiny85.vE/GznzIa
            $2a$10$VXygAw5SeXDexXii4H7gfOVXtul2.xjECh4vBN7frzP71Xks7DuIe
            $2a$10$Z7EuKSw8xpppN8Zi1Im.Z.cg5aZ3KmfRlCtHWZ0lorfzAG34dJMzK
            $2a$10$F30jdCIDHyZ802Lndxc6S.e7eNMdc4FVyok7VgCC84bRfh8RZZcNe
            $2a$10$Bzl7wkhwRPY45.OXUvRYi./7wD9TuB1WoHWANo5ucKeH.DHbsB9kO
            $2a$10$rlK.2EV0yn.p8tnUS4xa3ecrts17lVfyKDBDbHFSk6mJHUCT3Ywo6
            $2a$10$IssX3CMUDuVBUeGLv7LYbeyJ85LvBqe9VkZ7JmSzBBGF5PMvMoPRm
            $2a$10$3dvKRnT9IK9lGx7H2erOC.Cd6hCj8vZzvCt6qRKY/PyenudY60.qK
             */
        }
    }
    @Test
    public void test01(){
        boolean b = BCrypt.checkpw("majin", "$2a$10$PsjRr4o/EHnTlQXGpXUgzOi82LFHuZZAeJdHflI9s1/fDSodrYDBK");
        System.out.println(b);

    }
}
