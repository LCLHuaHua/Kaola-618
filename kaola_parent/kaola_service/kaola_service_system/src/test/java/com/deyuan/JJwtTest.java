package com.deyuan;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * Create by yanhongbo
 * 日期 2021/7/30
 *
 * @Version 1.0
 */
public class JJwtTest {
    //构建jwt
    @Test
    public void test01(){
        //获取当前时间
        long currentTimeMillis = System.currentTimeMillis();
        Date date=new Date(currentTimeMillis);
        JwtBuilder jwtBuilder = Jwts.builder().setId("888").setSubject("小风扇").setIssuedAt(new Date()).setExpiration(date)
                  .signWith(SignatureAlgorithm.HS256, "deyuan");
        //构建jwt字符串
        String jwtcompact = jwtBuilder.compact();
        System.out.println(jwtcompact);
    }
    //解析token
    @Test
    public void test02(){
        String jwtStr="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_po47miYciLCJpYXQiOjE2Mjc2MzQyODMsImV4cCI6MTYyNzYzNDI4M30.fuJLLBz4i2eVUyhmFfuIHMhPaaE4F5qf-QDondmeq3I";
        Claims claims = Jwts.parser().setSigningKey("deyuan").parseClaimsJws(jwtStr).getBody();
        System.out.println(claims);
    }
}
