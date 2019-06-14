package com.cqut.czb.bn.entity.dto.appBuyPetrol;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.entity.Petrol;


public class WeChatPetrolBackInfoDTO {

   private JSONObject WeChatPaymentOrder;//支付订单

   private Petrol petrol;//对应的油卡

    public JSONObject getWeChatPaymentOrder() {
        return WeChatPaymentOrder;
    }

    public void setWeChatPaymentOrder(JSONObject weChatPaymentOrder) {
        WeChatPaymentOrder = weChatPaymentOrder;
    }

    public Petrol getPetrol() {
        return petrol;
    }

    public void setPetrol(Petrol petrol) {
        this.petrol = petrol;
    }
}
