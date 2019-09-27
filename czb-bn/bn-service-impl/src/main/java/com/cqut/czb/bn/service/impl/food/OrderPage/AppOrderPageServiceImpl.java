package com.cqut.czb.bn.service.impl.food.OrderPage;

import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.*;
import com.cqut.czb.bn.entity.dto.food.OrderFoodDTO.DishDTO;
import com.cqut.czb.bn.service.food.AppOrderPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AppOrderPageServiceImpl implements AppOrderPageService {

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Autowired
    DishMapperExtra dishMapperExtra;

    @Override
    public OrderPageShopDTO selectOrderShopInfo(InputOrderPageDTO inputOrderPageDTO) {
        if(inputOrderPageDTO==null){
            return null;
        }
        double longitude;
        double latitude;
        double length=0.0;
        String shopId=inputOrderPageDTO.getShopId();
        OrderPageShopDTO orderPageDTO=new OrderPageShopDTO();
        orderPageDTO=shopMapperExtra.selectOrderShopInfo(shopId);
        orderPageDTO.setTopImg(shopMapperExtra.selectImg(shopId));
        //测试距离
        if(inputOrderPageDTO.getLongitude()!=null&&inputOrderPageDTO.getLatitude()!=null)
        {
           length= DistanceMeter.InputDistance(inputOrderPageDTO.getLatitude(),inputOrderPageDTO.getLongitude()
            ,orderPageDTO.getLatitude(),orderPageDTO.getLongitude());
           if(length<1000) {
               length=BigDecimal.valueOf(length).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
               orderPageDTO.setDistance(length+"m");
           }else {
               length = BigDecimal.valueOf(length).divide(BigDecimal.valueOf(1000)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
               if(length>50){
                   orderPageDTO.setDistance(50+"+km");
               }else {
                   orderPageDTO.setDistance(length+"km");
               }
           }
        }else {
            orderPageDTO.setDistance("定位失败");
        }

        return orderPageDTO;
    }

    @Override
    public List<OrderToRecommendDTO> selectRecommend(String shopId) {
        //获取今天星期几
        String weekDay= DamaiDay.getDamaiDay(new Date());
        return dishMapperExtra.selectRecommend(shopId,weekDay);
    }

    @Override
    public List<AllDishDTO> selectAllDish(String shopId) {
        String weekDay= DamaiDay.getDamaiDay(new Date());
        return dishMapperExtra.selectAllDish(shopId,weekDay);
    }

    @Override
    public List<OrderToRecommendDTO> selectDishInfo(String dishInfo) {
        List<OrderNum> list= ParseJSON.parseJSONWithJSONObject(dishInfo);
        if(list==null){
            return null;
        }
        //取出所有商品的信息
        List<OrderToRecommendDTO> dishDTOS=dishMapperExtra.selectDishs(list);
        return dishDTOS;
    }

}
