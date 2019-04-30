package com.cqut.czb.bn.entity.dto.petrolSaleConfig;

import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;

import java.util.List;

public class PetrolSaleConfigOutputDTO {
    List<PetrolSaleConfig> areaConfigs;
    String area;

    public List<PetrolSaleConfig> getAreaConfigs() {
        return areaConfigs;
    }

    public void setAreaConfigs(List<PetrolSaleConfig> areaConfigs) {
        this.areaConfigs = areaConfigs;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
