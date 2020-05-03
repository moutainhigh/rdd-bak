package com.cqut.czb.bn.service.PaymentProcess;

import com.cqut.czb.bn.entity.dto.paymentCallBack.AliPetrolCouponsDTO;

public interface RequestLuPayService {

    String httpRequestGETLuPay(AliPetrolCouponsDTO petrolCouponsDTO);



}
