package com.cqut.czb.bn.entity.dto;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-28 21:22
 */
public class WCPQRCode {
    private String errcode;

    private String errmsg;

    private String contentType;

    private String buffer;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }
}