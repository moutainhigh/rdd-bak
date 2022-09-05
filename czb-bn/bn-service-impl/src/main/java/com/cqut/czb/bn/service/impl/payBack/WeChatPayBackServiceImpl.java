package com.cqut.czb.bn.service.impl.payBack;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.dao.mapper.food.DishOrderMapper;
import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseMapperExtra;
import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseRecordMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.VehicleCleanOrderMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatGoodsDeliveryRecordsMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatStockMapperExtra;
import com.cqut.czb.bn.entity.dto.appCaptchaConfig.PhoneCode;
import com.cqut.czb.bn.entity.dto.directCustomers.CustomerManageDto;
import com.cqut.czb.bn.entity.dto.directCustomers.DirectCustomerManagerDto;
import com.cqut.czb.bn.entity.dto.directCustomers.ElectricityRechargeDto;
import com.cqut.czb.bn.entity.dto.integral.IntegralInfoDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatGoodsDeliveryRecords;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatStock;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.service.PartnerVipIncomeService;
import com.cqut.czb.bn.service.PaymentProcess.*;
import com.cqut.czb.bn.service.electricityRecharge.ElectricityRechargeService;
import com.cqut.czb.bn.service.impl.integral.IntegralServiceImpl;
import com.cqut.czb.bn.service.impl.vehicleServiceImpl.ServerOrderServiceImpl;
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
public class WeChatPayBackServiceImpl implements WeChatPayBackService {
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
    ElectricityRechargeService electricityRechargeService;

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
    IntegralServiceImpl integralService;

    @Autowired
    IntegralPurchaseMapperExtra integralPurchaseMapperExtra;

    @Override
    /**
     * 小程序库存支付成功(微信)
     * @param restmap
     * @return
     */
    public Integer addAppletPaymentOrderWeChat(Map<String, Object> restmap) {
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
        String stockIds = "";
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

            if("stockIds".equals(temp[0])){
                stockIds = temp[1];
            }
        }
        System.out.println(ownerId);
        System.out.println(stockIds);
        List<String> myList = new ArrayList<String>(Arrays.asList(stockIds.split(",")));

        List<WeChatStock> ids = new ArrayList<>();

        for (String weChatStock:myList){
            WeChatStock weChatStock1 = new WeChatStock();
            weChatStock1.setBuyerId(ownerId);
            weChatStock1.setStockId(weChatStock);
            ids.add(weChatStock1);
        }
        //更改库存状态
        int updateStock =  weChatStockMapperExtra.updateStockState(ids);
        System.out.println("更改库存状态："+(updateStock>0));

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
        WeChatCommodity weChatCommodity = weChatCommodityMapper.selectByPrimaryKey(order1.getCommodityId());
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

    @Override
    public Integer addRechargeVipOrderWeChat(Map<String, Object> restmap) {
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
        Boolean beginFanYong= fanYongService.AppletVIPBeginFanYong(ownerId,money,orgId,money);
        System.out.println("返佣"+beginFanYong);
        //vip是1 油卡是2
        Boolean addVipIncome=partnerVipIncomeService.addVipIncome(ownerId,money,1);
        System.out.println("addVipIncome"+addVipIncome);
//        //发送购买成功推送给特定用户
//        editContent(ownerId,null, MesInfo.userId.BOSS.getUserId(),MesInfo.noticeId.RECHARGE_VIP.getNoticeId(), money);
        return 1;
    }

    @Override
    public Integer addRechargeElectricityWechat(Map<String, Object> restmap) {
        for (Map.Entry<String, Object> entry : restmap.entrySet()) {
            System.out.println(entry.getKey() + "------>" + entry.getValue().toString());
        }
        String[] resDate = restmap.get("attach").toString().split("\\^");
        //商户订单号
        String out_trade_no = restmap.get("out_trade_no").toString();
        //微信交易订单号
        String thirdOrderId = restmap.get("transaction_id").toString();

        String temp1 = restmap.get("time_end").toString();

        String endTime = temp1.substring(0,4) + "-" + temp1.substring(4,6) + "-" + temp1.substring(6,8) + " " + temp1.substring(8,10)
                + ":" + temp1.substring(10,12) + ":" + temp1.substring(12,14);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date time = null;

        try {
            time = format.parse(endTime);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        String orgId = "";
        String rechargeAccount = "";
        String integralAmount = "";
        String rechargeAmount = "";
        String userId = "";
        String[] temp;
        double money = Double.valueOf(restmap.get("total_fee").toString());
        money = (BigDecimal.valueOf(money).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)).doubleValue();

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orderId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println("orderId===>" + orgId);
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
                System.out.println("userId===>" + userId);
            }
            if ("rechargeAmount".equals(temp[0])) {
                rechargeAmount = temp[1];
                System.out.println("rechargeAmount===>"+rechargeAmount);
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = temp[1];
                System.out.println("integralAmount===>"+integralAmount);
            }
            if ("rechargeAccount".equals(temp[0])) {
                rechargeAccount = temp[1];
                System.out.println("rechargeAccount===>"+rechargeAccount);
            }
        }

        String regional = electricityRechargeService.findRegional(orgId);
        if (regional == null){
            System.out.println("未找到城市对应得");
            return 0;
        }else {
            System.out.println("regional===>"+regional);
        }

        int insert = electricityRechargeService.changeState(orgId);

        if (insert > 0) {
            System.out.println("改变充值状态");
        }

        ElectricityRechargeDto electricityRechargeDto = new ElectricityRechargeDto();
        electricityRechargeDto.setViewTime(time);
        electricityRechargeDto.setOrderId(orgId);
        int timeChange = electricityRechargeService.changeFinishTime(electricityRechargeDto);

        Integer integral = Integer.parseInt(integralAmount);

        if (integral > 0){
            IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
            integralLogDTO.setOrderId(orgId);
            integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
            integralLogDTO.setUserId(userId);
            integralLogDTO.setIntegralLogType(5);
            integralLogDTO.setRemark("抵扣");
            integralLogDTO.setIntegralAmount(integral);
            integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

            IntegralInfoDTO integralInfoDTO = integralService.getGotTotal(userId);
            integralInfoDTO.setCurrentTotal(integralLogDTO.getBeforeIntegralAmount() - integralLogDTO.getIntegralAmount());
            integralInfoDTO.setUserId(userId);
            integralInfoDTO.setGotTotal(integralInfoDTO.getGotTotal());
            integralPurchaseMapperExtra.updateIntegralInfo(integralInfoDTO);
        }

        //TODO 调用第三方接口水电费充值
        System.out.println("调用第三方水电费接口");

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
