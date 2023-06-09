package com.cqut.czb.bn.entity.dto.appRentCarContract;

public class EnterpriseRegisterDTO {
    /**
     * 企业名称
     */
    private String userName;

    /**
     * 企业相关证件类型包含：1.社会信用代码，2.营业执照注册号，3.组织机构代码
     */
    private String certifyType;

    /**
     * 认证号码，应用内唯一：1.社会统一信用代码，长度为18；2.营业执照，长度为 18；3.组织机构代码，长度为 9
     */
    private String certifyNum;

    /**
     * 企业类型用户的手机号仅支持中国大陆：首位为 1，长度11 位纯数字
     */
    private String phoneNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCertifyType() {
        return certifyType;
    }

    public void setCertifyType(String certifyType) {
        this.certifyType = certifyType;
    }

    public String getCertifyNum() {
        return certifyNum;
    }

    public void setCertifyNum(String certifyNum) {
        this.certifyNum = certifyNum;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
