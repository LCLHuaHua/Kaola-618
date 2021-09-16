package com.deyuan.goods.fegin;

import com.deyuan.goods.pojo.Spu;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "goods")
@RequestMapping("/spu")
public interface SpuFegin {
    @RequestMapping("/findPageById/{spuId}")
    public Spu findPageById(@PathVariable("spuId")String spuId);
}
