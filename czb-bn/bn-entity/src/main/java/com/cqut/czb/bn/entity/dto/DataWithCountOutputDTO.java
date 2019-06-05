package com.cqut.czb.bn.entity.dto;

public class DataWithCountOutputDTO<T> {
    private T data;
    private String count;

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
