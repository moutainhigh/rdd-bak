package com.cqut.czb.bn.entity.dto;

public class TotalCountDTO {
    private int totalCount;
    private int totalSuccess;
    private int totalFailure;
    private int WXTotal;
    private int WXSuccess;
    private int WXFailure;
    private int ALTotal;
    private int ALSuccess;
    private int ALFailure;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalSuccess() {
        return totalSuccess;
    }

    public void setTotalSuccess(int totalSuccess) {
        this.totalSuccess = totalSuccess;
    }

    public int getTotalFailure() {
        return totalFailure;
    }

    public void setTotalFailure(int totalFailure) {
        this.totalFailure = totalFailure;
    }

    public int getWXTotal() {
        return WXTotal;
    }

    public void setWXTotal(int WXTotal) {
        this.WXTotal = WXTotal;
    }

    public int getWXSuccess() {
        return WXSuccess;
    }

    public void setWXSuccess(int WXSuccess) {
        this.WXSuccess = WXSuccess;
    }

    public int getWXFailure() {
        return WXFailure;
    }

    public void setWXFailure(int WXFailure) {
        this.WXFailure = WXFailure;
    }

    public int getALTotal() {
        return ALTotal;
    }

    public void setALTotal(int ALTotal) {
        this.ALTotal = ALTotal;
    }

    public int getALSuccess() {
        return ALSuccess;
    }

    public void setALSuccess(int ALSuccess) {
        this.ALSuccess = ALSuccess;
    }

    public int getALFailure() {
        return ALFailure;
    }

    public void setALFailure(int ALFailure) {
        this.ALFailure = ALFailure;
    }
}
