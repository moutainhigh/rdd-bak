package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ClassName: GeneralPartnerUserPageDTO
 * @Author: Iriya720
 * @Date: 2019/11/16
 * @Description:
 * @version: v1.0
 */
public class GeneralPartnerUserPageDTO extends PageDTO {
    private String userId = null;
    private String account = null;
    private String createAt;
    private String area = null;
    private String superiorUserAccount = null;
    private Integer isVip = null;
    private Integer startPage = null;
    private String right = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSuperiorUserAccount() {
        return superiorUserAccount;
    }

    public void setSuperiorUserAccount(String superiorUserAccount) {
        this.superiorUserAccount = superiorUserAccount;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getStartPage() {
        return (getCurrentPage() -1) * getPageSize();
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public String getRight() {
        if(!"".equals(getArea()) && null != getArea()){
            return "right";
        }else{
            return "";
        }
    }

    public void setRight(String right) {
        this.right = right;
    }
}
