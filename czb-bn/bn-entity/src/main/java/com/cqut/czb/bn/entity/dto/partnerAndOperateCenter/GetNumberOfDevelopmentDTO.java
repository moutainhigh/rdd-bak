package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

public class GetNumberOfDevelopmentDTO {

    //当前月份直推人数
    private Integer firstDirect;

    //当前月份间推人数
    private Integer firstIndirect;

    //前一时间段直推人数
    private Integer secondDirect;

    //前一时间段间推人数
    private Integer secondIndirect;

    //前两个时间段直推人数
    private Integer thirdDirect;

    //前两个时间段间推人数
    private Integer thirdIndirect;

    public Integer getFirstDirect() {
        return firstDirect;
    }

    public void setFirstDirect(Integer firstDirect) {
        this.firstDirect = firstDirect;
    }

    public Integer getFirstIndirect() {
        return firstIndirect;
    }

    public void setFirstIndirect(Integer firstIndirect) {
        this.firstIndirect = firstIndirect;
    }

    public Integer getSecondDirect() {
        return secondDirect;
    }

    public void setSecondDirect(Integer secondDirect) {
        this.secondDirect = secondDirect;
    }

    public Integer getSecondIndirect() {
        return secondIndirect;
    }

    public void setSecondIndirect(Integer secondIndirect) {
        this.secondIndirect = secondIndirect;
    }

    public Integer getThirdDirect() {
        return thirdDirect;
    }

    public void setThirdDirect(Integer thirdDirect) {
        this.thirdDirect = thirdDirect;
    }

    public Integer getThirdIndirect() {
        return thirdIndirect;
    }

    public void setThirdIndirect(Integer thirdIndirect) {
        this.thirdIndirect = thirdIndirect;
    }
}
