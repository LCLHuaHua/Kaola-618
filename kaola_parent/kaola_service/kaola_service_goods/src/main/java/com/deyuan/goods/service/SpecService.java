package com.deyuan.goods.service;

import com.deyuan.goods.pojo.Spec;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpecService {
    List<Spec> findByCateName(String specName);
}
