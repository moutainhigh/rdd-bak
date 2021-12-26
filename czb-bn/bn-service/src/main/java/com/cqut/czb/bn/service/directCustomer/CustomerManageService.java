package com.cqut.czb.bn.service.directCustomer;


import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerLoginDto;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface CustomerManageService {
    JSONResult getCustomers(CustomerManageDto customerManageDto);

    JSONResult recharge(CustomerManageDto customerManageDto);

    JSONResult recovered(CustomerManageDto customerManageDto);

    JSONResult checkPassword(CustomerLoginDto customerLoginDto);

    JSONResult addCustomer(CustomerManageDto customerManageDto);

    JSONResult changeRecharge(CustomerManageDto customerManageDto);

}
