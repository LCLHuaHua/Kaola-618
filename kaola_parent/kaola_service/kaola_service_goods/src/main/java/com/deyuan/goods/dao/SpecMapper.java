package com.deyuan.goods.dao;

import com.deyuan.goods.pojo.Spec;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecMapper extends Mapper<Spec> {
    //根据分类名称查询规格列表
    @Select("SELECT * from tb_spec where template_id in(select template_id from tb_category where id in(SELECT id from tb_category where name=#{specName}))")
    List<Spec> findByCateName(@Param("specName") String specName);
}
