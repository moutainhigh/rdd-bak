package com.cqut.czb.bn.entity.dto;

public class NewOldPwdDTO {

    private String OldPWD;

    private String NewPWD;

    private Integer isSpecial;

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getOldPWD() {
        return OldPWD;
    }

    public void setOldPWD(String oldPWD) {
        OldPWD = oldPWD;
    }

    public String getNewPWD() {
        return NewPWD;
    }

    public void setNewPWD(String newPWD) {
        NewPWD = newPWD;
    }
}
