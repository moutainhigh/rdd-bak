package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class AutoDirectDto {
    private String nameTitle;
    private String nameContent;
    private String state;
    private String time;

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getNameContent() {
        return nameContent;
    }

    public void setNameContent(String nameContent) {
        this.nameContent = nameContent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AutoDirectDto{" +
                "nameTitle='" + nameTitle + '\'' +
                ", nameContent='" + nameContent + '\'' +
                ", state='" + state + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
