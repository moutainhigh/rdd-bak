package com.cqut.czb.bn.entity.dto.directCustomers;

public class ElectricityTotalDto {
    private int all;
    private int fail;
    private int successful;
    private int waiting;

    private double totalMoney;
    private double successfulMoney;
    private double failMoney;
    private double waitMoney;

    public double getWaitMoney() {
        return waitMoney;
    }

    public void setWaitMoney(double waitMoney) {
        this.waitMoney = waitMoney;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getSuccessfulMoney() {
        return successfulMoney;
    }

    public void setSuccessfulMoney(double successfulMoney) {
        this.successfulMoney = successfulMoney;
    }

    public double getFailMoney() {
        return failMoney;
    }

    public void setFailMoney(double failMoney) {
        this.failMoney = failMoney;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getSuccessful() {
        return successful;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public int getWaiting() {
        return waiting;
    }

    public void setWaiting(int waiting) {
        this.waiting = waiting;
    }

    @Override
    public String toString() {
        return "ElectricityTotalDto{" +
                "all=" + all +
                ", fail=" + fail +
                ", successful=" + successful +
                ", waiting=" + waiting +
                ", totalMoney=" + totalMoney +
                ", successfulMoney=" + successfulMoney +
                ", failMoney=" + failMoney +
                ", waitMoney=" + waitMoney +
                '}';
    }
}
