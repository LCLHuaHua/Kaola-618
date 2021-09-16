package com.deyuan.goods.dao;

import com.deyuan.goods.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;



public interface BrandMapper extends Mapper<Brand> {
    //继承Mapper crud
    //根据分类名称查询品牌列表

    @Select("select * from tb_brand where id in(select brand_id  from  tb_category_brand where category_id in (SELECT id from tb_category where name=#{cateName}))")
    List<Brand> findByCateName(@Param("cateName") String cateName);


}
