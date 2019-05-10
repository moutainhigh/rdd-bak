package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.*;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/23
 * 作用：购买油卡
 */
public interface AppBuyPetrolService {

    /**
     * 购买油卡
     * 生成起调参数串——返回给app（支付订单）
     */
    String BuyPetrol(Petrol petrol,PetrolInputDTO petrolInputDTO);


}
