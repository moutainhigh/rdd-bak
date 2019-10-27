package com.cqut.czb.bn.service.impl.food.WebOrder;

import com.cqut.czb.bn.dao.mapper.food.DishSystemMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.WebOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder.FoodAllInfo;
import com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder.FoodOrder;
import com.cqut.czb.bn.entity.dto.ManageFood.SetInfo;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.WebOrderService.WebOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebOrderServiceImpl implements WebOrderService{
    @Autowired
    WebOrderMapperExtra mapperExtra;

    @Autowired
    DishSystemMapperExtra dishSystemMapperExtra;


    /**
     * 查询
     * @param foodOrder
     * @param pageDTO
     * @param user
     * @return
     */
    @Override
    public JSONResult search(FoodOrder foodOrder, PageDTO pageDTO, User user) {
        foodOrder.setShopId(dishSystemMapperExtra.getShopId(user.getUserId()));
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<FoodOrder> foodOrders = mapperExtra.search(foodOrder);
        Double allMoney = new Double("0");
        for (FoodOrder data: foodOrders) {
            if((data.getDiningStatus() == 0 || data.getDiningStatus() == 2) && data.getPayStatus() == 1)
            allMoney += data.getActualPrice();
        }
        List<Food> foods = new ArrayList<>();
        if(foodOrder.getSearchOrderId() != null && !foodOrder.getSearchOrderId().equals("")) {
            foods = mapperExtra.getOrderDishes(foodOrder.getSearchOrderId());
            List<SetInfo> countAmountFoods = new ArrayList<>();
            for (Food data: foods) {
                if(countAmountFoods.size() != 0) {
                    boolean isExist = false;
                    for(SetInfo set: countAmountFoods) {
                        if(set.getSingleId().equals(data.getDishId())) {
                            isExist = true;
                            set.setSetAmount(set.getSetAmount() + 1);
                            break;
                        }
                    }
                    if(!isExist){
                        SetInfo set = new SetInfo();
                        set.setSingleId(data.getDishId());
                        set.setSetAmount(1);
                        countAmountFoods.add(set);
                    }
                } else {
                    SetInfo set = new SetInfo();
                    set.setSingleId(data.getDishId());
                    set.setSetAmount(1);
                    countAmountFoods.add(set);
                }
            }
            for (SetInfo data: countAmountFoods) {
                SetInfo setInfo = mapperExtra.getFood(data.getSingleId());
                data.setDishName(setInfo.getDishName());
            }
            foodOrders.get(0).setSets(countAmountFoods);
        }
        return new JSONResult(allMoney.toString() + "," + user.getUserAccount(), 200, new PageInfo(foodOrders));
    }

    /**
     * 完成订单
     * @param orderId
     * @return
     */
    @Override
    public JSONResult sureOrder(String orderId) {
        if(mapperExtra.sureOrder(orderId) > 0) {
            return new JSONResult("完成订单成功", 200);
        } else {
            return new JSONResult("完成订单失败", 500);
        }
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @Override
    public JSONResult cancelOrder(String orderId) {
        if(mapperExtra.cancelOrder(orderId) > 0) {
            return new JSONResult("取消订单成功", 200);
        } else {
            return new JSONResult("取消订单失败", 500);
        }
    }
}
