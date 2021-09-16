package com.deyuan.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;

import com.deyuan.service.AuthService;
import com.deyuan.util.AuthToken;
import com.deyuan.util.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Value("${auth.cookieDomain}")
    private String cookieDomain;

    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;
/*

    @PostMapping("/interface/login")
    @ResponseBody
    public Result loginInterface(@RequestParam("username") String username,@RequestParam("password") String password) throws Exception{

        //1. 判断用户名是否为空
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("用户名不存在");
        }
        //2. 判断密码是否为空
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("密码不存在");
        }
        //3. 调用service登录
        AuthToken authToken = authService.applyToken(username, password, clientId, clientSecret);

        //4. 将短令牌写入cookie中
        if (authToken != null) {
            saveJtiToCookie(authToken.getJti());
        } else {
            throw new RuntimeException("========认证失败!======");
        }
        return new Result(true, StatusCode.OK, "登录调用成功");
    }
*/

    private void saveJtiToCookie(String jti) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,cookieDomain,"/","uid",jti,cookieMaxAge,false);
    }

    //登录的方法

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("ReturnUrl") String ReturnUrl){
        //1. 判断用户名是否为空
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("用户名不存在");
        }
        //2. 判断密码是否为空
        if (StringUtils.isEmpty(password)) {
            throw new RuntimeException("密码不存在");
        }
        try {
            //3. 调用service登录
            AuthToken authToken = authService.applyToken(username, password, clientId, clientSecret);

            //4. 将短令牌写入cookie中
            if (authToken != null) {
                saveJtiToCookie(authToken.getJti());
            } else {
                throw new RuntimeException("========认证失败!======");
            }
            return "redirect:" + ReturnUrl;
        }catch (Exception e){
            e.printStackTrace();
            return "redirect://localhost:8001/api/oauth/toLogin"+ReturnUrl;
        }

    }

    //跳转到登录页面
    @RequestMapping("/toLogin")
    public String toLogin(@RequestParam(name="ReturnUrl",required = false,defaultValue = "http://www.kaolayigou.com") String ReturnUrl, Model model){
        model.addAttribute("ReturnUrl",ReturnUrl);
        return "login";
    }
}