package com.cqut.czb.bn.service.ThirdBusinessService;

import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;

import java.util.List;

public interface GetUnChargeOrderService {

    List<GetUnChargeOrderDTO> getUnChargeOrder();

    Boolean InputChargeOrders(String obj);

}
