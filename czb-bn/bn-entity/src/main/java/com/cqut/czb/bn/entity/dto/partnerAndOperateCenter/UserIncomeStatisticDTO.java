package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

/**
 * @ClassName: UserIncomeStatistic
 * @Author: Iriya720
 * @Date: 2019/11/17
 * @Description:
 * @version: v1.0
 */
public class UserIncomeStatisticDTO {
    private String userId;
    private Integer condition;
    private String format;
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
        if(condition == 0){
            return "%Y-%m-%d";
        }else if(condition == 1){
            return "%Y-%m";
        }else{
            return "%Y";
        }
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getType() {
        if(condition == 0){
            return "DAY";
        }else if(condition == 1){
            return "MONTH";
        }else{
            return "YEAR";
        }
    }

    public void setType(String type) {
        this.type = type;
    }
}
