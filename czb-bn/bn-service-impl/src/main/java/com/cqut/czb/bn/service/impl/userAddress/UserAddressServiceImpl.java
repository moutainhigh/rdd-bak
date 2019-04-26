package com.cqut.czb.bn.service.impl.userAddress;

import com.cqut.czb.bn.dao.mapper.AddressMapper;
import com.cqut.czb.bn.dao.mapper.AddressMapperExtra;
import com.cqut.czb.bn.entity.entity.Address;
import com.cqut.czb.bn.service.userAddress.IUserAddressService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class UserAddressServiceImpl implements IUserAddressService {
    @Autowired
    AddressMapperExtra addressMapperExtra;

    @Autowired
    AddressMapper addressMapper;
    @Override
    public List<Address> getUserAddressList(String userId) {
        return addressMapperExtra.getUserAddressList(userId);
    }

    @Override
    public boolean addAddress(Address address, String userId) {
        address.setUserId(userId);
        address.setAddressId(StringUtil.createId());
        address.setCreateAt(new Date());
        return addressMapper.insert(address)>0;
    }

    @Override
    public boolean modifyAddress(Address address){
        address.setUpdateAt(new Date());
        return  addressMapper.updateByPrimaryKeySelective(address) > 0;
    }

    @Override
    public boolean deleteAddress(Address address, String userId) {
        if(addressMapperExtra.getUserAddressList(userId).size() <= 1){
            return false;
        }

        return addressMapper.deleteByPrimaryKey(address.getAddressId()) > 0;
    }
}
