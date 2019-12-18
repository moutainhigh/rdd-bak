package com.cqut.czb.bn.entity.dto.WeChatCommodity;

import java.util.List;

public class PayInputDTO {
    //商品id
    private String commodityId;

    //商品数量
    private Integer commodityNum;

    //购买者电话
    private String userPhone;

    //备注
    private String remark;

    //地址id
    private String addressId;

    //属性id
    private List<String> commodityAttrIds;

    public List<String> getCommodityAttrIds() {
        return commodityAttrIds;
    }

    public void setCommodityAttrIds(List<String> commodityAttrIds) {
        this.commodityAttrIds = commodityAttrIds;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getCommodityNum() {
        return commodityNum;
    }

    public void setCommodityNum(Integer commodityNum) {
        this.commodityNum = commodityNum;
    }
}
