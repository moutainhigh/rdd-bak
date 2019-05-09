package com.cqut.czb.bn.entity.dto.rentCar;

import javax.validation.constraints.NotNull;

public class PersonSignedInputInfo {
    /**
     * 认证码
     */
    @NotNull(message = "认证码不能为空")
    private String identifyCode;

    /**
     * 身份证
     */
    @NotNull(message = "身份证号码不能为空")
    private String personId;

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
