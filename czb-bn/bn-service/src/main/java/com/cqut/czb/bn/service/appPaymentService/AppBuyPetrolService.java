package com.cqut.czb.bn.service.appPaymentService;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.*;

import java.util.Map;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/23
 * 作用：购买油卡
 */
public interface AppBuyPetrolService {

    /**
     * 油卡购买，油卡优惠卷购买，分流处理
     */
    Map<String, Object> ShuntProcessing(PetrolInputDTO petrolInputDTO);

    /**
     * 用支付宝购买油卡
     * 生成起调参数串——返回给app（支付订单）
     */
    String AlipayBuyPetrol(Petrol petrol,PetrolInputDTO petrolInputDTO);

    /**
     * 用微信购买油卡
     * 生成起调参数串——返回给app（支付订单）
     */
    JSONObject WechatBuyPetrol(Petrol petrol, PetrolInputDTO petrolInputDTO);

    double backMoney(Petrol petrol, PetrolInputDTO petrolInputDTO);

    /**
     * 判断今日是否购买了油卡
     * @param petrolInputDTO
     * @return
     */
    boolean isTodayHadBuy(PetrolInputDTO petrolInputDTO);

    /**
     * 判断是否购买了相应类型的油卡
     * @param petrolInputDTO
     * @return
     */
    boolean isBuyRepeat(PetrolInputDTO petrolInputDTO);


    /**
     * 油卡购买和充值分流处理
     * @param petrolInputDTO
     * @return
     */
    Map<String, Object> PurchaseControl(PetrolInputDTO petrolInputDTO);

    /**
     * 新增油卡购买或充值记录
     */
    boolean insertPetrolSalesRecords(Petrol petrol,PetrolInputDTO petrolInputDTO,String orgId);

}
