package com.cqut.czb.bn.entity.dto.food.AppOrderPage;

import java.util.List;

//所有商品分类
public class AllDishDTO {

    //标识是那种类型的餐点
    private String label;

    //保存所有的餐点
    private List<DishDTO> dishDTOS;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<DishDTO> getDishDTOS() {
        return dishDTOS;
    }

    public void setDishDTOS(List<DishDTO> dishDTOS) {
        this.dishDTOS = dishDTOS;
    }
}
