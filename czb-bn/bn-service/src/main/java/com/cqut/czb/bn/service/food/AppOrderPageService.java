package com.cqut.czb.bn.service.food;

import com.cqut.czb.bn.entity.dto.food.OrderPage.OrderPageDTO;
import org.apache.ibatis.annotations.Param;

/**
 * app 点餐页面
 */
public interface AppOrderPageService {

    OrderPageDTO selectOrderShopInfo( String shopId);


}
