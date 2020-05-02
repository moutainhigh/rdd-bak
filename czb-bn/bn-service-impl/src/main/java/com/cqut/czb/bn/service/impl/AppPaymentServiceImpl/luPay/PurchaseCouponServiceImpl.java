package com.cqut.czb.bn.service.impl.AppPaymentServiceImpl.luPay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.PetrolMapperExtra;
import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.PayConfig.AliParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.AliPayConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.AlipayClientConfig;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.AliPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInfo;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PurchaseCouponServiceImpl {

    @Autowired
    PetrolMapperExtra petrolMapperExtra;

    @Autowired
    PetrolCouponsSalesRecordsMapperExtra petrolCouponsSalesRecordsMapperExtra;

    public Map<String,Object> PurchaseControl(PetrolInputDTO petrolInputDTO) {
        //查出对应的油卡优惠券
        Petrol petrol=petrolMapperExtra.selectPetrolCoupons(petrolInputDTO);
//        判断是否有油卡优惠券
        if(petrol==null){
            Map<String,Object> info=new HashMap<>();
            info.put("-1","油卡申请失败，信息有误，无此法生成订单");
            return info;
        }
        String AliOrder=AliPayBuyPetrolCoupons(petrol,petrolInputDTO);
        if(AliOrder!=null){ //请求成功
            Map<String,Object> info=new HashMap<>();
            AliPetrolBackInfoDTO petrolBackInfoDTO=new AliPetrolBackInfoDTO();
            petrolBackInfoDTO.setPaymentOrder(AliOrder);
            Petrol petrol2= PetrolInfo.getNewPetrol(petrol);
            petrol.setPetrolPrice(petrolInputDTO.getPetrolPrice());
            petrolBackInfoDTO.setPetrol(petrol2);
            info.put("0",petrolBackInfoDTO);
            return info;
        }else {             //没有请求成功
            Map<String,Object> info=new HashMap<>();
            info.put("-1","油卡申请失败，信息有误，无此法生成订单");
            return info;
        }
    }

    //支付宝支付
    public String AliPayBuyPetrolCoupons(Petrol petrol, PetrolInputDTO petrolInputDTO) {

        //生成起调参数串——返回给app（支付宝的支付订单）
        String orderString = null;//用于保存起调参数,

        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance("6");//“6”代表中石化优惠券

        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();

        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //订单标识
        String orgId =System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        request.setBizModel(AliParameterConfig.getBizModelPetrolCoupons(orgId,petrolInputDTO,petrol));//支付订单
        request.setNotifyUrl(AliPayConfig.Coupons_url);//支付回调接口

        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //插入购买信息
        insertPetrolSaleRecords(petrol,petrolInputDTO,orgId);
        return orderString;
    }

    //插入消费记录
    public void insertPetrolSaleRecords(Petrol petrol, PetrolInputDTO petrolInputDTO, String orgId){
        PetrolCouponsSalesRecords records=new PetrolCouponsSalesRecords();
        records.setRecordId(orgId);
        records.setPetrolId(petrol.getPetrolId());
        records.setBuyerId(petrolInputDTO.getOwnerId());
        records.setUserAccount(petrolInputDTO.getUserAccount());
        records.setPaymentMethod(petrolInputDTO.getPaymentMethod());
        records.setToRddOutTradeNo(orgId);
        records.setToRddState(0);
        records.setCreateAt(new Date());
        records.setUpdateAt(new Date());
        int is= petrolCouponsSalesRecordsMapperExtra.insertSelective(records);
        System.out.println("插入支付宝起吊信息"+(is>0));
    }

}
