package com.deyuan.goods.controller;

import com.deyuan.goods.pojo.Sku;
import com.deyuan.goods.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sku")
public class SkuController {
    @Autowired
    private SkuService skuService;

    //根据spuId来查询skuList
    @RequestMapping("/findBySpuId/{spuId}")
    public List<Sku> findBySpuId(@PathVariable("spuId") String spuId) {
        Map<String, Object> map = new HashMap<>();
        map.put("spuId", spuId);
        List<Sku> skuList = skuService.findList(map);
        return skuList;
    }
    //查询所有的skuList
    @RequestMapping("/findAll")
    public List<Sku> findAll(){
      return   skuService.findAll();
    }


    //得到sku对象
    @RequestMapping("/findById/{skuId}")
    public Sku findById(@PathVariable("skuId") String skuId){
        Sku sku= skuService.findById(skuId);
        return sku;
    }

}
