package com.deyuan.goods.service.impl;

import com.deyuan.goods.dao.CategoryMapper;
import com.deyuan.goods.pojo.Category;
import com.deyuan.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
