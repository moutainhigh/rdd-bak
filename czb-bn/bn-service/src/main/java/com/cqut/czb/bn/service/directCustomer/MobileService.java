package com.cqut.czb.bn.service.directCustomer;

import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.global.JSONResult;

import javax.servlet.http.HttpServletRequest;


public interface MobileService {
    JSONResult telorder(DirectCustomersDto directCustomersDto);


    JSONResult ordersta(DirectCustomersDto directCustomersDto);

    JSONResult getState(DirectCustomersDto directCustomersDto);

    JSONResult getTheBalance(DirectCustomersDto directCustomersDto);

    JSONResult DirectPhone(HttpServletRequest request);
}
