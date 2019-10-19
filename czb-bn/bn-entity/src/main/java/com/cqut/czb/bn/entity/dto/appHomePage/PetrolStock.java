package com.cqut.czb.bn.entity.dto.appHomePage;

/**
 * @author: lyk
 * @date: 10/19/2019
 */
public class PetrolStock {
    private Integer total;
    private Integer petrolType;
    private String showMessage;
    private Integer petrolKind;

    public Integer getPetrolKind() {
        return petrolKind;
    }

    public void setPetrolKind(Integer petrolKind) {
        this.petrolKind = petrolKind;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPetrolType() {
        return petrolType;
    }

    public void setPetrolType(Integer petrolType) {
        this.petrolType = petrolType;
    }

    public String getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }
}
