package com.cqut.czb.bn.entity.dto.appBuyPetrol;

public class PetrolInputDTO {

    private String ownerId;

    private Integer petrolKind;

    private Double petrolPrice;

    private String userAccount;

    public PetrolInputDTO() {
    }

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public Double getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(Double petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
