package com.deyuan.service;

import java.util.Map;

/**
 * @User LiChengLong
 * @Date 2021/8/2014:16
 * @Jia Jia开开心心
 */
public interface CartService {
    /**
     * 根据用户名来添加购物车
     * @param username
     * @param skuId
     * @param num
     */
    void add(String username,String skuId,Integer num);
    /**
     * 查询购物车列表
     * @param username
     * @return
     */
    Map list(String username);

    /**
     * 根据skuId删除购物车数据 指定用户名
     * @param skuId
     * @param username
     */
    void deleteCart(String skuId,String username);
    /**
     *
     */
    void updateChecked(String skuId,String username,boolean checked);
}
