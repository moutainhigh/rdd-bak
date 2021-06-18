package com.cqut.czb.bn.service.directCustomer;


import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface CustomerManageService {
    JSONResult getCustomers(CustomerManageDto customerManageDto);

    JSONResult recharge(CustomerManageDto customerManageDto);
}
