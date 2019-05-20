package com.cqut.czb.bn.entity.dto.petrolSaleConfig;

public class SetAreaConfigInputDTO {
    private String area;
    private String setIds;
    private String[] ids;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSetIds() {
        return setIds;
    }

    public void setSetIds(String setIds) {
        this.setIds = setIds;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
