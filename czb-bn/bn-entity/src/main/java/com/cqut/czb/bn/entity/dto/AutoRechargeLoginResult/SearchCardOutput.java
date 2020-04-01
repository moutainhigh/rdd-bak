package com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult;

import java.util.List;

public class SearchCardOutput {
    private String acount;
    private String result;
    private String errorMsg;
    private String pageCount;
    private String rowCount;
    private List<SearchCardUser> data;

    public String getAcount() {
        return acount;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public List<SearchCardUser> getData() {
        return data;
    }

    public void setData(List<SearchCardUser> data) {
        this.data = data;
    }
}
