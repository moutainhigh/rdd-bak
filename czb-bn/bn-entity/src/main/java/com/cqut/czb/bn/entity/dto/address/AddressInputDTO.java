package com.cqut.czb.bn.entity.dto.address;

import com.cqut.czb.bn.entity.entity.Address;

import java.util.Date;

public class AddressInputDTO {
    private String addressId;

    private String province;

    private String city;

    private String area;

    private String detail;

    private String userId;

    private Date createAt;

    private Date updateAt;

    private String contactNumber;

    private String receiver;

    private int isDefault;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber == null ? null : contactNumber.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public Address getAddressEntity(){
        Address address = new Address();
        address.setAddressId(this.addressId);
        address.setProvince(this.province);
        address.setCity(this.city);
        address.setArea(this.area);
        address.setDetail(this.detail);
        address.setUserId(this.userId);
        address.setContactNumber(this.contactNumber);
        address.setReceiver(this.receiver);
        address.setCreateAt(this.createAt);
        address.setUpdateAt(this.updateAt);
        address.setIsDefault(this.isDefault);
        return address;
    }
}
