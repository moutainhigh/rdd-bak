package com.cqut.czb.bn.entity.dto.WeChatSmallProgram;

import java.util.Map;

public class WxTemplateMsg {
    private String appid;
    private String template_id;
    private String url;
    private MiniProgram miniprogram;
    private Map<String, MsgData> data;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MiniProgram getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(MiniProgram miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, MsgData> getData() {
        return data;
    }

    public void setData(Map<String, MsgData> data) {
        this.data = data;
    }

    public static class MsgData{
        private String value;
        private String color;

        public MsgData(String value, String color) {
            this.value = value;
            this.color = color;
        }

        public MsgData() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class MiniProgram{
        private String appid;
        private String pagepath;

        public MiniProgram(String appid, String pagepath) {
            this.appid = appid;
            this.pagepath = pagepath;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPagepath() {
            return pagepath;
        }

        public void setPagepath(String pagepath) {
            this.pagepath = pagepath;
        }
    }

}
