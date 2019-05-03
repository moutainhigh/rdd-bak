package com.cqut.czb.bn.service.impl.userAddress;

import com.cqut.czb.bn.dao.mapper.AddressMapper;
import com.cqut.czb.bn.dao.mapper.AddressMapperExtra;
import com.cqut.czb.bn.entity.dto.address.AddressInputDTO;
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
    public boolean addAddress(AddressInputDTO address, String userId) {
        address.setUserId(userId);
        address.setAddressId(StringUtil.createId());
        address.setCreateAt(new Date());
        return addressMapper.insert(address.getAddressEntity())>0;
    }

    @Override
    public boolean modifyAddress(AddressInputDTO address){
        address.setUpdateAt(new Date());
        return  addressMapperExtra.updateByPrimaryKeySelective(address.getAddressEntity()) > 0;
    }

    @Override
    public boolean deleteAddress(String addressId, String userId) {
        if(addressMapperExtra.getUserAddressList(userId).size() <= 1){
            return false;
        }

        return addressMapper.deleteByPrimaryKey(addressId) > 0;
    }

    @Override
    public boolean setDefaultAddress(String addressId,String userId) {
        addressMapperExtra.cancelDefault(userId);

        return  addressMapperExtra.setDefault(addressId,userId) >0;
    }

    @Override
    public Address getDefaultAddress(String userId) {
        return addressMapperExtra.getDefaultAddress(userId);
    }
}
