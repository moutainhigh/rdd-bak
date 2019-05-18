package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class AppRouter {
    private String routerId;

    private Integer appType;

    private String menuName;

    private Integer isShow;

    private String iconPathId;

    private String menuIdentityCode;

    private Date createAt;

    private Date updateAt;

    private String androidPath;

    private String iosPath;

    private Integer order;

    private Integer userType;

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId == null ? null : routerId.trim();
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getIconPathId() {
        return iconPathId;
    }

    public void setIconPathId(String iconPathId) {
        this.iconPathId = iconPathId == null ? null : iconPathId.trim();
    }

    public String getMenuIdentityCode() {
        return menuIdentityCode;
    }

    public void setMenuIdentityCode(String menuIdentityCode) {
        this.menuIdentityCode = menuIdentityCode == null ? null : menuIdentityCode.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getAndroidPath() {
        return androidPath;
    }

    public void setAndroidPath(String androidPath) {
        this.androidPath = androidPath == null ? null : androidPath.trim();
    }

    public String getIosPath() {
        return iosPath;
    }

    public void setIosPath(String iosPath) {
        this.iosPath = iosPath == null ? null : iosPath.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}