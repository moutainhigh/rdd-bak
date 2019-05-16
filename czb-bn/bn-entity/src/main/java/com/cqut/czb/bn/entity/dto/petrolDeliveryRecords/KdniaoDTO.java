package com.cqut.czb.bn.entity.dto.petrolDeliveryRecords;

import java.util.List;

public class KdniaoDTO {
    private String EBusinessID;

    private String OrderCode;

    private String ShipperCode;

    private String LogisticCode;

    private Boolean Success;

    private String Reason;

    private String State;

    List<Traces> Traces;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public List<com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.Traces> getTraces() {
        return Traces;
    }

    public void setTraces(List<com.cqut.czb.bn.entity.dto.petrolDeliveryRecords.Traces> traces) {
        Traces = traces;
    }
}
