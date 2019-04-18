package com.cqut.czb.bn.entity.entity;

import java.util.Date;

public class AppRouter {
    private String routerId;

    private Integer appType;

    private String menuName;

    private String menuPath;

    private Integer isShow;

    private String iconPathId;

    private String menuIdentityCode;

    private Date ctreateAt;

    private Date updateAt;

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

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath == null ? null : menuPath.trim();
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

    public Date getCtreateAt() {
        return ctreateAt;
    }

    public void setCtreateAt(Date ctreateAt) {
        this.ctreateAt = ctreateAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}