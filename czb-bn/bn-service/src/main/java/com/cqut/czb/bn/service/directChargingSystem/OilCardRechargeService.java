package com.cqut.czb.bn.service.directChargingSystem;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.*;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public interface OilCardRechargeService{
    List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type);

    List<DirectChargingOrderDto> getOnceOrderInfoList(String userId, Integer type);

    JSONResult getAllOrderInfoList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getAllOnceOrderInfoList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getTheStatics(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getTotalConsumption(int type);

    JSONResult bindingOilCard(String userId, OilCardBinging oilCardBinging);

    JSONResult getAllUserCard(DirectChargingOrderDto directChargingOrderDto);

    JSONResult updateOrderState(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getOilOrderState(DirectChargingOrderDto directChargingOrderDto);

    //    插入订单
    boolean insertOilCardOrder(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getPhoneOrderState(DirectChargingOrderDto directChargingOrderDto);

    JSONResult isPhoneRecharge(DirectChargingOrderDto directChargingOrderDto);

    void phoneRechargeSubmission(DirectChargingOrderDto directChargingOrderDto);

    void onlineorderSubmission(DirectChargingOrderDto directChargingOrderDto);

    JSONResult isNeedLogin();

    /**
     * 用支付宝充值油卡
     * 生成起调参数串——返回给app（支付订单）
     */
    String AlipayRechargeDirect(DirectChargingOrderDto directChargingOrderDto);

    /**
     * 微信支付
     * 充值
     */
    JSONObject WeChatRechargeDirect(DirectChargingOrderDto directChargingOrderDto);

    String aliPayReturn(HttpServletRequest request,String consumptionType);

    String aliElectricityReturn(HttpServletRequest request, String consumptionType);

    String wechatPayReturn(HttpServletRequest request,String consumptionType);

    String getAccount(String userId);

    String chenxieOilRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    String oilCardRechargeCallBack(CallBackInfo backInfo);

    /**
     * 生成excel表
     */
    Workbook exportOilCardRecord(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    JSONResult automaticSubmitPhone(AutoDirectDto autoDirectDto);

    JSONResult automaticSubmitOilCard(DictInputDTO dictInputDTO);

    List<DirectChargingOrderDto> getPartOrderInfoList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getSelectPhoneOrderState(SelectOrderDto selectOrderDto);

    JSONResult getSelectOilOrderState(SelectOrderDto selectOrderDto);

    int importData(MultipartFile file, Integer recordType) throws Exception;

    JSONResult submitSelectState(SelectOrderDto selectOrderDto);

    boolean test();
}
