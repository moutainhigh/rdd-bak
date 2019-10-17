package com.cqut.czb.bn.service.impl.payBack;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.dao.mapper.food.DishOrderMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppVehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.entity.food.DishOrder;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.PaymentProcess.BusinessProcessService;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.service.PaymentProcess.DataProcessService;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import com.cqut.czb.bn.service.PaymentProcess.FanYongService;
import com.cqut.czb.bn.service.PaymentProcess.PetrolRecharge;
import com.cqut.czb.bn.service.impl.vehicleServiceImpl.ServerOrderServiceImpl;
import com.cqut.czb.bn.util.config.SendMesConfig.MesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    VehicleCleanOrderMapperExtra vehicleCleanOrderMapperExtra;

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;

    @Autowired
    DishOrderMapper dishOrderMapper;

    @Autowired
    ServerOrderServiceImpl serverOrderService;

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
        }else if(consumptionType.equals("vip")){//充值vip
            if (getAddVipOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if(consumptionType.equals("CarWash")) {//洗车
            if (getAddCarWashOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if(consumptionType.equals("Dish")) {//点餐
            if (getAddBuyDishOrderAli(params) == 1) {
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
        }else if(consumptionType.equals("CarWash")){
            result.put("success", addCarWashOrderWeChat(restmap));
        }else if(consumptionType.equals("Dish")){
            result.put("success", addBuyDishOrderWeChat(restmap));
        }else {
            result.put("fail",0);
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

    //点餐(支付宝)
    public int getAddBuyDishOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        //自己的订单号
        String orgId = "";
        double money = 0;
        String ownerId = "";
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
                System.out.println("支付金额:money" + money);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
        }

        //更改用户订单
        DishOrder dishOrder=new DishOrder();
        dishOrder.setOrderId(orgId);
        dishOrder.setThirdOrder(thirdOrderId);
        dishOrder.setUpdateAt(new Date());
        dishOrder.setPayStatus(1);
        int update= dishOrderMapper.updateByPrimaryKeySelective(dishOrder);
        System.out.println("更改用户订单："+(update>0));

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //businessType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务,5为点餐
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "5", 1);

        return 1;
    }

    /**
     * 洗车服务（微信）
     */
    public int addBuyDishOrderWeChat(Map<String, Object> restmap) {
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();
        String[] temp;
        String orgId = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        System.out.println("微信支付:"+money);
        String ownerId = "";
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {
                continue;
            }
            //商家订单
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
            }
            //用户id
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
            }
        }

        //更改用户订单
        DishOrder dishOrder=new DishOrder();
        dishOrder.setOrderId(orgId);
        dishOrder.setThirdOrder(thirdOrderId);
        dishOrder.setUpdateAt(new Date());
        dishOrder.setPayStatus(1);
        int update= dishOrderMapper.updateByPrimaryKeySelective(dishOrder);
        System.out.println("更改用户订单："+(update>0));

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //businessType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务，5为点餐
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "5", 2);

        return 1;
    }

    /**
     * 洗车服务（支付宝）
     */
    public int getAddCarWashOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        //自己的订单号
        String orgId = "";
        double money = 0;
        String ownerId = "";
        String serverId = "";
        String couponId="";
        String area="";
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
                System.out.println("支付金额:money" + money);
            }
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + ownerId);
            }
            if ("serverId".equals(temp[0])) {
                serverId = temp[1];
                System.out.println("产品id:" + serverId);
            }
            if ("couponId".equals(temp[0])) {
                couponId = temp[1];
                System.out.println("优惠劵id:" + couponId);
            }
        }
        //更改优惠卷
        if(!couponId.equals("")&&couponId!=null){
            boolean k1=serverCouponMapperExtra.updateCoupons(couponId)>0;
            System.out.println("更改优惠劵"+k1);
        }

        //更改用户订单
        AppVehicleCleanOrderDTO orderDTO=new AppVehicleCleanOrderDTO();
        orderDTO.setServerOrderId(orgId);
        orderDTO.setThirdOrder(thirdOrderId);
        orderDTO.setPayStatus((byte)1);
        orderDTO.setUpdateAt(new Date());
        orderDTO.setServerId(serverId);
        orderDTO.setActualPrice(money);
        if(!couponId.equals("")&&couponId!=null){
            orderDTO.setCouponId(couponId);
        }
        boolean ve=vehicleCleanOrderMapperExtra.updateMyBackOrder(orderDTO)>0;
        System.out.println("更改洗车订单完毕"+ve);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //businessType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "4", 1);

//        //进行返佣
//        boolean beginFanYong= fanYongService.beginWashCarFanYong(ownerId);
//        System.out.println("返佣"+beginFanYong);
        return 1;
    }

    /**
     * 洗车服务（微信）
     */
    public int addCarWashOrderWeChat(Map<String, Object> restmap) {
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();
        String[] temp;
        String orgId = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        System.out.println("weixin支付:"+money);
        String ownerId = "";
        String couponId="";
        String area="";//地区
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {
                continue;
            }
            //商家订单
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
            }
            //用户id
            if ("ownerId".equals(temp[0])) {
                ownerId = temp[1];
            }
            if ("couponId".equals(temp[0])) {
                couponId = temp[1];
                System.out.println("优惠劵"+couponId);
            }
        }
        //更改优惠卷
        if(!couponId.equals("")&&couponId!=null){
            boolean k1=serverCouponMapperExtra.updateCoupons(couponId)>0;
            System.out.println("更改优惠劵"+k1);
        }

        //更改用户订单
        AppVehicleCleanOrderDTO orderDTO=new AppVehicleCleanOrderDTO();
        orderDTO.setServerOrderId(orgId);
        orderDTO.setThirdOrder(thirdOrderId);
        orderDTO.setPayStatus((byte)1);
        orderDTO.setUpdateAt(new Date());
        orderDTO.setActualPrice(money);
        if(!couponId.equals("")&&couponId!=null){
            orderDTO.setCouponId(couponId);
        }
        boolean ve=vehicleCleanOrderMapperExtra.updateMyBackOrder(orderDTO)>0;
        System.out.println("更改洗车订单完毕"+ve);


        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //businessType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "4", 2);

//        //进行返佣
//        boolean beginFanYong= fanYongService.beginWashCarFanYong(ownerId);
//        System.out.println("返佣"+beginFanYong);
        return 1;
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
        String area="";//地区
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

        //businessType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(3,area,ownerId,money,money,orgId);
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
        String area="";//地区
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
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 2);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(3,area,ownerId,money,money,orgId);
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
        String area="";//地区
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
                vipAreaConfigId = temp[1];
                System.out.println("用户id:" + vipAreaConfigId);
            }
        }
        //更改用户状态
        boolean changeToVip= userMapperExtra.UpdateToVip(ownerId)>0;
        System.out.println("更改用户为vip"+changeToVip);

        //插入VIP充值记录表
        dataProcessService.insertRechargeOrder(money,1,orgId,thirdOrderId,ownerId,vipAreaConfigId);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "3", 1);

        //进行返佣 String BusinessType
        boolean beginFanYong= fanYongService.beginFanYong(2,area,ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        //发送购买成功推送给特定用户
        editContent(ownerId,null,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_VIP.getNoticeId());
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
        String area="";//地区
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
                vipAreaConfigId = temp[1];
                System.out.println("地区id:" + vipAreaConfigId);
            }
        }

        //更改用户状态
        boolean kk= userMapperExtra.UpdateToVip(ownerId)>0;
        System.out.println("更改用户为vip"+kk);

        //插入VIP充值记录表
        dataProcessService.insertRechargeOrder(money,2,orgId,thirdOrderId,ownerId,vipAreaConfigId);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 2);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(2,area,ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        //发送购买成功推送给特定用户
        editContent(ownerId,null,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_VIP.getNoticeId());
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
        String area="";//地区

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("area".equals(temp[0])) {//地区
                System.out.println(temp[0] + temp[1]);
                area=temp[1];
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
                payType = temp[1];
                System.out.println(temp[0] + ":" + temp[1]);
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

        //
        if ("2".equals(payType)) {
            System.out.println("开始充值");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "1", 1);
            //插入消费记录
            boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);
            if (beginPetrolRecharge == true){
                //发送购买成功推送给特定用户
                editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_PETROL.getNoticeId());
                return 1;
            }
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "0", 1);
            //发送购买成功推送给特定用户
            editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.BUY_PETROL.getNoticeId());

            boolean isChange = dataProcessService.changeInfo(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);

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
        String orgId = restmap.get("out_trade_no").toString();
        String payType = "";
        String petrolNum = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        System.out.println("money" + money);
        String ownerId = "";
        double actualPayment = 0.00;//后面有变化
        String addressId = "";
        String area="";//地区
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("area".equals(temp[0])) {//地区
                System.out.println(temp[0] + temp[1]);
                area=temp[1];
            }
            if ("petrolNum".equals(temp[0])) {
                petrolNum = temp[1];
                System.out.println("油卡号petrolNum:" + petrolNum);
            }
            if ("payType".equals(temp[0])) {
                payType = temp[1];
                System.out.println(temp[0] + ":" + temp[1]);
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

            boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);
            if (beginPetrolRecharge == true){
                //发送购买成功推送给特定用户
                editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_PETROL.getNoticeId());
                return 1;
            }
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买0");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);
            //此处插入购油的相关信息，油卡购买记录

            //发送购买成功推送给特定用户
            editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.BUY_PETROL.getNoticeId());

            boolean isChange = dataProcessService.changeInfo(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);
            //判断是否放回油卡
            return dataProcessService.putBackPetrol(isChange,petrolNum);
        }

        return 1;
    }

    public void editContent(String ownerId,String petrolNum,String userId,String noticeId) {
        //发送购买成功推送给特定用户
        Map<String,String> content = new HashMap<>();
        if (ownerId == null && petrolNum == null){
           return;
        }
        if (petrolNum != null) {
            Petrol petrol = petrolMapperExtra.selectPetrolByNum(petrolNum);
            if (petrol!=null && petrol.getPetrolKind()==1){
                content.put("petrolKind","中石油");
                content.put("petrolPrice",petrol.getPetrolPrice().toString());
            }else if (petrol!=null && petrol.getPetrolKind()==2) {
                content.put("petrolKind","中石化");
                content.put("petrolPrice",petrol.getPetrolPrice().toString());
            }else if (petrol!=null && petrol.getPetrolKind()==0) {
                content.put("petrolKind","国通");
                content.put("petrolPrice",petrol.getPetrolPrice().toString());
            }
        }
        if (ownerId != null) {
            UserDTO userDTO = userMapperExtra.findUserDTOById(ownerId);
            if (userDTO != null && userDTO.getUserAccount() != null) {
                content.put("userAccount", userDTO.getUserAccount());
            }
        }
        serverOrderService.sendMessage(userId,noticeId,content);
    }


}
