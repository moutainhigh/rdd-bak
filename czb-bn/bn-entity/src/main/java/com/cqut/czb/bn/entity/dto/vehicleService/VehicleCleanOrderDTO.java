package com.cqut.czb.bn.entity.dto.vehicleService;

import org.springframework.stereotype.Component;
import java.util.Date;
@Component
public class VehicleCleanOrderDTO {
        private String serverOrderId;

        private String userId;

        private String riderId;

        private String thirdOrder;

        private Integer payStatus;

        private Double actualPrice;

        private String vehicleId;

        private Integer processStatus;

        private Date cancelTime;

        private String cancelPersonId;

        private String cancelReason;

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

