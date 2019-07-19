package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapperExtra {
    List<Address> getUserAddressList(String userId);

    Address getDefaultAddress(@Param("userId") String userId);

    int cancelDefault(@Param("userId") String userId);

    int setDefault(@Param("addressId") String addressId,@Param("userId") String userId);

    int updateByPrimaryKeySelective(Address record);

    String selectAddressId(@Param("contractRecordId") String contractRecordId);
}