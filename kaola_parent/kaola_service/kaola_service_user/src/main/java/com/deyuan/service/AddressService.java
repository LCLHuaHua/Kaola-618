package com.deyuan.service;

import com.deyuan.user.pojo.Address;

import java.util.List;

/**
 * @User LiChengLong
 * @Date 2021/8/2311:53
 * @Jia Jia开开心心
 */
public interface AddressService {
    List<Address> list(String username);
}
