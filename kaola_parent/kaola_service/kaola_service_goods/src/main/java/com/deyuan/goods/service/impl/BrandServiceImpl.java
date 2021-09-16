package com.deyuan.goods.service.impl;

import com.deyuan.goods.dao.BrandMapper;
import com.deyuan.goods.pojo.Brand;
import com.deyuan.goods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandServiceImpl  implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    @Override
    public void addBrand(Brand brand) {
        brandMapper.insertSelective(brand);//内置非空判断
    }

    @Override
    public void updateBrand(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    @Override
    public void deleteBrand(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> findByCateName(String cateName) {
        return brandMapper.findByCateName(cateName);
    }

}
