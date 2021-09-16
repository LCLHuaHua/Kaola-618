package com.deyuan.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @User LiChengLong
 * @Date 2021/8/2014:15
 * @Jia Jia开开心心
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/add")
    public Result add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num){
        cartService.add("majin",skuId,num);
        return new Result(true, StatusCode.OK,"添加或减少商品购物车");
    }

    //查询购物车列表
    @RequestMapping("/list")
    public Map list(String username){
        Map map = cartService.list("majin");
        return map;
    }
    //删除购物车
    @RequestMapping("/delete")
    public Result deleteCart(@RequestParam("skuId") String skuId){
        cartService.deleteCart(skuId,"majin");
        return new Result(true,StatusCode.OK,"删除成功");
    }
    //勾选或取消商品
    @RequestMapping("/updateChecked")
    public Result updateChecked(@RequestParam("skuId") String skuId,
                                @RequestParam("checked")boolean checked){
        cartService.updateChecked(skuId,"majin",checked);
        return new Result(true,StatusCode.OK,"更新成功");

    }
}