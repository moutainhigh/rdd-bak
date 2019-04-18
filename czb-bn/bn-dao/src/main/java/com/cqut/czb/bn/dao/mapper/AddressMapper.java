package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Address;

public interface AddressMapper {
    int deleteByPrimaryKey(String addressId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(String addressId);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
}