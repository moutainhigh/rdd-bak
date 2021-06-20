package com.cqut.czb.bn.service.directCustomer;

import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomersDto;
import com.cqut.czb.bn.entity.global.JSONResult;

public interface SinopecService {

    JSONResult onlineorder(DirectCustomersDto directCustomersDto);

    JSONResult ordersta(DirectCustomersDto directCustomersDto);

}
