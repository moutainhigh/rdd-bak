package com.cqut.czb.bn.entity.dto.ThirdBusiness;

public class GetChargeOrderInputDTO {

    //订单id
    private String orderId;

    //充值状态
    private String state;

    //信息描述
    private String message;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
