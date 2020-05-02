package com.cqut.czb.bn.service.appPaymentService;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;

import java.util.Map;

public interface PurchaseCouponService {

    Map<String,Object> PurchaseControl(PetrolInputDTO petrolInputDTO);

}
