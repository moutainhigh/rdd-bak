package com.cqut.czb.bn.service.directChargingSystem;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    String yfPhoneRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    String yfCallBack(YFCallBack backInfo);

    String hxPhoneRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    String hxCallBack(HXCallBack backInfo);

    String jhOilRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    String jhCallBack(JHCallBack backInfo);

    void dealOrderExtra(boolean success, DirectChargingOrderDto directChargingOrderDto);

    String fastCallBack(FastBackInfo backInfo);

    //    插入订单
    boolean insertOilCardOrder(DirectChargingOrderDto directChargingOrderDto);

    boolean insertPhoneOrder(DirectChargingOrderDto directChargingOrderDto);

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

    String fastPhoneOrderSubmit(DirectChargingOrderDto directChargingOrderDto);

    String fastOilOrderSubmit(DirectChargingOrderDto directChargingOrderDto);

    String chenxieOilRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    String chenxiePhoneRechargeSubmit(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    String oilCardRechargeCallBack(CallBackInfo backInfo);

    /**
     * 生成excel表
     */
    Workbook exportOilCardRecord(DirectChargingOrderDto directChargingOrderDto) throws Exception;

    JSONResult automaticSubmitPhone(AutoDirectDto autoDirectDto);

    JSONResult automaticSubmitOilCard(DictInputDTO dictInputDTO);

    JSONResult automaticSubmitPhone(DictInputDTO phone);

    JSONResult automaticSubmitPhoneFast(DictInputDTO phone);

    JSONResult automaticSubmitOilCardFast(DictInputDTO oil);

    JSONResult automaticSubmitPhoneYF(DictInputDTO phone);

    JSONResult automaticSubmitPhoneHX(DictInputDTO phone);

    JSONResult automaticSubmitPhoneJH(DictInputDTO phone);

    List<DirectChargingOrderDto> getPartOrderInfoList(DirectChargingOrderDto directChargingOrderDto);

    JSONResult getSelectPhoneOrderState(SelectOrderDto selectOrderDto);

    JSONResult getSelectOilOrderState(SelectOrderDto selectOrderDto);

    int importData(MultipartFile file, Integer recordType) throws Exception;

    int importUpdate(MultipartFile file, Integer recordType) throws Exception;

    JSONResult submitSelectState(SelectOrderDto selectOrderDto);

    boolean test();
}
