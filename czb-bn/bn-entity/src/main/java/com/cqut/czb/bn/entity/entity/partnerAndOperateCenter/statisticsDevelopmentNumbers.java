package com.cqut.czb.bn.entity.entity.partnerAndOperateCenter;

public class statisticsDevelopmentNumbers {

    //用户ID
    private String userId;

    //时期类型判定
    private Integer condition;

    //时间类型转换
    private String format;

    //时间类型
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }

    public String getFormat() {
        if (condition == 0) {
            return "%Y-%m-%d";
        } else if (condition == 1) {
            return "%Y-%m";
        } else {
            return "%Y";
        }
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        if (condition == 0) {
            return "DAY";
        } else if (condition == 1) {
            return "MONTH";
        } else {
            return "YEAR";
        }
    }

    public void setType(String type) {
        this.type = type;
    }
}
