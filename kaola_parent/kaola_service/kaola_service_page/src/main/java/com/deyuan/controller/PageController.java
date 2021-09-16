package com.deyuan.controller;

import com.deyuan.entity.Result;
import com.deyuan.entity.StatusCode;
import com.deyuan.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/page")
public class PageController {
    @Autowired
    private PageService pageService;

    @RequestMapping("/createHtml/{spuId}")
    public Result createHtml(@PathVariable("spuId") String spuId){
        pageService.createDateHtml(spuId);
        return  new Result(true, StatusCode.OK,"生成详情页成功");
    }

}
