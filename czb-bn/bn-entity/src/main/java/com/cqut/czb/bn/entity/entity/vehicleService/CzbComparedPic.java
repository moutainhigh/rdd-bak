package com.cqut.czb.bn.entity.entity.vehicleService;

import java.util.Date;

public class CzbComparedPic {
    private String comparedPicId;

    private String serverOrderId;

    private Byte status;

    private String fileId;

    private Date createAt;

    private Date updateAt;

    public String getComparedPicId() {
        return comparedPicId;
    }

    public void setComparedPicId(String comparedPicId) {
        this.comparedPicId = comparedPicId;
    }

    public String getServerOrderId() {
        return serverOrderId;
    }

    public void setServerOrderId(String serverOrderId) {
        this.serverOrderId = serverOrderId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}