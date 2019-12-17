package com.cqut.czb.bn.service.ThirdBusinessService;

import com.cqut.czb.bn.entity.dto.ThirdBusiness.ChargeBackDTO;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetChargeOrderInputDTO;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;
import com.cqut.czb.bn.entity.global.JSONResult;

import java.security.Principal;
import java.util.List;

public interface GetUnChargeOrderService {

    List<GetUnChargeOrderDTO> getUnChargeOrder();

    List<String> InputChargeOrders(List<GetChargeOrderInputDTO> list);

}
