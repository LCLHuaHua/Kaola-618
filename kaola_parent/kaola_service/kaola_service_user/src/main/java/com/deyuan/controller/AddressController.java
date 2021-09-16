package com.deyuan.controller;

import com.deyuan.service.AddressService;
import com.deyuan.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @User LiChengLong
 * @Date 2021/8/2311:56
 * @Jia Jia开开心心
 */
@RestController
@RequestMapping("/address")

public class AddressController {
    @Autowired
    private AddressService addressService;

    @RequestMapping("/list")
    public List<Address> list(){
        List<Address> addressList = addressService.list("majin");
        return addressList;
    }
}
