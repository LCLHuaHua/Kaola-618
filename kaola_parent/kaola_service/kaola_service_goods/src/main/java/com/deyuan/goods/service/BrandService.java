package com.deyuan.goods.service;

import com.deyuan.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrandService {
    //查询所有品牌列表
    List<Brand> findAll();
    //添加品牌信息
    void addBrand(Brand brand);
    //修改品牌信息
    void updateBrand(Brand brand);
    //删除品牌信息
    void deleteBrand(Integer id);

    List<Brand> findByCateName(String cateName);
}
