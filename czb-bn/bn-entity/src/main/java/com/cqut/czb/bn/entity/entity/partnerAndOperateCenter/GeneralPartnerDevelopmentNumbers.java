package com.cqut.czb.bn.entity.entity.partnerAndOperateCenter;



public class GeneralPartnerDevelopmentNumbers {

    //普通合伙人id
    private String userId;

    //时间类型id
    private String  dateType;

    //时间类型判定
    private Integer condition;

    //日期格式化
    private String format;


    public GeneralPartnerDevelopmentNumbers(String userId, Integer condition) {
        this.userId = userId;
        this.condition = condition;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateType() {
        if (condition == 0) {
            return "YEAR";
        } else if (condition == 1) {
            return "MONTH";
        } else {
            return "DAY";
        }
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public String getFormat() {
        if (condition == 0) {
            return "%Y";
        } else if (condition == 1) {
            return "%Y-%m";
        } else {
            return "%Y-%m-%d";
        }
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
