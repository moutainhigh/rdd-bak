package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.food.DishOrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.OrderDishesMapperExtra;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DetailsOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishOrderDTO;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.OrderDishDTO;
import com.cqut.czb.bn.entity.entity.Shop;
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
            orderDishDTO.setCurrentPrice(mul(orderDishDTO.getOriginalPrice(),orderDishDTO.getDishCount().doubleValue()));
            orderDishDTO.setOriginalPrice(mul(orderDishDTO.getCurrentPrice(),orderDishDTO.getDishCount().doubleValue()));
            orderDishDTO.setVipPrice(mul(orderDishDTO.getVipPrice(),orderDishDTO.getDishCount().doubleValue()));
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

    @Override
    public Boolean confirmOrder(User user, String orderTime ,String orderId) {
        Shop shop = null;
        if (user!=null && user.getUserId()!=null) {  //查询是否有此商家，必须是商家才能操作
            shop = dishOrderMapperExtra.selectShopByUser(user.getUserId());
        }
        if (shop!=null && shop.getShopId()!=null) {
                Boolean isBeyond = new Date().getTime()-Long.parseLong(orderTime)>5*60*1000L;
                if (isBeyond) {
                    return false;
                }
            DishOrderDTO dishOrderDTO = new DishOrderDTO();
            dishOrderDTO.setOrderId(orderId);
            dishOrderDTO.setDiningStatus(2);
            dishOrderDTO.setUpdateAt(new Date());
            dishOrderDTO.setShopId(shop.getShopId());
            boolean isConfirm = dishOrderMapperExtra.updateDishOrderByShop(dishOrderDTO)>0;
            return isConfirm;
        }
        return false;
    }

    //Double与Double进行乘法，如直接相乘容易精度缺失
    public Double mul (Double num1,Double num2){
        BigDecimal mul1 = new BigDecimal(Double.toString(num1));
        BigDecimal mul2 = new BigDecimal(Double.toString(num2));
        double mul = mul1.multiply(mul2).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        return mul;
    }
}
