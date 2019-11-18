package com.cqut.czb.bn.entity.entity.partnerAndOperateCenter;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @ClassName: GeneralPartnerUserDTO
 * @Author: Iriya720
 * @Date: 2019/11/17
 * @Description:
 * @version: v1.0
 */
public class GeneralPartnerUser {
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 分页大小
     */
    private Integer pageSize;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户电话
     */
    private String userAccount;
    /**
     * 区域
     */
    private String area;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt;

    /**
     * 年费到期时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireAt;
    /**
     * 推荐类型
     */
    private Integer promotionType;
    /**
     * 推荐人
     */
    private String superiorUser;
    /**
     * 推荐人姓名
     */
    private String superiorUserName;
    /**
     * 推荐人电话
     */
    private String superiorUserAccount;
    /**
     * 累计购油收益
     */
    private Double accumulatedConsumptionAmount;
    /**
     * 我的收益
     */
    private Double myIncome;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public String getSuperiorUser() {
        return superiorUser;
    }

    public void setSuperiorUser(String superiorUser) {
        this.superiorUser = superiorUser;
    }

    public String getSuperiorUserName() {
        return superiorUserName;
    }

    public void setSuperiorUserName(String superiorUserName) {
        this.superiorUserName = superiorUserName;
    }

    public String getSuperiorUserAccount() {
        return superiorUserAccount;
    }

    public void setSuperiorUserAccount(String superiorUserAccount) {
        this.superiorUserAccount = superiorUserAccount;
    }

    public Double getAccumulatedConsumptionAmount() {
        return accumulatedConsumptionAmount;
    }

    public void setAccumulatedConsumptionAmount(Double accumulatedConsumptionAmount) {
        this.accumulatedConsumptionAmount = accumulatedConsumptionAmount;
    }

    public Double getMyIncome() {
        return myIncome;
    }

    public void setMyIncome(Double myIncome) {
        this.myIncome = myIncome;
    }
}
