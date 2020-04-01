package com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult;

import java.util.List;

public class ReadCardOutput {
    private String result;

    private String errorMsg;

    private String templateId;

    List<MasterCardAsn> data;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
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

    public List<MasterCardAsn> getData() {
        return data;
    }

    public void setData(List<MasterCardAsn> data) {
        this.data = data;
    }
}
