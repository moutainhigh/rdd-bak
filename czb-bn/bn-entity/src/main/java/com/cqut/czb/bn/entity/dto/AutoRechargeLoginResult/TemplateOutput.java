package com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult;

import java.util.List;

public class TemplateOutput {
    private String acount;
    private String result;
    private String errorMsg;
    private Integer pageCount;
    private Integer rowCount;
    private List<TemplateOutData> data;

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

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

    public List<TemplateOutData> getData() {
        return data;
    }

    public void setData(List<TemplateOutData> data) {
        this.data = data;
    }
}
