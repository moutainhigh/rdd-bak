package com.cqut.czb.bn.entity.dto.appHomePage;

/**
 * @author: lyk
 * @date: 10/19/2019
 */
public class PetrolStockSwitch {
    private Boolean UseFakeData;
    private Integer fakeTotal;
    public Boolean getUseFakeData() {
        return UseFakeData;
    }

    public void setUseFakeData(Boolean useFakeData) {
        UseFakeData = useFakeData;
    }

    public Integer getFakeTotal() {
        return fakeTotal;
    }

    public void setFakeTotal(Integer fakeTotal) {
        this.fakeTotal = fakeTotal;
    }
}
