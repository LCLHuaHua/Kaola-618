package com.deyuan.goods.controller;

import com.deyuan.goods.pojo.Category;
import com.deyuan.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CateController {
    @Autowired
    private CategoryService categoryService;

    //根据category1Id来查询category对象
    @RequestMapping("/{id}")
    public Category findById(@PathVariable("id")Integer id){
     Category category=   categoryService.findById(id);
     return category;

    }
}
