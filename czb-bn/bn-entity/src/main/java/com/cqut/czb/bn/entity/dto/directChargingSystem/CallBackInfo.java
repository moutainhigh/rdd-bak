package com.cqut.czb.bn.entity.dto.directChargingSystem;

public class CallBackInfo {

    String msg;
    String id;
    Double amount;
    String no;
    Integer status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CallBackInfo{" +
                "msg='" + msg + '\'' +
                ", id='" + id + '\'' +
                ", amount=" + amount +
                ", no='" + no + '\'' +
                ", status=" + status +
                '}';
    }
}
