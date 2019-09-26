package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.food.DishOrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.OrderDishesMapperExtra;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DetailsOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.OrderDishDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.food.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Service
public class MyOrderServiceImpl implements MyOrderService {
    @Autowired
    DishOrderMapperExtra dishOrderMapperExtra;
    @Autowired
    OrderDishesMapperExtra orderDishesMapperExtra;

    @Override
    public List<DishOrderDTO> getAllMyOrderList(User user) {
        return dishOrderMapperExtra.selectOrderByUser(user.getUserId());
    }

    @Override
    public DetailsOrderDTO getOrderDetails(DishOrderDTO dishOrderDTO) {
        DetailsOrderDTO detailsOrder = dishOrderMapperExtra.selectOrderDetails(dishOrderDTO);
        detailsOrder.setDishes(orderDishesMapperExtra.selectDishInOrder(detailsOrder.getOrderId()));
        for (OrderDishDTO orderDishDTO : detailsOrder.getDishes()){
            orderDishDTO.setCurrentPrice(mul(orderDishDTO.getOriginalPrice(),orderDishDTO.getDishCount()));
            orderDishDTO.setOriginalPrice(mul(orderDishDTO.getCurrentPrice(),orderDishDTO.getDishCount()));
            orderDishDTO.setVipPrice(mul(orderDishDTO.getVipPrice(),orderDishDTO.getDishCount()));
        }
        return detailsOrder;
    }

    @Override
    public Boolean cancelDishOrder(DishOrderDTO dishOrderDTO) {
        if (!"".equals(dishOrderDTO.getOrderId()) && dishOrderDTO.getOrderId()!=null) {
            dishOrderDTO.setUpdateAt(new Date());
            dishOrderDTO.setDiningStatus(-1);
        }
        return dishOrderMapperExtra.updateDishOrderById(dishOrderDTO)>0;
    }

    //double与Integer进行乘法，如直接相乘容易精度缺失
    public Double mul (Double num1,Integer num2){
        BigDecimal mul1 = new BigDecimal(Double.toString(num1));
        BigDecimal mul2 = new BigDecimal(Integer.toString(num2));
        double mul = mul1.multiply(mul2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return mul;
    }
}
