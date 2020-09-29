package com.cqut.czb.bn.entity.dto.petrolManagement;

import com.cqut.czb.bn.entity.dto.PageDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GetPetrolListInputDTO extends PageDTO {
    private String petrolNum;//油卡号吗
    private String petrolKind;//油卡种类 0 国通，1 中石油，2 中石化
    private String petrolType;//油卡类型 0 虚拟卡，1 实体卡
    private String state;//油卡状态
    private String petrolDenomination;//油卡面额
    private String petrolPrice;//油卡售价
    private String area;//所属地区
    private Integer isSpecial;//特殊用户
    private Integer isSpecialPetrol; // 普通特殊油卡类型

    @DateTimeFormat(pattern ="yyyy-MM-dd")
    private Date createAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPetrolNum() {
        return petrolNum;
    }

    public void setPetrolNum(String petrolNum) {
        this.petrolNum = petrolNum;
    }

    public String getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(String petrolKind) {
        this.petrolKind = petrolKind;
    }

    public String getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(String petrolType) {
        this.petrolType = petrolType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPetrolDenomination() {
        return petrolDenomination;
    }

    public void setPetrolDenomination(String petrolDenomination) {
        this.petrolDenomination = petrolDenomination;
    }

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Integer isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Integer getIsSpecialPetrol() {
        return isSpecialPetrol;
    }

    public void setIsSpecialPetrol(Integer isSpecialPetrol) {
        this.isSpecialPetrol = isSpecialPetrol;
    }
}
