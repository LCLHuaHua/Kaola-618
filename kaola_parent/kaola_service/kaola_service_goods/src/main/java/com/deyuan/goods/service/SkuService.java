package com.deyuan.goods.service;

import com.deyuan.goods.pojo.Sku;

import java.util.List;
import java.util.Map;

public interface SkuService {
    //根据spuId来查询skulist
    List<Sku>  findList(Map<String,Object> searchMap);
    //查询所有SkuList
    List<Sku> findAll();

    Sku findById(String skuId);
}
