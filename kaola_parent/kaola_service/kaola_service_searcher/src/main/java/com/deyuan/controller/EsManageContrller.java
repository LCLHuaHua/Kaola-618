package com.deyuan.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.service.EsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class EsManageContrller {

    @Autowired
    private EsManageService esManageService;
    //创建索引库
    @RequestMapping("/createIndex")
    public Result createIndex(){
        esManageService.createIndexManage();
        return new Result(true, StatusCode.OK,"创建索引库成功");
    }
    //删除索引库
    @RequestMapping("/deleteIndex")
    public Result deleteIndex(){
        esManageService.deleteIndexManage();
        return new Result(true, StatusCode.OK,"删除索引库成功");
    }


    //根据spuId导入索引库
    @RequestMapping("/importBySpuId/{spuId}")
    public Result importBySpuId(@PathVariable("spuId")String spuId){
        esManageService.importBySpuId(spuId);
        return new Result(true, StatusCode.OK,"根据spuId导入索引库成功");
    }

    //导入所有索引库
    @RequestMapping("/importAll")
    public Result importAll(){
        esManageService.importAll();
        return new Result(true, StatusCode.OK,"导入所有索引库成功");
    }

}
