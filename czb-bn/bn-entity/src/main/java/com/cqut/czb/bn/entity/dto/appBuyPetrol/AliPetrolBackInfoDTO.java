package com.cqut.czb.bn.entity.dto.appBuyPetrol;

import com.cqut.czb.bn.entity.entity.Petrol;

public class AliPetrolBackInfoDTO {

   private String paymentOrder;//支付订单

   private Petrol petrol;//对应的油卡

    public String getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(String paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public Petrol getPetrol() {
        return petrol;
    }

    public void setPetrol(Petrol petrol) {
        this.petrol = petrol;
    }
}
