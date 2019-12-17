package com.cqut.czb.bn.entity.dto.PayConfig;

import com.alibaba.fastjson.JSONObject;

public class WeChatBackDTO {

    private String paySignStr;

    private JSONObject jsonObject;

    public String getPaySignStr() {
        return paySignStr;
    }

    public void setPaySignStr(String paySignStr) {
        this.paySignStr = paySignStr;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
