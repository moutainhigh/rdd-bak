package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.PaymentProcess.BusinessProcessService;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.service.PaymentProcess.DataProcessService;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import com.cqut.czb.bn.service.PaymentProcess.FanYongService;
import com.cqut.czb.bn.service.PaymentProcess.PetrolRecharge;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BusinessProcessServiceImpl implements BusinessProcessService {

    @Autowired
    private DataProcessService dataProcessService;

    @Autowired
    private PetrolMapperExtra petrolMapperExtra;

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    FanYongService fanYongService;

    @Autowired
    PetrolRecharge petrolRecharge;

    @Autowired
    PetrolDeliveryRecordsMapper petrolDeliveryRecordsMapper;

    @Autowired
    PetrolDeliveryRecordsMapperExtra petrolDeliveryRecordsMapperExtra;

    @Autowired
    ConsumptionRecordMapperExtra consumptionRecordMapperExtra;

    @Autowired
    InfoSpreadService infoSpreadService;

    @Autowired
    OrderMapperExtra orderMapperExtra;

    @Autowired
    UserMapperExtra userMapperExtra;

    @Autowired
    VipRechargeRecordsMapperExtra vipRechargeRecordsMapperExtra;

    @Override
    public synchronized Map AliPayback(Object[] param, String consumptionType) {
        // 支付宝支付
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        if(consumptionType.equals("Petrol")){//购油
            if (getPetrolOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if(consumptionType.equals("Service")){//购买服务
            if (getAddServiceOrderData(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if(consumptionType.equals("vip")){//油卡充值
            if (getAddVipOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else {
            result.put("fail", AlipayConfig.response_fail);
            return result;
        }
        result.put("fail", AlipayConfig.response_fail);
        return result;
    }

    @Override
    public synchronized Map WeChatPayBack(Object[] param, String consumptionType) {
        Map<String, Object> restmap = (HashMap<String, Object>) param[0];
        Map<String, Integer> result = new HashMap<>();
        if(consumptionType.equals("Petrol")){
            result.put("success", addPetrolOrderWeChat(restmap));
        }else if(consumptionType.equals("Service")){
            result.put("success", addBuyServiceOrderWeChat(restmap));
        }else if(consumptionType.equals("vip")){
            result.put("success", addVipOrderWeChat(restmap));
        }
        return result;
    }

    @Override
    public synchronized void purchaseFailed(Object[] param) {
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = dataProcessService.getOrderdata(params);
        System.out.println("购买失败删除前" + PetrolCache.AllpetrolMap + ":" + PetrolCache.currentPetrolMap);
        if (petrolSalesRecordsDTO == null)
            return;
        Petrol petrol = PetrolCache.currentPetrolMap.get(petrolSalesRecordsDTO.getPetrolNum());
        petrol.setOwnerId("");
        petrol.setEndTime(0);
        PetrolCache.currentPetrolMap.remove(petrolSalesRecordsDTO.getPetrolNum());//移除
        PetrolCache.AllpetrolMap.put(petrol.getPetrolNum(), petrol);//放入
        System.out.println("购买失败删除后" + PetrolCache.AllpetrolMap + ":" + PetrolCache.currentPetrolMap);
    }

    /**
     * 购买服务（支付宝）
     */
    public int getAddServiceOrderData(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        String orgId = "";//商家订单
        double money = 0;
        String ownerId = "";
        String commodityId = "";
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println("商家订单orgId:" + orgId);
            }
            if ("money".equals(temp[0])) {
                money = Double.valueOf(temp[1]);
                System.out.println("充值金额:money" + money);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
            if ("commodityId".equals(temp[0])) {
                commodityId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
        }
        //更改订单状态
        Order order=new Order();
        order.setThirdOrder(thirdOrderId);
        order.setState(1);
        boolean updateOrder=orderMapperExtra.updateByPrimaryKeySelective(order)>0;
        System.out.println("更改订单成功"+updateOrder);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }

    /**
     * 购买服务（微信）
     */
    public int addBuyServiceOrderWeChat(Map<String, Object> restmap) {
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();
        String[] temp;
        String orgId = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        String ownerId = "";
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println(orgId);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
        }
        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }

    /**
     * 充值vip（支付宝）
     */
    public synchronized int getAddVipOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        String orgId = "";//商家订单
        double money = 0;
        String ownerId = "";
        String vipAreaConfigId="";//地区vipId
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println("商家订单orgId:" + orgId);
            }
            if ("money".equals(temp[0])) {
                money = Double.valueOf(temp[1]);
                System.out.println("充值金额:money" + money);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
            if ("vipAreaConfigId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + vipAreaConfigId);
            }
        }
        //更改用户状态
        boolean changeToVip= userMapperExtra.UpdateToVip(ownerId)>0;
        System.out.println("更改用户为vip"+changeToVip);

        //插入VIP充值记录表
        dataProcessService.insertRechargeOrder(money,2,orgId,thirdOrderId,ownerId,vipAreaConfigId);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "3", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }

    /**
     * 充值vip（微信）
     */
    public int addVipOrderWeChat(Map<String, Object> restmap) {
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();
        String[] temp;
        String orgId = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        String ownerId = "";
        String vipAreaConfigId="";//地区vipId
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println(orgId);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
            if ("vipAreaConfigId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + vipAreaConfigId);
            }
        }

        //更改用户状态
        boolean kk= userMapperExtra.UpdateToVip(ownerId)>0;
        System.out.println("更改用户为vip"+kk);

        //插入VIP充值记录表
        dataProcessService.insertRechargeOrder(money,2,orgId,thirdOrderId,ownerId,vipAreaConfigId);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "3", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }

    /**
     * (支付宝)购油
     */
    public synchronized int getPetrolOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        System.out.println("第三方订单" + params.get("trade_no"));
        String orgId = "";//商家订单
        // payType"0"为购油"1"代表的是优惠卷购买（vip未有）"2"代表的是充值
        String payType = "";
        double money = 0;
        int petrolKind = 0;
        String petrolNum = "";
        String ownerId = "";
        String addressId = "";
        double actualPayment = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("trade_no".equals(temp[0])) {
                System.out.println("该交易在支付宝系统中的交易流水号:" + temp[1]);
            }
            if ("out_trade_no".equals(temp[0])) {
                System.out.println("商户网站唯一订单号:" + temp[1]);
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println("商家订单orgId:" + orgId);
            }
            if ("payType".equals(temp[0])) {
                System.out.println(temp[0] + ":" + temp[1]);
                payType = temp[1];
                System.out.println("payType:" + payType);
            }
            if ("money".equals(temp[0])) {
                money = Double.valueOf(temp[1]);
                System.out.println("充值金额:money" + money);
            }
            if ("petrolKind".equals(temp[0])) {
                petrolKind = Integer.parseInt(temp[1]);
                System.out.println("油卡类型petrolKind:" + petrolKind);
            }
            if ("petrolNum".equals(temp[0])) {
                petrolNum = temp[1];
                System.out.println("油卡号petrolNum:" + petrolNum);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
            if ("actualPayment".equals(temp[0])) {
                actualPayment = Double.valueOf(temp[1]);
                System.out.println("实际支付actualPayment:" + actualPayment);
            }
            if ("addressId".equals(temp[0])) {
                addressId = temp[1];
                System.out.println("用户addressId:" + addressId);
            }
        }
        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应"0"为购油"1"代表的是优惠卷购买（vip未有）"2"代表的是充值
        if ("2".equals(payType)) {
            System.out.println("开始充值");
            PetrolSalesRecords petrolSalesRecords = new PetrolSalesRecords();
            petrolSalesRecords = petrolSalesRecordsMapperExtra.selectInfoByOrgId(orgId);
            PetrolInputDTO petrolInputDTO = new PetrolInputDTO();
            petrolInputDTO.setOwnerId(ownerId);
            petrolInputDTO.setPetrolKind(petrolSalesRecords.getPetrolKind());
            Petrol petrol1 = petrolMapperExtra.selectDifferentPetrol(petrolInputDTO);
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 1);

            boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);
            if (beginPetrolRecharge == true)
                return 1;
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);

            boolean isChange = dataProcessService.changeInfo(thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);

            return dataProcessService.putBackPetrol(isChange,petrolNum);
        }
        return 1;
    }


    /**
     * (微信)购油
     */
    public synchronized int addPetrolOrderWeChat(Map<String, Object> restmap) {
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        System.out.println(out_trade_no);
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();
        System.out.println(thirdOrderId);
        String[] temp;
        String orgId = "";
        String payType = "";
        String petrolNum = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        System.out.println("money" + money);
        String ownerId = "";
        double actualPayment = 0.00;//后面有变化
        String addressId = "";
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println(orgId);
            }
            if ("petrolNum".equals(temp[0])) {
                petrolNum = temp[1];
                System.out.println("油卡号petrolNum:" + petrolNum);
            }
            if ("payType".equals(temp[0])) {
                System.out.println(temp[0] + ":" + temp[1]);
                payType = temp[1];
                System.out.println(payType);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
            if ("addressId".equals(temp[0])) {
                addressId = temp[1];
                System.out.println("用户addressId:" + addressId);
            }
        }
        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应"0"为购油"1"代表的是优惠卷购买（vip未有）"2"代表的是充值
        if ("2".equals(payType)) {
            PetrolSalesRecords petrolSalesRecords = new PetrolSalesRecords();
            petrolSalesRecords = petrolSalesRecordsMapperExtra.selectInfoByOrgId(orgId);
            PetrolInputDTO petrolInputDTO = new PetrolInputDTO();
            petrolInputDTO.setOwnerId(ownerId);
            petrolInputDTO.setPetrolKind(petrolSalesRecords.getPetrolKind());
            Petrol petrol1 = petrolMapperExtra.selectDifferentPetrol(petrolInputDTO);
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);

            boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);
            if (beginPetrolRecharge == true)
                return 1;
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买0");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);
            //此处插入购油的相关信息，油卡购买记录
            boolean isChange = dataProcessService.changeInfo(thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);
            //判断是否放回油卡
            return dataProcessService.putBackPetrol(isChange,petrolNum);
        }
        return 1;
    }


}
