package com.cqut.czb.bn.service.userAddress;

import com.cqut.czb.bn.entity.dto.address.AddressInputDTO;
import com.cqut.czb.bn.entity.entity.Address;

import java.util.List;

public interface IUserAddressService {
    List<Address> getUserAddressList(String userId);
    boolean addAddress(AddressInputDTO address,String userId);
    boolean modifyAddress(AddressInputDTO address);
    boolean deleteAddress(String addressId,String userId);
    boolean setDefaultAddress(String addressId,String userId);
    Address getDefaultAddress(String userId);
}
