package com.cqut.czb.bn.service.impl.payBack;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.InfoSpreadService;
import com.cqut.czb.bn.service.PaymentProcess.DataProcessService;
import com.cqut.czb.bn.service.PaymentProcess.FanYongService;
import com.cqut.czb.bn.service.PaymentProcess.PetrolRecharge;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DataProcessServiceImpl implements DataProcessService {

    @Autowired
    private FanYongService fanYongService;

    @Autowired
    private PetrolMapperExtra petrolMapperExtra;

    @Autowired
    UserIncomeInfoMapperExtra userIncomeInfoMapperExtra;

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

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
    UserMapper userMapper;

    @Autowired
    VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

    @Autowired
    VipRechargeRecordsMapperExtra vipRechargeRecordsMapperExtra;

    @Autowired
    DictMapperExtra dictMapperExtra;

    //解析订单数据用于处理
    @Override
    public  PetrolSalesRecordsDTO getOrderdata(Map<String, String> params) {
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

    //插入VIP充值记录表
    @Override
    public void insertRechargeOrder(double money, int payMethod, String orgId, String thirdOrderId, String ownerId, String vipAreaConfigId){
        //查出此人属于哪个地区的vip
        String area;

        VipRechargeRecords vipRechargeRecords=new VipRechargeRecords();
        vipRechargeRecords.setAmount(money);
        vipRechargeRecords.setIsReceived(1);
        vipRechargeRecords.setRechargeWay(payMethod);//2为微信
        vipRechargeRecords.setRecordId(orgId);
        vipRechargeRecords.setThirdTradeNum(thirdOrderId);
        vipRechargeRecords.setUserId(ownerId);
        vipRechargeRecords.setVipAreaConfigId(vipAreaConfigId);
        boolean isRecharge=vipRechargeRecordsMapperExtra.insert(vipRechargeRecords)>0;
        System.out.println("插入VIP充值记录表"+isRecharge);
    }

    //查询是否首次消费
    @Override
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

    //插入消费记录（payType对应0为油卡购买，1为油卡充值,2为购买服务）
    @Override
    public void insertConsumptionRecord(String orgId, String thirdOrderId, double money, String ownerId, String businessType, int payMethod) {
        ConsumptionRecord consumptionRecord = new ConsumptionRecord();
        consumptionRecord.setRecordId(StringUtil.createId());
        consumptionRecord.setLocalOrderId(orgId);//本地订单号
        consumptionRecord.setThirdOrderId(thirdOrderId);//第三方订单号
        consumptionRecord.setMoney(money);
        consumptionRecord.setType(Integer.valueOf(businessType));//0为油卡购买，1为油卡充值,2为充值vip，3为购买服务，4为洗车服务
        consumptionRecord.setUserId(ownerId);
        consumptionRecord.setOriginalAmount(money);//油卡面额
        consumptionRecord.setPayMethod(payMethod);//1为支付宝2为微信
        boolean insertInfo = consumptionRecordMapperExtra.insert(consumptionRecord) > 0;
        System.out.println("插入用户消费记录" + insertInfo);
    }

    //更改油卡状态
    @Override
    public boolean updatePetrol(Petrol petrol) {
        return petrolMapperExtra.updateByPrimaryKeySelective(petrol) > 0;
    }

    //新增购买记录表——插入
    @Override
    public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
        return petrolSalesRecordsMapperExtra.insert(petrolSalesRecords) > 0;
    }

    //进行所有的操作——相关表的增删改查（油卡表，新增购买记录表，收益变更记录表，用户收益信息表
    @Override
    public boolean changeInfo(String area,String thirdOrderId, double money, String petrolNum, String ownerId, double actualPayment, String addressId, String orgId) {
        //更改油卡状态
        //取出油卡
        Petrol petrol = PetrolCache.currentPetrolMap.get(petrolNum);
        if (petrol == null) {
            //查看是否放回主存PetrolCache.AllpetrolMap中
            petrol=PetrolCache.AllpetrolMap.get(petrolNum);
            if(petrol==null){
                System.out.println("油卡为空");
                return false;
            }else {//主存中存在则再次放回缓存中
                PetrolCache.currentPetrolMap.put(petrolNum,petrol);
                PetrolCache.AllpetrolMap.remove(petrolNum);
            }
        }
        petrol.setOwnerId(ownerId);
        petrol.setState(2);
        petrol.setPetrolPrice(money);
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
        petrolSalesRecords.setTurnoverAmount(money);
        //更改销售原价
        Double petrolDenomination=getDenomination(money,ownerId,area);
        petrolSalesRecords.setDenomination(petrolDenomination);
        petrolSalesRecords.setCurrentPrice(money);//售价
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

//        //发放补贴给购卡人
//        Double sendMoney =getSubsidies(orgId,money,ownerId,area);
//        System.out.println("发放补贴"+sendMoney);
//        double money1= BigDecimal.valueOf(money).subtract(BigDecimal.valueOf(sendMoney)).doubleValue();
//        System.out.println("实际支付"+money1);

        boolean beginFanYong = fanYongService.beginFanYong(1,area,ownerId, money, money, orgId);

        if (beginFanYong == true)
            return true;
        else {
            System.out.println("新增购买记录表有问题或beginFanYong");
            return false;
        }
    }

    //放回油卡
    @Override
    public int putBackPetrol(boolean isSucceed,String petrolNum){
        //若插入失败则放回卡
        if (isSucceed != true) {
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
    }

    @Override
    public Double sendSubsidies(String orgId, double money, String ownerId, String area) {
        User user = userMapper.selectByPrimaryKey(ownerId);
        VipAreaConfig vipAreaConfig = vipAreaConfigMapperExtra.selectVipAreaConfigByArea(area);
        if (vipAreaConfig != null && user != null && user.getIsVip() == 1) {
            Dict dict= dictMapperExtra.selectDictByName("petrol_subsidies_rate");
            double FYrate=Double.valueOf(dict.getContent());
            UserIncomeInfo oldUserIncomeInfo = userIncomeInfoMapperExtra.selectOneUserIncomeInfo(ownerId);//查出原收益信息
            fanYongService.changeUserIncomeInfo("购油补贴", ownerId, ownerId, 1, oldUserIncomeInfo, money, money, ownerId, 1, FYrate, orgId);
            return BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(FYrate)).doubleValue();
        }
        return 0.0;
    }

    @Override
    public Double getSubsidies(String orgId, double money, String ownerId, String area) {
        User user = userMapper.selectByPrimaryKey(ownerId);
        VipAreaConfig vipAreaConfig = vipAreaConfigMapperExtra.selectVipAreaConfigByArea(area);
        if (vipAreaConfig != null && user != null && user.getIsVip() == 1) {
            Dict dict= dictMapperExtra.selectDictByName("petrol_subsidies_rate");
            double FYrate=Double.valueOf(dict.getContent());
            return BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(FYrate)).doubleValue();
        }
        return 0.0;
    }

    @Override
    public Double getDenomination(double money,String ownerId, String area) {
        if(area==null||area.equals("")){
            area="重庆市";
        }
        User user = userMapper.selectByPrimaryKey(ownerId);
        VipAreaConfig vipAreaConfig = vipAreaConfigMapperExtra.selectVipAreaConfigByArea(area);
        if (vipAreaConfig != null && user != null && user.getIsVip() == 1) {
            Dict dict= dictMapperExtra.selectDictByName("petrol_denomination");
            double FYrate=Double.valueOf(dict.getContent());
            return BigDecimal.valueOf(money).multiply(BigDecimal.valueOf(FYrate)).doubleValue();
        }
        return money;
    }


}
