package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Address;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AddressMapperExtra {
    List<Address> getUserAddressList(String userId);
}