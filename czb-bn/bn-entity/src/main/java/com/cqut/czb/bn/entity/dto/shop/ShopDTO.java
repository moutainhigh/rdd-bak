package com.cqut.czb.bn.entity.dto.shop;

import com.cqut.czb.bn.entity.entity.FileFunction;

import java.util.Date;
import java.util.List;

public class ShopDTO {
    private String shopId;

    private String userId;

    private String shopName;

    private String shopPhone;

    private String userAccount;

    private String  userName;

    private String shopContent;

    private String shopAddress;

    private String savePath;  //商铺的标志图片路径

    private String code;  //特征码

    private List<FileFunctionDTO> fileList;

    private String deleteId;    //删除的图片

    private Integer isLabelImg;  //是否为店铺的标签图片 0 否 1 是

    private Integer audit;  //审核状态

    private Integer shopType;

    private Double longitude;

    private Double latitude;

    private Date startingTimeBusiness;

    private Date endTimeBusiness;

    private String shopImg;

    private Integer orderWriteOff;

    private Integer isRecommend;

    private Date createAt;

    private Date updateAt;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone == null ? null : shopPhone.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopContent() {
        return shopContent;
    }

    public void setShopContent(String shopContent) {
        this.shopContent = shopContent == null ? null : shopContent.trim();
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress == null ? null : shopAddress.trim();

    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public Integer getIsLabelImg() {
        return isLabelImg;
    }

    public void setIsLabelImg(Integer isLabelImg) {
        this.isLabelImg = isLabelImg;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getStartingTimeBusiness() {
        return startingTimeBusiness;
    }

    public void setStartingTimeBusiness(Date startingTimeBusiness) {
        this.startingTimeBusiness = startingTimeBusiness;
    }

    public Date getEndTimeBusiness() {
        return endTimeBusiness;
    }

    public void setEndTimeBusiness(Date endTimeBusiness) {
        this.endTimeBusiness = endTimeBusiness;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public Integer getOrderWriteOff() {
        return orderWriteOff;
    }

    public void setOrderWriteOff(Integer orderWriteOff) {
        this.orderWriteOff = orderWriteOff;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
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

    public List<FileFunctionDTO> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileFunctionDTO> fileList) {
        this.fileList = fileList;
    }
}
