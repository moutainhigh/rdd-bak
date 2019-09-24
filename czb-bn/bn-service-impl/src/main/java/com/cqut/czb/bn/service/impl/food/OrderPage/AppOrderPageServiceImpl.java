package com.cqut.czb.bn.service.impl.food.OrderPage;

import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishMapperExtra;
import com.cqut.czb.bn.entity.dto.food.AppOrderPage.*;
import com.cqut.czb.bn.service.food.AppOrderPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
           orderPageDTO.setDistance(length+"");
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

}
