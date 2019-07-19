package com.cqut.czb.bn.entity.dto.appHomePage;

/**
 * 创建人：陈德强
 * 时间：2019/4/30
 */
public class appAnnouncementDTO {

    private String announcementTitle;

    private String announcementContent;

    private Integer announcementType;

    private Integer announcementOrder;

    private String locationCode;

    private String fileName;

    private String uploader;

    private String savePath;

    private String remark;

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public void setAnnouncementContent(String announcementContent) {
        this.announcementContent = announcementContent;
    }

    public Integer getAnnouncementType() {
        return announcementType;
    }

    public void setAnnouncementType(Integer announcementType) {
        this.announcementType = announcementType;
    }

    public Integer getAnnouncementOrder() {
        return announcementOrder;
    }

    public void setAnnouncementOrder(Integer announcementOrder) {
        this.announcementOrder = announcementOrder;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
