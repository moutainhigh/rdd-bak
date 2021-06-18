package com.cqut.czb.bn.service.directCustomer;

import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.global.JSONResult;


public interface MobileService {
    JSONResult telorder(DirectCustomersDto directCustomersDto);


    JSONResult ordersta(DirectCustomersDto directCustomersDto);
}
