package com.cqut.czb.bn.entity.dto.partnerAndOperateCenter;

public class ManagerStaticsDTO {

    /** VIP直返 */
    private double directVip;
    /** 用户人数 */
    private int userCount;

    /** 普通用户人数 */
    private int ordinaryUser;

    /** 普通合伙人人数 */
    private int ordinaryPartner;

    /** 事业合伙人人数 */
    private int businessPartner;
    private int developmentNumber;

    /** 返佣金额 */
    private double allCommissionIncome;
    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;

    /** VIP用户 */
    private int vipUser;

    /** VIP直推返利 */
    private double directVipIncome;

    /** VIP间推返利 */
    private double indirectVipIncome;

    /** 直推油卡返利 */
    private double directPetrolIncome;

    /** 间推油卡返利 */
    private double indirectPetrolIncome;

    public int getDevelopmentNumber() {
        return developmentNumber;
    }

    public void setDevelopmentNumber(int developmentNumber) {
        this.developmentNumber = developmentNumber;
    }

    public double getAllCommissionIncome() {
        return allCommissionIncome;
    }

    public void setAllCommissionIncome(double allCommissionIncome) {
        this.allCommissionIncome = allCommissionIncome;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public int getThirdNumber() {
        return thirdNumber;
    }

    public void setThirdNumber(int thirdNumber) {
        this.thirdNumber = thirdNumber;
    }

    public int getOrdinaryUser() {
        return ordinaryUser;
    }

    public void setOrdinaryUser(int ordinaryUser) {
        this.ordinaryUser = ordinaryUser;
    }

    public int getOrdinaryPartner() {
        return ordinaryPartner;
    }

    public void setOrdinaryPartner(int ordinaryPartner) {
        this.ordinaryPartner = ordinaryPartner;
    }

    public int getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(int businessPartner) {
        this.businessPartner = businessPartner;
    }

    public int getVipUser() {
        return vipUser;
    }

    public void setVipUser(int vipUser) {
        this.vipUser = vipUser;
    }

    public double getDirectPetrolIncome() {
        return directPetrolIncome;
    }

    public void setDirectPetrolIncome(double directPetrolIncome) {
        this.directPetrolIncome = directPetrolIncome;
    }

    public double getIndirectPetrolIncome() {
        return indirectPetrolIncome;
    }

    public void setIndirectPetrolIncome(double indirectPetrolIncome) {
        this.indirectPetrolIncome = indirectPetrolIncome;
    }

    public double getDirectVipIncome() {
        return directVipIncome;
    }

    public void setDirectVipIncome(double directVipIncome) {
        this.directVipIncome = directVipIncome;
    }

    public double getIndirectVipIncome() {
        return indirectVipIncome;
    }

    public void setIndirectVipIncome(double indirectVipIncome) {
        this.indirectVipIncome = indirectVipIncome;
    }

    public double getDirectVip() {
        return directVip;
    }

    public void setDirectVip(double directVip) {
        this.directVip = directVip;
    }
}
