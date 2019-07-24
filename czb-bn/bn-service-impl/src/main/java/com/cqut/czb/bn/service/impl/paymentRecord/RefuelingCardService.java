package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.PersonalCenterUserDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.IRefuelingCard;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import com.cqut.czb.bn.service.petrolRecharge.FanYongService;
import com.cqut.czb.bn.service.petrolRecharge.PetrolRecharge;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RefuelingCardService implements IRefuelingCard {

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

    // 同一时间只允许一个线程访问购买油卡接口
    public synchronized Map AliPayCallback(Object[] param) {
        // 支付宝支付
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        if (getAddPaymentRecordData(params) == 1) {//获取订单数据存入数据库(支付宝)
            result.put("success", AlipayConfig.response_success);
            return result;
        } else {
            result.put("fail", AlipayConfig.response_fail);
            return result;
        }
    }

    @Override
    public synchronized Map AliBuyServicePayCallback(Object[] param) {
        // 支付宝支付
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        if (getAddServiceOrderData(params) == 1) {//获取订单数据存入数据库(支付宝)
            result.put("success", AlipayConfig.response_success);
            return result;
        } else {
            result.put("fail", AlipayConfig.response_fail);
            return result;
        }
    }

    @Override
    public Map AliRechargeVipPayCallback(Object[] param) {
        // 支付宝支付
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        if (getAddVipOrderData(params) == 1) {//获取订单数据存入数据库(支付宝)
            result.put("success", AlipayConfig.response_success);
            return result;
        } else {
            result.put("fail", AlipayConfig.response_fail);
            return result;
        }
    }

    @Override
    public Map WeChatRechargeVipPayCallback(Object[] param) {
        Map<String, Object> restmap = (HashMap<String, Object>) param[0];
        Map<String, Integer> result = new HashMap<>();
        result.put("success", addVipOrderWeChat(restmap));//返回后进行判断
        return result;
    }

    @Override
    public Map WeChatPayCallback(Object[] param) {
        Map<String, Object> restmap = (HashMap<String, Object>) param[0];
        Map<String, Integer> result = new HashMap<>();
        result.put("success", addPaymentRecordDataForWechat(restmap));//返回后进行判断
        return result;
    }

    @Override
    public Map WeChatBuyServicePayCallback(Object[] param) {
        Map<String, Object> restmap = (HashMap<String, Object>) param[0];
        Map<String, Integer> result = new HashMap<>();
        result.put("success", addBuyServiceOrderWeChat(restmap));//返回后进行判断
        return result;
    }

    @Override
    public void purchaseFailed(Object[] param) {
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = getOrderdata(params);
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
     * 解析订单数据用于处理（成功此块有点冗余）
     */
    public static PetrolSalesRecordsDTO getOrderdata(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        // petrol_record主键
        String id = "";
        // 0代表充值，1代表购油——对应payType
        String payType = "";
        double money = 0;
        int petrolKind = 0;
        String petrolNum = "";
        String ownerId = "";
        double actualPayment = 0;
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = new PetrolSalesRecordsDTO();
        petrolSalesRecordsDTO.setPaymentMethod(0);
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {
                continue;
            }
            if ("orgId".equals(temp[0])) {
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setThirdOrderId(temp[1]);
            }
            if ("payType".equals(temp[0])) {
                System.out.println(temp[0] + ":" + temp[1]);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPayType(Integer.parseInt(temp[1]));
            }
            if ("money".equals(temp[0])) {
                System.out.println("充值金额:money" + money);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPetrolPrice(Double.valueOf(temp[1]));
            }
            if ("petrolKind".equals(temp[0])) {
                System.out.println("油卡类型petrolKind:" + petrolKind);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPetrolKind(Integer.parseInt(temp[1]));
            }
            if ("petrolNum".equals(temp[0])) {
                System.out.println("油卡号petrolNum:" + petrolNum);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setPetrolNum(temp[1]);
            }
            if ("ownerId".equals(temp[0])) {
                System.out.println("用户id:" + ownerId);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setBuyerId(temp[1]);
            }
            if ("actualPayment".equals(temp[0])) {
                System.out.println("实际支付actualPayment:" + actualPayment);
                if (temp[1] != null)
                    petrolSalesRecordsDTO.setActualPayment(Double.valueOf(temp[1]));
            }
        }
        return petrolSalesRecordsDTO;
    }


    /**
     * 获取订单数据存入数据库(支付宝)购买服务
     */
    public synchronized int getAddServiceOrderData(Map<String, String> params) {
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
        isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }

    public synchronized int getAddVipOrderData(Map<String, String> params) {
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
//            vipAreaConfigId
            if ("vipAreaConfigId".equals(temp[0])) {
                ownerId = temp[1];
                System.out.println("用户id:" + vipAreaConfigId);
            }
        }
        //更改用户状态
        boolean changeToVip= userMapperExtra.UpdateToVip(ownerId)>0;
        System.out.println("更改用户为vip"+changeToVip);

        //插入VIP充值记录表
        insertRechargeOrder(money,2,orgId,thirdOrderId,ownerId,vipAreaConfigId);


        //查询是否为首次消费
        isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "3", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }

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
        insertRechargeOrder(money,2,orgId,thirdOrderId,ownerId,vipAreaConfigId);

        //查询是否为首次消费
        isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "3", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }


    /**
     * 获取订单数据存入数据库(支付宝)购油
     */
    public synchronized int getAddPaymentRecordData(Map<String, String> params) {
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
        isHaveConsumption(ownerId);

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
            insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 1);

            boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);
            if (beginPetrolRecharge == true)
                return 1;
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买");
            //插入消费记录
            insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);

            boolean ischange = changeInfo(thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);

            //若插入失败则放回卡
            if (ischange != true) {
                Petrol petrol = PetrolCache.currentPetrolMap.get(petrolNum);
                if (petrol == null) {
                    return 2;
                }
                petrol.setOwnerId("");
                petrol.setEndTime(0);
                PetrolCache.AllpetrolMap.put(petrolNum, petrol);//放回all中
                PetrolCache.currentPetrolMap.remove(petrolNum);
                return 2;
            }

            //成功后移除对应的卡
            PetrolCache.currentPetrolMap.remove(petrolNum);
            return 1;

        } else if ("1".equals(payType)) {
            System.out.println("优惠卷" + payType);
        }
        return 1;
    }

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
        isHaveConsumption(ownerId);

        //payType对应0为油卡购买，1为油卡充值,2为购买服务
        //插入消费记录
        insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, "2", 1);

        //进行返佣
        boolean beginFanYong= fanYongService.beginFanYong(ownerId,money,money,orgId);
        System.out.println("返佣"+beginFanYong);
        return 1;
    }


    /**
     * 获取订单数据存入数据库(微信)
     */
    public int addPaymentRecordDataForWechat(Map<String, Object> restmap) {
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
        isHaveConsumption(ownerId);

        //payType对应"0"为购油"1"代表的是优惠卷购买（vip未有）"2"代表的是充值
        if ("2".equals(payType)) {
            System.out.println("开始充值2");
            PetrolSalesRecords petrolSalesRecords = new PetrolSalesRecords();
            petrolSalesRecords = petrolSalesRecordsMapperExtra.selectInfoByOrgId(orgId);
            PetrolInputDTO petrolInputDTO = new PetrolInputDTO();
            petrolInputDTO.setOwnerId(ownerId);
            petrolInputDTO.setPetrolKind(petrolSalesRecords.getPetrolKind());
            Petrol petrol1 = petrolMapperExtra.selectDifferentPetrol(petrolInputDTO);
            //插入消费记录
            insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);

            boolean beginPetrolRecharge = petrolRecharge.beginPetrolRecharge(thirdOrderId, money, petrolNum, ownerId, actualPayment, orgId);
            if (beginPetrolRecharge == true)
                return 1;
            else
                return 2;
        } else if ("0".equals(payType)) {
            System.out.println("开始购买0");
            //插入消费记录
            insertConsumptionRecord(orgId,thirdOrderId, money, ownerId, payType, 2);

//			此处插入购油的相关信息，油卡购买记录
            boolean ischange = changeInfo(thirdOrderId, money, petrolNum, ownerId, actualPayment, addressId, orgId);

            //若插入失败则放回卡
            if (ischange != true) {
                Petrol petrol = PetrolCache.currentPetrolMap.get(petrolNum);
                if (petrol == null) {
                    return 2;
                }
                petrol.setOwnerId("");
                petrol.setEndTime(0);
                PetrolCache.AllpetrolMap.put(petrolNum, petrol);//放回all中
                PetrolCache.currentPetrolMap.remove(petrolNum);
                return 2;
            }

            //成功后移除对应的卡
            PetrolCache.currentPetrolMap.remove(petrolNum);
            return 1;

        } else if ("1".equals(payType)) {
            System.out.println("优惠卷" + payType);
        }
        return 1;
    }


    /**
     * 进行所有的操作——相关表的增删改查（油卡表，新增购买记录表，收益变更记录表，用户收益信息表）
     *
     * @return
     */
    public boolean changeInfo(String thirdOrderId, double money, String petrolNum, String ownerId, double actualPayment, String addressId, String orgId) {
        //油卡表——更改相应油卡的状态（用户的id，卡号）——更改
        //取出油卡
        Petrol petrol = PetrolCache.currentPetrolMap.get(petrolNum);
        if (petrol == null) {
            System.out.println("油卡为空");
            return false;
        }
        petrol.setOwnerId(ownerId);
        petrol.setState(2);
        boolean updatePetrol = updatePetrol(petrol);
        System.out.println("更改油卡状态完毕" + updatePetrol);

        //通过商家订单号查询充值信息
        PetrolSalesRecords petrolSalesRecords = new PetrolSalesRecords();
        petrolSalesRecords = petrolSalesRecordsMapperExtra.selectInfoByOrgId(orgId);
        if (petrolSalesRecords == null) {
            System.out.println("无充值信息");
            return false;
        }
        //更改油卡购买信息的状态
        petrolSalesRecords.setState(1);
        petrolSalesRecords.setThirdOrderId(thirdOrderId);
        if (petrol.getPetrolKind() == 0) {
            petrolSalesRecords.setIsRecharged(-1);
        }
        boolean update = petrolSalesRecordsMapperExtra.updateByPrimaryKeySelective(petrolSalesRecords) > 0;
        System.out.println("更改购买信息:" + update);

        //新增油卡邮寄记录——插入
        if (petrol.getPetrolType() == 1) {//实体卡才寄送
            PetrolDeliveryRecords petrolDeliveryRecords = new PetrolDeliveryRecords();
            petrolDeliveryRecords.setAddressId(addressId);
            petrolDeliveryRecords.setPetrolNum(petrolNum);
            petrolDeliveryRecords.setDeliveryState(0);
            petrolDeliveryRecords.setRecordId(StringUtil.createId());
            boolean isInsert = petrolDeliveryRecordsMapperExtra.insert(petrolDeliveryRecords) > 0;
            System.out.println("新增油卡邮寄记录" + isInsert);
        }

        boolean beginFanYong = fanYongService.beginFanYong(ownerId, money, actualPayment, orgId);

        if (beginFanYong == true)
            return true;
        else {
            System.out.println("新增购买记录表有问题或beginFanYong");
            return false;
        }
    }

    //油卡表——更改相应油卡的状态（用户的id，卡号）——更改
    @Override
    public boolean updatePetrol(Petrol petrol) {
        return petrolMapperExtra.updateByPrimaryKeySelective(petrol) > 0;
    }

    //新增购买记录表——插入
    @Override
    public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
        return petrolSalesRecordsMapperExtra.insert(petrolSalesRecords) > 0;
    }


    //插入VIP充值记录表
    public void insertRechargeOrder(double money,int payType,String orgId,String thirdOrderId,String ownerId,String vipAreaConfigId){
       //查出此人属于哪个地区的vip
        String area;

        VipRechargeRecords vipRechargeRecords=new VipRechargeRecords();
        vipRechargeRecords.setAmount(money);
        vipRechargeRecords.setIsReceived(1);
        vipRechargeRecords.setRechargeWay(payType);//2为微信
        vipRechargeRecords.setRecordId(orgId);
        vipRechargeRecords.setThirdTradeNum(thirdOrderId);
        vipRechargeRecords.setUserId(ownerId);
        vipRechargeRecords.setVipAreaConfigId(vipAreaConfigId);
        boolean isRecharge=vipRechargeRecordsMapperExtra.insert(vipRechargeRecords)>0;
        System.out.println("插入VIP充值记录表"+isRecharge);
    }

    //查询是否首次消费
    public void isHaveConsumption(String ownerId){
        List<ConsumptionRecord> consumptionRecordList = consumptionRecordMapperExtra.selectByPrimaryKey(ownerId);
        if (consumptionRecordList.size() == 0) {
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(currentTime);
            PartnerDTO partnerDTO = new PartnerDTO();
            partnerDTO.setUserId(ownerId);
            partnerDTO.setMonthTime(dateString);
            boolean isSuccessful = infoSpreadService.addChildConsumer(partnerDTO);
            System.out.println("插入消费人数" + isSuccessful);
        }
    }

    //payType对应0为油卡购买，1为油卡充值,2为购买服务
    //插入消费记录
    public void insertConsumptionRecord(String orgId,String thirdOrderId,double money,String ownerId,String businessType,int payMethod) {
        ConsumptionRecord consumptionRecord = new ConsumptionRecord();
        consumptionRecord.setRecordId(StringUtil.createId());
        consumptionRecord.setLocalOrderId(orgId);//本地订单号
        consumptionRecord.setThirdOrderId(thirdOrderId);//第三方订单号
        consumptionRecord.setMoney(money);
        consumptionRecord.setType(Integer.valueOf(businessType));//0为油卡购买，1为油卡充值,2购买服务,3vip充值
        consumptionRecord.setUserId(ownerId);
        consumptionRecord.setOriginalAmount(money);//油卡面额
        consumptionRecord.setPayMethod(payMethod);//1为支付宝2为微信
        boolean insertInfo = consumptionRecordMapperExtra.insert(consumptionRecord) > 0;
        System.out.println("插入用户消费记录" + insertInfo);
    }

}
