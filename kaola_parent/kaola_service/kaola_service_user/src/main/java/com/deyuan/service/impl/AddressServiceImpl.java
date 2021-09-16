package com.deyuan.service.impl;

import com.deyuan.dao.AddressMapper;
import com.deyuan.service.AddressService;
import com.deyuan.user.pojo.Address;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @User LiChengLong
 * @Date 2021/8/2311:54
 * @Jia Jia开开心心
 */
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> list(String username) {
        Address address = new Address();
        address.setUsername(username);
        List<Address> addressList = addressMapper.select(address);
        return addressList;
    }
}
