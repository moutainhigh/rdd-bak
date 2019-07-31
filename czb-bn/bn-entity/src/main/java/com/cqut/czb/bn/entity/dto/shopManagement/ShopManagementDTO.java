package com.cqut.czb.bn.entity.dto.shopManagement;

import com.cqut.czb.bn.entity.dto.PageDTO;

/**
 * @Description
 * @auther nihao
 * @create 2019-07-28 14:51
 */
public class ShopManagementDTO extends PageDTO {
    /**
     * 商铺ID
     */
    private String shopId;

    /**
     * 联系人ID
     */
    private String userId;

    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 商铺联系人名称
     */
    private String userName;

    /**
     * 商铺联系人电话
     */
    private String shopPhone;

    /**
     * 商铺地址
     */
    private String shopAddress;

    /**
     * 历史交易总额
     */
    private Double totalHistoricalTransactions;

    /**
     * 未结算订单个数
     */
    private Integer numberOfOutstandingAccounts;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNumberOfOutstandingAccounts() {
        return numberOfOutstandingAccounts;
    }

    public void setNumberOfOutstandingAccounts(Integer numberOfOutstandingAccounts) {
        this.numberOfOutstandingAccounts = numberOfOutstandingAccounts;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Double getTotalHistoricalTransactions() {
        return totalHistoricalTransactions;
    }

    public void setTotalHistoricalTransactions(Double totalHistoricalTransactions) {
        this.totalHistoricalTransactions = totalHistoricalTransactions;
    }
}