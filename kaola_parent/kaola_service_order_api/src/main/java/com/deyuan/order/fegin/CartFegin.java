package com.deyuan.order.fegin;

import com.deyuan.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @User LiChengLong
 * @Date 2021/8/2016:40
 * @Jia Jia开开心心
 */
@FeignClient(name="order")
@RequestMapping("/cart")
public interface CartFegin {
    //添加购物车
    @RequestMapping("/add")
    public Result add(@RequestParam("skuId") String skuId, @RequestParam("num") Integer num);
    //查询购物车列表
    @RequestMapping("/list")
    public Map list(String username);
    //删除购物车商品数据
    @RequestMapping("/delete")
     Result deleteCart(@RequestParam("skuId") String skuId);
    @RequestMapping("/updateChecked")
     Result updateChecked(@RequestParam("skuId") String skuId, @RequestParam("checked")boolean checked);
}
