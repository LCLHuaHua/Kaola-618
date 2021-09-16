package com.deyuan.controller;

import com.deyuan.order.fegin.CartFegin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @User LiChengLong
 * @Date 2021/8/2016:29
 * @Jia Jia开开心心
 */

@Controller
@RequestMapping("/wcart")
public class CartController {
    private static final String Cart_URL = "http://localhost:9111";
    @Autowired
    private CartFegin cartFegin;

    //渲染购物车列表页面
    @RequestMapping("/list")
    public String list(Model model) {//渲染购物车列表，
        Map result = cartFegin.list("majin");
        model.addAttribute("result", result);
        return "cart";
    }

    //添加购物车
    @RequestMapping("/add")
    public String add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num, Model model) {
        cartFegin.add(skuId, num);
        model.addAttribute("skuId", skuId);
        model.addAttribute("num", num);
        return "redirect:" + Cart_URL + "/wcart/list";
    }

    //删除购物车
    @RequestMapping("/delete")
    public String deleteCart(@RequestParam("skuId") String skuId) {
        cartFegin.deleteCart(skuId);
        return "redirect:" + Cart_URL + "/wcart/list";
    }

    //勾选或减少购物车商品
    @RequestMapping("/updateChecked")
    public String updateChecked(@RequestParam("skuId") String skuId, @RequestParam("checked") boolean checked) {
        cartFegin.updateChecked(skuId, checked);
        return "redirect:" + Cart_URL + "/wcart/list";
    }
}