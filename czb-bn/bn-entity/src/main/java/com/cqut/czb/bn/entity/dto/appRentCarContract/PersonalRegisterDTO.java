package com.cqut.czb.bn.entity.dto.appRentCarContract;

public class PersonalRegisterDTO {
    /**
     * 用户姓名（最长 15 字符）
     */
    private String userName;

    /**
     * 身份地区：0 大陆，1 香港，2 台湾，3 澳门，4 外籍人员
     */
    private String identityRegion;

    /**
     * 证件类型：a 身份证， b 护照， d 港澳通行证， e 台胞证， f 港澳居民来往内地通行证， z 其他
     */
    private String certifyType;

    /**
     * 身份证号码，应用内唯一。
     */
    private String certifyNum;

    /**
     * 手机号地区：0 大陆，1 香港、澳门，2 台湾
     */
    private String phoneRegion;

    /**
     * 手机号：1.大陆,首位为 1，长度 11 位纯数字；2.香港、澳门,长度为 8 的纯数字；3.台湾,长度为 10 的纯数字
     */
    private String phoneNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityRegion() {
        return identityRegion;
    }

    public void setIdentityRegion(String identityRegion) {
        this.identityRegion = identityRegion;
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

    public String getPhoneRegion() {
        return phoneRegion;
    }

    public void setPhoneRegion(String phoneRegion) {
        this.phoneRegion = phoneRegion;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
