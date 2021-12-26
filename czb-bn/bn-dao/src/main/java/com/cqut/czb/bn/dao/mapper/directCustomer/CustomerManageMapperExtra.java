package com.cqut.czb.bn.dao.mapper.directCustomer;

import com.cqut.czb.bn.entity.dto.directCustomers.CustomerLoginDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.util.List;

public interface CustomerManageMapperExtra {
    List<CustomerManageDto> getCustomers(CustomerManageDto customerManageDto);

    CustomerManageDto findCustomer(CustomerManageDto customerManageDto);

    int recharge(CustomerManageDto customerManageDto);

    int recovered(CustomerManageDto customerManageDto);

    int addCustomer(CustomerManageDto customerManageDto);

    CustomerLoginDto checkPassword(CustomerLoginDto customerLoginDto);

    int changeRecharge(CustomerManageDto customerManageDto);
}
