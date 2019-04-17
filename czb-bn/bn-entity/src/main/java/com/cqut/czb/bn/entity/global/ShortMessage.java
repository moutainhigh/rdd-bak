package com.cqut.czb.bn.entity.global;

import java.util.HashMap;
import java.util.Map;

public class ShortMessage {

    private String appKey = "74f93e4ec214150bef62e663b3e306a6";

    private int appId = 1400030325;

    private String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";

    private String defaultNationCode = "86";

    private Map<String, Integer> templateIds = new HashMap<>();//模板Id

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefaultNationCode() {
        return defaultNationCode;
    }

    public void setDefaultNationCode(String defaultNationCode) {
        this.defaultNationCode = defaultNationCode;
    }

    public Map<String, Integer> getTemplateIds() {
        templateIds.put("register", 27713);
        templateIds.put("checkPass", 120388);
        templateIds.put("reject", 121943);
        templateIds.put("notice", 129205);
        return templateIds;
    }

    public void setTemplateIds(Map<String, Integer> templateIds) {
        this.templateIds = templateIds;
    }
}
