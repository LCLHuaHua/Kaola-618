package com.deyuan.goods.fegin;

import com.deyuan.goods.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "goods")
@RequestMapping("/category")
public interface CategoryFegin {
    //根据id来查询category对象
    @RequestMapping("/{id}")
    public Category findById(@PathVariable("id")Integer id);
}
