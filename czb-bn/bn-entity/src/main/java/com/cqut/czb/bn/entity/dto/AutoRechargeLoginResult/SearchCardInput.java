package com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult;

public class SearchCardInput {
    private String id;
    private String templateId;
    private String pageSize;
    private String currPage;
    private String cardAsn;
    private String date;
    private String date1;
    private String cardAsns;

    public String getCardAsns() {
        return cardAsns;
    }

    public void setCardAsns(String cardAsns) {
        this.cardAsns = cardAsns;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getCurrPage() {
        return currPage;
    }

    public void setCurrPage(String currPage) {
        this.currPage = currPage;
    }

    public String getCardAsn() {
        return cardAsn;
    }

    public void setCardAsn(String cardAsn) {
        this.cardAsn = cardAsn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }
}
