package com.cqut.czb.bn.service.impl.directChargingSystem;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.AliParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.AliPayConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.AlipayClientConfig;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.VipAreaConfig;
import com.cqut.czb.bn.entity.entity.directChargingSystem.UserCardRelation;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OilCardRechargeServiceImpl implements OilCardRechargeService {
    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;

    @Override
    public List<DirectChargingOrderDto> getOrderInfoList(String userId, Integer type) {
        return oilCardRechargeMapperExtra.getOrderInfoList(userId, type);
    }

    @Override
    public JSONResult getAllOrderInfoList(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectChargingOrderDto> withdrawList = oilCardRechargeMapperExtra.getAllOrderInfoList(directChargingOrderDto);
        PageInfo<DirectChargingOrderDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult getAllUserCard(DirectChargingOrderDto directChargingOrderDto) {
        PageHelper.startPage(directChargingOrderDto.getCurrentPage(), directChargingOrderDto.getPageSize(),true);
        List<DirectChargingOrderDto> withdrawList = oilCardRechargeMapperExtra.getAllUserCard(directChargingOrderDto);
        PageInfo<DirectChargingOrderDto> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult bindingOilCard(String userId, OilCardBinging oilCardBinging) {
        if(!oilCardBinging.getPertrolNum().equals(oilCardBinging.getIsPertrolNum()))
            return new JSONResult(500,"输入卡号不一致");

        int oilCardNum = oilCardRechargeMapperExtra.isExistOilCard(oilCardBinging);
        if (oilCardNum > 0)
            return new JSONResult(500,"该卡号已被绑定过,请重新确认卡号");

        // 检查该用户有没有绑过卡号
        int isExist = oilCardRechargeMapperExtra.isExistOilCardUser(userId);
        boolean isSuccess = false;
        if (isExist > 0)
            isSuccess = oilCardRechargeMapperExtra.upDatePetrolNum(userId,oilCardBinging) > 0;
        else {
            UserCardRelation userCardRelation = new UserCardRelation();
            userCardRelation.setRecordId(StringUtil.createId());
            userCardRelation.setUserId(userId);
            if (oilCardBinging.getType() == 0)
                userCardRelation.setPetrolchinaPetrolNum(oilCardBinging.getPertrolNum());
            else if(oilCardBinging.getType() == 1)
                userCardRelation.setSinopecPetrolNum(oilCardBinging.getPertrolNum());
            isSuccess = oilCardRechargeMapperExtra.insertPetrolNum(userCardRelation) > 0;
        }
        return isSuccess ? new JSONResult(200,"绑定成功"):new JSONResult(500,"绑定失败");
    }


//    直充支付
    @Override
    public String AlipayRechargeDirect(DirectChargingOrderDto directChargingOrderDto) {
        //检验是否都为空
        if (directChargingOrderDto == null)
            return "传入数据有误（为空）";
        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("7");
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //订单标识
        String orderId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);

        //支付的金额
//        Double money=backMoney( petrol,petrolInputDTO);
        Double rechargeAmount = directChargingOrderDto.getRechargeAmount();
        // userId
        String userId = directChargingOrderDto.getUserId();
        //直充类型
        Integer  recordType = directChargingOrderDto.getRecordType();

        request.setBizModel(AliParameterConfig.getPhonePill(orderId, rechargeAmount, userId, recordType));//支付订单
        request.setNotifyUrl(AliPayConfig.Direct_url);//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //插入购买信息
        boolean insertPetrolSalesRecords= insertPhonePillRecords(directChargingOrderDto,orderId);
        System.out.println("新增直充充值记录完毕"+insertPetrolSalesRecords);
        return orderString;
    }

//    插入订单
    public boolean insertPhonePillRecords(DirectChargingOrderDto directChargingOrderDto, String orderId) {
        DirectChargingOrderDto directChargingOrder = new DirectChargingOrderDto();
        directChargingOrder.setUserId(directChargingOrderDto.getUserId());
        directChargingOrder.setOrderId(orderId);
        directChargingOrder.setRechargeAmount(directChargingOrderDto.getRechargeAmount());
        directChargingOrder.setRecordType(directChargingOrderDto.getRecordType());
        directChargingOrder.setPaymentMethod(1);
        directChargingOrder.setState(0);
        boolean insertRecords=oilCardRechargeMapperExtra.insertOrder(directChargingOrder)>0;
        return insertRecords;
    }
}
