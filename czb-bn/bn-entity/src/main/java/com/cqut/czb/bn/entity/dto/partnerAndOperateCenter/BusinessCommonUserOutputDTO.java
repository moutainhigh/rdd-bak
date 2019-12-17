package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
* @Description:    事业合伙人下普通用户查询结果DTO
* @Author:  WenJunYuan
* @CreateDate:     2019/11/16 17:14
* @Version:        1.0
*/
public class BusinessCommonUserOutputDTO implements Serializable {

    /**
     * 普通用户userId
     */
    private String userId;

    /**
     * 普通用户电话号码
     */
    private String mobile;

    /**
     * 普通用户真实姓名
     */
    private String realName;

    /**
     * 普通用户所属区域
     */
    private String area;
    /**
     * 普通用户注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", timezone = "GMT+8")
    private Date createAt;

    /**
     * 年费到期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:MM:ss", timezone = "GMT+8")
    private Date expireTime;

    /**
     * 推广类型   1直推  2间推
     */
    private Integer promotionType;

    /**
     * 推荐人姓名
     */
    private String spreadName;

    /**
     * 推荐人电话号码
     */
    private String promotionMobile;

    /**
     * 油卡消费总金额
     */
    private Double petrolMoney;

    /**
     * 我的收益
     */
    private Double myProfit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public String getSpreadName() {
        return spreadName;
    }

    public void setSpreadName(String spreadName) {
        this.spreadName = spreadName;
    }

    public String getPromotionMobile() {
        return promotionMobile;
    }

    public void setPromotionMobile(String promotionMobile) {
        this.promotionMobile = promotionMobile;
    }

    public Double getPetrolMoney() {
        return petrolMoney;
    }

    public void setPetrolMoney(Double petrolMoney) {
        this.petrolMoney = petrolMoney;
    }

    public Double getMyProfit() {
        return myProfit;
    }

    public void setMyProfit(Double myProfit) {
        this.myProfit = myProfit;
    }
}
