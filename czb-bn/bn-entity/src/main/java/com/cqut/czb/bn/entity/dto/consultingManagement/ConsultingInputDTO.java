package com.cqut.czb.bn.entity.dto.consultingManagement;

import com.cqut.czb.bn.entity.dto.PageDTO;

public class ConsultingInputDTO extends PageDTO {
    private String enterpriseName;
    private String contactPhone;
    private Integer isHandled;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getIsHandled() {
        return isHandled;
    }

    public void setIsHandled(Integer isHandled) {
        this.isHandled = isHandled;
    }
}
