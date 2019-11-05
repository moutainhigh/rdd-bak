package com.cqut.czb.bn.entity.dto;

public class DataWithCountOutputDTO<T> {

    private T data;

    private String count;

    private String todayCount;//今日总额

    private String todayNum;//今日总数量

    public String getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(String todayCount) {
        this.todayCount = todayCount;
    }

    public String getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(String todayNum) {
        this.todayNum = todayNum;
    }

    public T getData(){
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
