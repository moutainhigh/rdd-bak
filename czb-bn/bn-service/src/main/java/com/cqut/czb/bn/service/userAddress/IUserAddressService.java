package com.cqut.czb.bn.service.userAddress;

import com.cqut.czb.bn.entity.entity.Address;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IUserAddressService {
    List<Address> getUserAddressList(String userId);
}
