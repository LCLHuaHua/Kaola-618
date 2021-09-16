package com.deyuan.goods.service.impl;

import com.deyuan.goods.dao.SpecMapper;
import com.deyuan.goods.pojo.Spec;
import com.deyuan.goods.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SpecServiceImpl implements SpecService {
    @Autowired
    private SpecMapper specMapper;

    @Override
    public List<Spec> findByCateName(String specName) {
        return specMapper.findByCateName(specName);
    }
}
