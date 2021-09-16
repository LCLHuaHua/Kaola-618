package com.deyuan.user.fegin;

import com.deyuan.user.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @User LiChengLong
 * @Date 2021/8/1819:25
 * @Jia Jia开开心心
 */
@FeignClient(name = "user")
@RequestMapping("/user")
public interface UserFegin {


    @RequestMapping("/load/{username}")
    User findByUsername(@PathVariable("username") String username);
}
