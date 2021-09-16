package com.deyuan.goods.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.goods.pojo.Goods;
import com.deyuan.goods.pojo.Spec;
import com.deyuan.goods.service.SpecService;
import com.deyuan.goods.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/spec")
public class SpecController {

    @Autowired
    private SpecService specService;


    @RequestMapping("/{cateName}")
    public Result findByCateName(@PathVariable("cateName")String cateName){
        List<Spec> specList = specService.findByCateName(cateName);
        return new Result(true, StatusCode.OK,"查询成功",specList);
    }


}
