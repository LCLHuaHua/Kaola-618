package com.deyuan.service.impl;


import com.deyuan.entity.Constants;
import com.deyuan.goods.fegin.SkuFegin;
import com.deyuan.goods.fegin.SpuFegin;
import com.deyuan.goods.pojo.Sku;
import com.deyuan.goods.pojo.Spu;
import com.deyuan.order.pojo.OrderItem;
import com.deyuan.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User LiChengLong
 * @Date 2021/8/2014:17
 * @Jia Jia开开心心
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SkuFegin skuFegin;
    @Autowired
    private SpuFegin spuFegin;

    @Override
    public void add(String username, String skuId, Integer num) {
        //1.根据用户名和skuId来查询购物车数据  OrderItem
        OrderItem orderItem = (OrderItem) redisTemplate.boundHashOps(Constants.REDIS_CART + username).get(skuId);
        //2.如果orderItem不为空
        if (orderItem != null) {
            //num更新
            orderItem.setNum(orderItem.getNum() + num);
            //money更新
            orderItem.setMoney(orderItem.getMoney() * orderItem.getNum());
            //paymoney更新
            orderItem.setPrice(orderItem.getPrice());

        } else {
            //3.如果orderItem为空 需要构建orderItem对象
            orderItem = builderOrdreItem(skuId, num);
            System.out.println(orderItem);
            //4.保持到redis
            redisTemplate.boundHashOps(Constants.REDIS_CART + username).put(skuId, orderItem);
        }
    }

    //构建OrderItem方法
    private OrderItem builderOrdreItem(String skuId, Integer num) {
        Sku sku = skuFegin.findById(skuId);
        Spu spu = spuFegin.findPageById(sku.getSpuId());

        OrderItem orderItem = new OrderItem();
        //orderItem.setChecked(false);
        orderItem.setCategoryId1(spu.getCategory1Id());
        orderItem.setCategoryId2(spu.getCategory2Id());
        orderItem.setCategoryId3(spu.getCategory3Id());
        orderItem.setSpuId(spu.getId());
        orderItem.setSkuId(skuId);
        orderItem.setName(sku.getName());
        orderItem.setPrice(sku.getPrice());
        orderItem.setNum(num);
        orderItem.setMoney(num*orderItem.getPrice());
        orderItem.setPayMoney(sku.getPrice());
        orderItem.setImage(sku.getImage());
        orderItem.setWeight(sku.getWeight());
        return orderItem;
    }
    /**
     * 查询购物车列表
     * @param username
     * @return
     */

    @Override
    public Map list(String username) {
        Map map=new HashMap();
        //redis中去查询购物车列表
        List<OrderItem> orderItemList = redisTemplate.boundHashOps(Constants.REDIS_CART + username).values();
        if(orderItemList==null){
            throw  new RuntimeException("请先添加商品");
        }
        map.put("orderItemList",orderItemList);
        int totalNum=0;//2  3
        int totalPrice=0;
        //遍历orderItemList
        for (OrderItem orderItem : orderItemList) {
            totalNum+=orderItem.getNum();//数量变化 2+3
            totalPrice+=orderItem.getMoney(); //小计发生变化
        }
        map.put("totalNum",totalNum);
        map.put("totalPrice",totalPrice);

        return map;
    }

    @Override
    public void deleteCart(String skuId, String username) {
        //从redis中删除
        redisTemplate.boundHashOps(Constants.REDIS_CART + username).delete(skuId);
    }

    @Override
    public void updateChecked(String skuId, String username, boolean checked) {
        //从redis数据
        OrderItem orderItem =(OrderItem) redisTemplate.boundHashOps(Constants.REDIS_CART + username).get(skuId);
        if(checked) {
            orderItem.setChecked(checked);
        }
        redisTemplate.boundHashOps(Constants.REDIS_CART + username).put(skuId, orderItem);//更新数据 num pricie 查询购物车列表
    }
}
