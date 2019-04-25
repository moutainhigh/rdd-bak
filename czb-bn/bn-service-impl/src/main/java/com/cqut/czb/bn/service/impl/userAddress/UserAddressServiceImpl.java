package com.cqut.czb.bn.service.impl.userAddress;

import com.cqut.czb.bn.dao.mapper.AddressMapperExtra;
import com.cqut.czb.bn.entity.entity.Address;
import com.cqut.czb.bn.service.userAddress.IUserAddressService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserAddressServiceImpl implements IUserAddressService {
    @Autowired
    AddressMapperExtra addressMapperExtra;
    @Override
    public List<Address> getUserAddressList(String userId) {
        return addressMapperExtra.getUserAddressList(userId);
    }
}
