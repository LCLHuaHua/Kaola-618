package com.deyuan.goods.fegin;

import com.deyuan.goods.pojo.Sku;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "goods") //远程调用服务名字
@RequestMapping("/sku")
public interface SkuFegin {
    //根据spuId查询skuList
    @RequestMapping("/findBySpuId/{spuId}")
    List<Sku> findBySpuId(@PathVariable("spuId") String spuId);
    //查询所有skulist
    @RequestMapping("/findAll")
    List<Sku> findAll();

    @RequestMapping("/findById/{skuId}")
    Sku findById(@PathVariable("skuId") String skuId);
}
