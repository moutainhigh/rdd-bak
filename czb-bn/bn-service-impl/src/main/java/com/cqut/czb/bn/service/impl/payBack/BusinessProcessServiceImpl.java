package com.cqut.czb.bn.service.impl.payBack;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.NoLoginDirectSystemMapperExtra;
import com.cqut.czb.bn.dao.mapper.directChargingSystem.OilCardRechargeMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishOrderMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatGoodsDeliveryRecordsMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatStockMapperExtra;
import com.cqut.czb.bn.entity.dto.appBuyCarWashService.AppVehicleCleanOrderDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.entity.food.DishOrder;
import com.cqut.czb.bn.entity.entity.integral.IntegralPurchaseRecord;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatGoodsDeliveryRecords;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.PartnerVipIncomeService;
import com.cqut.czb.bn.service.PaymentProcess.*;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import com.cqut.czb.bn.service.impl.directChargingSystem.NoLoginDirectSystemServiceImpl;
import com.cqut.czb.bn.service.impl.directChargingSystem.OilCardRechargeServiceImpl;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import com.cqut.czb.bn.service.impl.vehicleServiceImpl.ServerOrderServiceImpl;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.util.config.SendMesConfig.MesInfo;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BusinessProcessServiceImpl implements BusinessProcessService {

    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

    @Autowired
    DictMapperExtra dictMapperExtra;

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

    @Autowired
    PartnerVipIncomeService partnerVipIncomeService;

    @Autowired
    WeChatCommodityOrderMapper weChatCommodityOrderMapper;

    @Autowired
    WeChatGoodsDeliveryRecordsMapper weChatGoodsDeliveryRecordsMapper;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    DealWithPetrolCouponsService dealWithPetrolCouponsService;

    @Autowired
    WeChatStockMapperExtra weChatStockMapperExtra;

    @Autowired
    public WeChatPayBackService weChatPayBackService;

    @Autowired
    OilCardRechargeMapperExtra oilCardRechargeMapperExtra;

    @Autowired
    IntegralPurchaseMapperExtra integralPurchaseMapperExtra;

    @Autowired
    OilCardRechargeServiceImpl oilCardRechargeServiceImpl;

    @Autowired
    public IntegralService integralService;

    @Autowired
    public ElectricityRechargeService electricityRechargeService;

    @Autowired
    public NoLoginDirectSystemServiceImpl noLoginDirectSystemService;

    @Override
    public synchronized Map AliPayback(Object[] param, String consumptionType) {
        // 支付宝支付
        System.out.println("支付宝回调 type："+consumptionType);
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
        }else if(consumptionType.equals("PetrolCoupons")) {//油卡卡券销售
            if ((dealWithPetrolCouponsService.getAddBuyPetrolCouponsAli(params)) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if(consumptionType.equals("Direct")) {//直冲系统
            System.out.println("直冲回调");
            if (getAddBuyDirectOrderAli(params) == 1) {
//            if (addBuyDirectOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else if(consumptionType.equals("Electricity")) {//水电费充值
            System.out.println("水电费充值回调进入");
            if (getAddElectricity(params) == 1) {
//            if (addBuyDirectOrderAli(params) == 1) {
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
    public synchronized Map WeChatPayBack(Object[] param, String consumptionType){
        System.out.println("微信回调 type："+consumptionType);
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
        }else if(consumptionType.equals("Applet")){
            result.put("success", addAppletOrderWeChat(restmap));
        }else if(consumptionType.equals("AppletPayment")){
            result.put("success", weChatPayBackService.addAppletPaymentOrderWeChat(restmap));
        }else if(consumptionType.equals("RechargeVip")){
            result.put("success", weChatPayBackService.addRechargeVipOrderWeChat(restmap));
        }else if(consumptionType.equals("Direct")){
            result.put("success", getAddBuyDirectOrderWechat(restmap));
        }else if(consumptionType.equals("Electricity")){
            result.put("success",weChatPayBackService.addRechargeElectricityWechat(restmap));
        }
        else {
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

    //直冲系统（支付宝）
    public int addBuyDirectOrderAli(Map<String, String> params){
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        String orgId = "";
        double money = 0;
        String ownerId = "";
        String userId = "";
        String userAccount = "";
        int recordType = 0;
        String cardNum = "";
        Integer integralAmount = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orderId".equals(temp[0])) {
                orgId = temp[1];
            }
            if ("rechargeAmount".equals(temp[0])) {
                money = Double.valueOf(temp[1]);
            }
            if ("userId".equals(temp[0])) {
                ownerId = temp[1];
                userId = temp[1];
            }
            if ("recordType".equals(temp[0])) {
                recordType = Integer.valueOf(temp[1]);
            }
            if ("userAccount".equals(temp[0])) {
                userAccount = temp[1];
            }
            if ("cardNum".equals(temp[0])) {
                cardNum = temp[1];
            }
        }
        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
        directChargingOrderDto.setOrderId(orgId);
        directChargingOrderDto.setThirdOrderId(thirdOrderId);
        directChargingOrderDto.setUpdateAt(new Date());
        directChargingOrderDto.setState(1);
        if (recordType == 1){
            ownerId = userAccount;
        }else{
            ownerId = cardNum;
        }
        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "7", 1);
        return 1;
    }

    //水电费充值
    public int getAddElectricity(Map<String, String> params){
        System.out.println("切片水电费attach");
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        String orgId = "";
        double money = 0;
        String userId = "";
        String userAccount = "";
        int recordType = 0;
        Integer integralAmount = 0;

        String tempTime = params.get("gmt_create");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date time = null;

        try {
            time = format.parse(tempTime);
        }catch (ParseException e) {
            e.printStackTrace();
        }

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            System.out.println(temp[0] + " -------->" + temp[1]);
            if ("orderId".equals(temp[0])) {
                orgId = temp[1];
            }
            if ("rechargeAmount".equals(temp[0])) {
                money = Double.valueOf(temp[1]);
            }
            if ("recordType".equals(temp[0])) {
                recordType = Integer.valueOf(temp[1]);
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("userAccount".equals(temp[0])) {
                userAccount = temp[1];
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }
        }

        //切换充值状态
        int insert = electricityRechargeService.changeState(orgId);

        ElectricityRechargeDto electricityRechargeDto = new ElectricityRechargeDto();
        electricityRechargeDto.setOrderId(orgId);
        electricityRechargeDto.setEndTime(time);
        int update = electricityRechargeService.changeFinishTime(electricityRechargeDto);

        double actualPayment = money - integralAmount;
        actualPayment = new BigDecimal(actualPayment).setScale(2, RoundingMode.HALF_UP).doubleValue();

        if (integralAmount > 0){
            IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
            integralLogDTO.setOrderId(orgId);
            integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
            integralLogDTO.setUserId(userId);
            integralLogDTO.setIntegralLogType(5);
            integralLogDTO.setRemark("抵扣");
            integralLogDTO.setIntegralAmount(integralAmount);
            integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

            IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
            integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
            integralInfoDTO.setUserId(userId);
            integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
            integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        }

        return 1;
    }

    //直冲系统（支付宝）
    public int getAddBuyDirectOrderAli(Map<String, String> params){
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        String orgId = "";
        double money = 0;
        String ownerId = "";
        String userId = "";
        String userAccount = "";
        int recordType = 0;
        String cardNum = "";
        Integer integralAmount = 0;
        Integer isNew = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orderId".equals(temp[0])) {
                orgId = temp[1];
            }
            if ("rechargeAmount".equals(temp[0])) {
                money = Double.valueOf(temp[1]);
            }
            if ("userId".equals(temp[0])) {
                ownerId = temp[1];
                userId = temp[1];
            }
            if ("recordType".equals(temp[0])) {
                recordType = Integer.valueOf(temp[1]);
            }
            if ("isNew".equals(temp[0])) {
                isNew = Integer.valueOf(temp[1]);
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("userAccount".equals(temp[0])) {
                userAccount = temp[1];
            }
            if ("cardNum".equals(temp[0])) {
                cardNum = temp[1];
            }
        }
        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
        directChargingOrderDto.setOrderId(orgId);
        directChargingOrderDto.setThirdOrderId(thirdOrderId);
        directChargingOrderDto.setUpdateAt(new Date());
        directChargingOrderDto.setState(1);
        if (recordType == 1){
            ownerId = userAccount;
        }else{
            ownerId = cardNum;
        }
        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;

        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "7", 1);

        System.out.println("isNew"+isNew);
        if (!userId.equals("18883790995157397")) {
            if (isNew == 1) {
                //插入log记录
                IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
                integralLogDTO.setOrderId(orgId);
                integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
                integralLogDTO.setUserId(userId);
                integralLogDTO.setIntegralLogType(5);
                integralLogDTO.setRemark("抵扣");
                integralLogDTO.setIntegralAmount(integralAmount);
                integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);
                System.out.println("插入log记录");

                IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
                integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
                integralInfoDTO.setUserId(userId);
                integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
                integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
            }
        }else if (userId.equals("18883790995157397")) {
            noLoginDirectSystemService.updateState(orgId);
        }

        return 1;
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
     * 小程序（微信）
     */
    public int addAppletOrderWeChat(Map<String, Object> restmap) {
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();
        String[] temp;
        String orgId = "";
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();
        System.out.println("微信小程序支付:"+money);
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
        WeChatCommodityOrder order=new WeChatCommodityOrder();
        order.setOrderId(orgId);
        order.setThirdOrder(thirdOrderId);
        order.setUpdateAt(new Date());
        order.setPayStatus(1);
        order.setOrderState(1);
        int update= weChatCommodityOrderMapper.updateByPrimaryKeySelective(order);
        System.out.println("更改用户订单："+(update>0));

        //判断是否邮寄
        WeChatCommodityOrder order1=weChatCommodityOrderMapper.selectByPrimaryKey(orgId);
        WeChatCommodity weChatCommodity=weChatCommodityMapper.selectByPrimaryKey(order1.getCommodityId());
        String title="";
        if(weChatCommodity.getCommodityTitle().length()>10){
            title=weChatCommodity.getCommodityTitle().substring(0,8)+"…";
        }else {
            title=weChatCommodity.getCommodityTitle();
        }
        if(order1.getCommodityType()==1){
            WeChatGoodsDeliveryRecords records=new WeChatGoodsDeliveryRecords();
            records.setRecordId(StringUtil.createId());
            records.setAddressId(order1.getAddressId());
            records.setCreateAt(new Date());
            records.setDeliveryState(0);
            records.setOrderId(orgId);
            weChatGoodsDeliveryRecordsMapper.insertSelective(records);
        }else {
            // 发送短信
            //查出商家电话
            Shop shop=shopMapper.selectByPrimaryKey(weChatCommodity.getShopId());
            PhoneCode.sendAppletShopMessage(order1.getPhone(),title,order1.getCommodityNum(),order1.getElectronicCode(),shop.getShopPhone());

        }

        //更改商品数量
        WeChatCommodity commodity=new WeChatCommodity();
        commodity.setCommodityId(order1.getCommodityId());
        //计算商品的总库存量
        int num=weChatCommodity.getCommodityNum()-order1.getCommodityNum();
        if(num>=0){
            commodity.setCommodityNum(num);
        }else {
            commodity.setCommodityNum(0);
        }
        //计算商品的总销售量
        int saleNum=weChatCommodity.getSalesVolume()+order1.getCommodityNum();
        commodity.setSalesVolume(saleNum);
        weChatCommodityMapper.updateByPrimaryKeySelective(commodity);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        Boolean isSucceed=fanYongService.AppletBeginFanYong(ownerId,money,orgId,order1.getFyMoney(),title);
        System.out.println("返佣"+isSucceed);

        //businessType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务，5为点餐,6小程序购物
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "6", 2);

        return 1;
    }

    //直冲系统（微信）
    public int getAddBuyDirectOrderWechat(Map<String, Object> restmap){
//        String[] resDate = restmap.get("attach").toString().split("\\^");
//        String[] temp;
//        String thirdOrderId = restmap.get("transaction_id").toString();
//        String orgId = "";
//        double money = 0;
//        String ownerId = "";
//        String userAccount = "";
//        String commodityId = "";
//        int recordType = 0;
//        String cardNum = "";
//        String userId = "";
//        int integralAmount = 0;
//        for (String data : resDate) {
//            temp = data.split("\'");
//            if (temp.length < 2) {//判空
//                continue;
//            }
//            if ("orderId".equals(temp[0])) {
//                orgId = temp[1];
//            }
//            if ("rechargeAmount".equals(temp[0])) {
//                money = Double.valueOf(temp[1]);
//            }
//            if ("recordType".equals(temp[0])) {
//                recordType = Integer.valueOf(temp[1]);
//            }
//            if ("userAccount".equals(temp[0])) {
//                userAccount = temp[1];
//            }
//            if ("userId".equals(temp[0])) {
//                userId = temp[1];
//            }
//            if ("commodityId".equals(temp[0])) {
//                commodityId = temp[1];
//            }
//            if ("integralAmount".equals(temp[0])) {
//                integralAmount = Integer.valueOf(temp[1]);
//            }
//        }
//        DirectChargingOrderDto directChargingOrderDto = new DirectChargingOrderDto();
//        directChargingOrderDto.setOrderId(orgId);
//        directChargingOrderDto.setThirdOrderId(thirdOrderId);
//        directChargingOrderDto.setUpdateAt(new Date());
//        directChargingOrderDto.setState(1);
//        if (recordType == 1){
//            ownerId = userAccount;
//        }else{
//            ownerId = cardNum;
//        }
//        System.out.println("更新成功");
//        boolean update = oilCardRechargeMapperExtra.updateRechargeRecord(directChargingOrderDto) > 0;
//        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "7", 1);
//
//        if (!userId.equals("18883790995157397")) {
//            //插入log记录
//            System.out.println("插入log记录");
//            IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
//            integralLogDTO.setOrderId(orgId);
//            integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
//            integralLogDTO.setUserId(userId);
//            integralLogDTO.setIntegralLogType(5);
//            integralLogDTO.setRemark("抵扣");
//            integralLogDTO.setIntegralAmount(integralAmount);
//            integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);
//
//            IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
//            integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
//            integralInfoDTO.setUserId(userId);
//            integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
//            integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
//        }else if (userId.equals("18883790995157397")){
//            boolean i = noLoginDirectSystemService.updateState(orgId);
//        }
//
//        DirectChargingOrderDto directChargingOrderDto1 = oilCardRechargeMapperExtra.getOrder(orgId);
//        if (dictMapperExtra.selectDictByName("is_direct_recharge").getContent().equals("0")) {
//            System.out.println("尚未开通充值");
//            return 1;
//        }
//        if (directChargingOrderDto1.getRecordType()==1){
//            System.out.println("开通充值");
//            directChargingOrderDto1.setUserAccount(directChargingOrderDto1.getRechargeAccount());
//            directChargingOrderDto1.setRechargeAmount(directChargingOrderDto1.getRealPrice());
//            oilCardRechargeServiceImpl.phoneRechargeSubmission(directChargingOrderDto1);
//            System.out.println("充值参数"+directChargingOrderDto1.toString());
//        }else{
//            System.out.println("开通充值");
//            directChargingOrderDto1.setRechargeAmount(directChargingOrderDto1.getRealPrice());
//            oilCardRechargeServiceImpl.onlineorderSubmission(directChargingOrderDto1);
//        }
//        return 1;
        return 0;
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
            Boolean k1=serverCouponMapperExtra.updateCoupons(couponId)>0;
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
        Boolean ve=vehicleCleanOrderMapperExtra.updateMyBackOrder(orderDTO)>0;
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
        Boolean beginFanYong= fanYongService.beginFanYong(3,area,ownerId,money,money,orgId);
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
        Boolean beginFanYong= fanYongService.beginFanYong(3,area,ownerId,money,money,orgId);
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
        Boolean beginFanYong= fanYongService.beginFanYong(2,area,ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        Boolean addVipIncome=partnerVipIncomeService.addVipIncome(ownerId,money,1);
        System.out.println("addVipIncome"+addVipIncome);
//        //发送购买成功推送给特定用户
        editContent(ownerId,null,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_VIP.getNoticeId(), money);
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
        Boolean kk= userMapperExtra.UpdateToVip(ownerId)>0;
        System.out.println("更改用户为vip"+kk);

        //插入VIP充值记录表
        dataProcessService.insertRechargeOrder(money,2,orgId,thirdOrderId,ownerId,vipAreaConfigId);

        //查询是否为首次消费
        dataProcessService.isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务
        //插入消费记录
        dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 2);

        //进行返佣
        Boolean beginFanYong= fanYongService.beginFanYong(2,area,ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        //vip是1 油卡是2
        Boolean addVipIncome=partnerVipIncomeService.addVipIncome(ownerId,money,1);
        System.out.println("addVipIncome"+addVipIncome);
//        //发送购买成功推送给特定用户
        editContent(ownerId,null,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_VIP.getNoticeId(), money);
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

        if ("2".equals(payType)) {
            System.out.println("开始充值");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "1", 1);
            //插入消费记录
            Boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);

            if (beginPetrolRecharge == true){
                //vip是1 油卡是2
                Boolean addVipIncome=partnerVipIncomeService.addVipIncome(ownerId,money,2);
                System.out.println("addVipIncome"+addVipIncome);
                //发送购买成功推送给特定用户
                editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_PETROL.getNoticeId(), money);
                return 1;
            }
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "0", 1);
            //vip是1 油卡是2
            Boolean addVipIncome=partnerVipIncomeService.addVipIncome(ownerId,money,2);
            System.out.println("addVipIncome"+addVipIncome);

            Boolean isChange = dataProcessService.changeInfo(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);

            Integer putBack = dataProcessService.putBackPetrol(isChange,petrolNum);
            //发送购买成功推送给特定用户
            editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.BUY_PETROL.getNoticeId(), money);
            return putBack;
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

            Boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);

            if (beginPetrolRecharge == true){
                //vip是1 油卡是2
                Boolean addVipIncome=partnerVipIncomeService.addVipIncome(ownerId,money,2);
                System.out.println("addVipIncome"+addVipIncome);
                //发送购买成功推送给特定用户
                editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_PETROL.getNoticeId(), money);
                return 1;
            }
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买0");
            //插入消费记录
            dataProcessService.insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);
            //此处插入购油的相关信息，油卡购买记录
            //vip是1 油卡是2
            Boolean addVipIncome=partnerVipIncomeService.addVipIncome(ownerId,money,2);
            System.out.println("addVipIncome"+addVipIncome);

            Boolean isChange = dataProcessService.changeInfo(area,thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);
            //判断是否放回油卡
            Integer putBack = dataProcessService.putBackPetrol(isChange,petrolNum);
            //发送购买成功推送给特定用户
            editContent(ownerId,petrolNum,MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.BUY_PETROL.getNoticeId(), money);
            return putBack;
        }

        return 1;
    }

    public void editContent(String ownerId,String petrolNum,String userId,String noticeId, Double money) {
        //发送购买成功推送给特定用户
        Map<String,String> content = new HashMap<>();
        if (ownerId == null && petrolNum == null){
            return;
        }
        if (petrolNum != null) {
            Petrol petrol = petrolMapperExtra.selectPetrolByNum(petrolNum);
            if (petrol!=null && petrol.getPetrolKind()==1){
                content.put("petrolKind","中石油");
            }else if (petrol!=null && petrol.getPetrolKind()==2) {
                content.put("petrolKind","中石化");
            }else if (petrol!=null && petrol.getPetrolKind()==0) {
                content.put("petrolKind","国通");
            }
            content.put("petrolPrice", String.valueOf(money));
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
