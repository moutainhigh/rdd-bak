package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

/**
 * @Description
 * @auther nihao
 * @create 2019-12-01 16:01
 */
public class WeChatCommodityComdirmOrderDTO {
    private String orderId;

    private String shopId;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}