package com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder;

import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.ManageFood.SetInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class FoodAllInfo {
    private PageInfo<FoodOrder> foodOrderList;

    private Food food;

    private List<SetInfo> sets;

    public PageInfo<FoodOrder> getFoodOrderList() {
        return foodOrderList;
    }

    public void setFoodOrderList(PageInfo<FoodOrder> foodOrderList) {
        this.foodOrderList = foodOrderList;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public List<SetInfo> getSets() {
        return sets;
    }

    public void setSets(List<SetInfo> sets) {
        this.sets = sets;
    }
}
