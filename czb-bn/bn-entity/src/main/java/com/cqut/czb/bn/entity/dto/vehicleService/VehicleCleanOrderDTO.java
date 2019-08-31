package com.cqut.czb.bn.entity.dto.vehicleService;

import com.cqut.czb.bn.entity.entity.vehicleService.ComparedPic;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
public class VehicleCleanOrderDTO {
        private String serverOrderId;

        private String couponId;

        private String userId;

        private String userName;

        private String riderId;

        private String riderName;

        private String contactNumber;

        private String vehicleType;

        private String vehicleColor;

        private String vehicleSeries;

        private String userPhone;

        private String thirdOrder;

        private Integer payStatus;

        private Double actualPrice;

        private String vehicleId;

        private Integer processStatus;

        private Date cancelTime;

        private String cancelPersonId;

        private String cancelReason;

        private String licenseNumber;

        private List<ComparedPicDTO> OrderPic;

        private String customerServicePhone;

        private Integer evaluateLevel; //评价等级

        private String evaluateMessage; //评价信息

        private Date createAt;

        private Date updateAt;

    public String getServerOrderId() {
        return serverOrderId;
    }

    public void setServerOrderId(String serverOrderId) {
        this.serverOrderId = serverOrderId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries;
    }

    public String getUserId() {
            return userId;
        }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRiderId() {
            return riderId;
        }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getThirdOrder() {
        return thirdOrder;
    }

    public void setThirdOrder(String thirdOrder) {
        this.thirdOrder = thirdOrder;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelPersonId() {
        return cancelPersonId;
    }

    public void setCancelPersonId(String cancelPersonId) {
        this.cancelPersonId = cancelPersonId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public List<ComparedPicDTO> getOrderPic() {
        return OrderPic;
    }

    public void setOrderPic(List<ComparedPicDTO> orderPic) {
        OrderPic = orderPic;
    }

    public Integer getEvaluateLevel() {
            return evaluateLevel;
        }

    public void setEvaluateLevel(Integer evaluateLevel) {
        this.evaluateLevel = evaluateLevel;
    }

    public String getEvaluateMessage() {
        return evaluateMessage;
    }

    public void setEvaluateMessage(String evaluateMessage) {
        this.evaluateMessage = evaluateMessage;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getCustomerServicePhone() {
        return customerServicePhone;
    }

    public void setCustomerServicePhone(String customerServicePhone) {
        this.customerServicePhone = customerServicePhone;
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

