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

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        //根据redis信息，设置此次查询人的商店id
        foodOrder.setShopId(dishSystemMapperExtra.getShopId(user.getUserId()));


        //获取总的订单，然后将收益相加
        List<FoodOrder> orderList = mapperExtra.search(foodOrder);
        Double allMoney = new Double("0"); // 总收益
        Integer orderAllAmount = orderList.size(); //总单数
        for (FoodOrder order: orderList) {
            // 满足if条件的为实收金额的订单，将钱加到总收益里
            if((order.getDiningStatus() == 0 || order.getDiningStatus() == 2) && order.getPayStatus() == 1)
                allMoney += order.getActualPrice();
        }

        // 只处理分页数据的信息
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<FoodOrder> foodOrders = mapperExtra.search(foodOrder);
        JSONResult jsonResult = getOrderDishes(foodOrders, user);

        //获取今日的订单，然后将收益相加
        SimpleDateFormat day = new SimpleDateFormat("y-MM-dd");//设置日期格式为天,大写的H为24小时制，小写为12
        Date today = new Date();
        String todayStr = day.format(today);

        foodOrder.setStartTime(todayStr + " 00:00:00");
        foodOrder.setEndTime(todayStr + " 23:59:59");
        System.out.println("111111111111111111111111111111111111111111");
        System.out.println(todayStr);
        System.out.println(foodOrder.getStartTime());
        System.out.println(foodOrder.getEndTime());

        List<FoodOrder> orderTodayList = mapperExtra.search(foodOrder);
        // 今日总收益
        Double todayMoney = new Double("0");
        Integer orderTodayAmount = orderTodayList.size();
        for (FoodOrder order: orderTodayList) {
            // 满足if条件的为实收金额的订单，将钱加到总收益里
            if((order.getDiningStatus() == 0 || order.getDiningStatus() == 2) && order.getPayStatus() == 1)
                todayMoney += order.getActualPrice();
        }

        // 设置总收益和用户账号
        String pageInfo = "总销售额：" + allMoney.toString() + "元——总订单数："  + orderAllAmount.toString() + "单";
        pageInfo = pageInfo  + ";";
        pageInfo = pageInfo +  "今销售额：" + todayMoney + "元——今订单数："  +  orderTodayAmount.toString() + "单";
        jsonResult.setMessage(pageInfo);

        return jsonResult;
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

    private JSONResult getOrderDishes(Page<FoodOrder> foodOrders, User user) {

        //遍历每个订单，设置其菜品信息
        for (FoodOrder order: foodOrders) {

            //获得每个订单相应的菜品信息id
            List<Food> foods = foods = mapperExtra.getOrderDishes(order.getOrderId());

            // 若订单菜品不为0条，则将相同菜品信息合为一条：如，鲍鱼-2份,可乐-1份
            if(foods.size() != 0){
                List<SetInfo> countAmountFoods = new ArrayList<>();
                //计算每个菜品的份数
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
                //菜品信息
                StringBuilder orderDishes = new StringBuilder();
                //拼接菜品信息
                for (SetInfo data: countAmountFoods) {
                    SetInfo setInfo = mapperExtra.getFood(data.getSingleId());
                    orderDishes.append(setInfo.getDishName()).append("-").append(data.getSetAmount()).append("份，");
                }
                //去掉最后的,号
                orderDishes.deleteCharAt(orderDishes.lastIndexOf("，"));
                //设置订单菜品信息
                order.setOrderDishes(orderDishes.toString());
            }
        }

        return new JSONResult(200, new PageInfo(foodOrders));
    }

}
