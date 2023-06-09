package com.cqut.czb.bn.service.impl.AppPaymentServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.PayConfig.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.AliPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInfo;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.WeChatPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppHomePageService;
import com.cqut.czb.bn.service.appPaymentService.AppBuyPetrolService;
import com.cqut.czb.bn.service.appPaymentService.PurchaseCouponService;
import com.cqut.czb.bn.service.impl.AppPaymentServiceImpl.luPay.PurchaseCouponServiceImpl;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

@Service
public class AppBuyPetrolServiceImpl implements AppBuyPetrolService {

    private String characterEncoding = "UTF-8";

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    private PetrolMapperExtra petrolMapperExtra;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VipAreaConfigMapperExtra vipAreaConfigMapperExtra;

    @Autowired
    AppHomePageService appHomePageService;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Autowired
    PurchaseCouponService purchaseCouponService;

    @Override
    public Map<String, Object> ShuntProcessing(PetrolInputDTO petrolInputDTO) {
        //检验传输数据是否为空
        if (petrolInputDTO == null) {
            Map<String,Object> info=new HashMap<>();
            info.put("-1","油卡申请失败，信息有误，无此法生成订单");
            return info;
        }

        //3代表中石化优惠券；
        if(petrolInputDTO.getPetrolKind()==3){
           //判断是否为重庆市的
            if("重庆市".equals(petrolInputDTO.getArea())==false){
                Map<String,Object> info=new HashMap<>();
                info.put("-1","油卡申请失败，信息有误，无此法生成订单");
                return info;
            }else {
                return purchaseCouponService.PurchaseControl(petrolInputDTO);
            }
        }else {
        //购油充值
            return PurchaseControl(petrolInputDTO);
        }
    }

    @Override
    public JSONObject WechatBuyPetrol(Petrol petrol, PetrolInputDTO petrolInputDTO) {

        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String orgId = WeChatUtils.getRandomStr();
        String nonceStrTemp = WeChatUtils.getRandomStr();
        User user = userMapper.selectByPrimaryKey(petrolInputDTO.getOwnerId());
        petrolInputDTO.setIsVip(user.getIsVip());
        VipAreaConfig vipAreaConfig = vipAreaConfigMapperExtra.selectVipAreaConfigByArea(petrolInputDTO.getArea());
        if(vipAreaConfig!=null){
            petrolInputDTO.setIsHaveVip(1);
        }
//        double money=backMoney(petrol,petrolInputDTO);
        Double money=petrolInputDTO.getPetrolPrice();
        // 设置参数
        SortedMap<String, Object> parameters =WeChatParameterConfig.getParameters(nonceStrTemp,orgId,money,petrolInputDTO,petrol);
        //插入购买信息o
        boolean insertPetrolSalesRecords= insertPetrolSalesRecords(petrol,petrolInputDTO,orgId);
        System.out.println("新增油卡购买或充值记录完毕"+insertPetrolSalesRecords);
        petrolInputDTO.setPetrolPrice(money);//返回实际支付金额
        return   WeChatParameterConfig.getSign( parameters, nonceStrTemp);
    }
    public double backMoney(Petrol petrol, PetrolInputDTO petrolInputDTO){
        double discount=1;
        Double money=0.0;
        if(petrolInputDTO.getPayType().equals("2")){//查出充值的折扣
            discount=appHomePageService.getDisCount(petrol.getRemark());
        }else {
            discount=petrol.getDiscount();
        }
        User user = userMapper.selectByPrimaryKey(petrolInputDTO.getOwnerId());
        VipAreaConfig vipAreaConfig = vipAreaConfigMapperExtra.selectVipAreaConfigByArea(petrolInputDTO.getArea());
        if (vipAreaConfig != null && user!=null&& user.getIsVip() == 1){
           if(discount==0){
               discount=1;
           }
            money=BigDecimal.valueOf(petrolInputDTO.getPetrolPrice()).multiply(BigDecimal.valueOf(discount)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        }else {
            money = petrolInputDTO.getPetrolPrice();
        }
        return money;
    }

    @Override
    public String AlipayBuyPetrol(Petrol petrol, PetrolInputDTO petrolInputDTO) {
        //检验是否都为空
        if (petrol == null && petrolInputDTO == null)
            return "没有油卡或传入数据有误（为空）";
        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String orderString = null;//用于保存起调参数,
        AlipayClientConfig alipayClientConfig = AlipayClientConfig.getInstance(petrolInputDTO.getPayType(),petrolInputDTO.getIsSpecial());//"0"代表的是购油
        AlipayClient alipayClient = alipayClientConfig.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //来源合同——合同id
        String contractId=petrolInputDTO.getContractId();
        //订单标识
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        //支付类型
        String payType =petrolInputDTO.getPayType();
        //支付的金额
//        Double money=backMoney( petrol,petrolInputDTO);
        Double money=petrolInputDTO.getPetrolPrice();
        //地区
        String area=petrolInputDTO.getArea();
        //购买的油卡类型
        Integer petrolKind = petrol.getPetrolKind();
        //购买的油卡号
        String petrolNum = petrol.getPetrolNum();
        //购买人的id
        String ownerId = petrol.getOwnerId();

        request.setBizModel(AliParameterConfig.getBizModel(area,orgId, payType,contractId ,money,
                                                                             petrolKind, ownerId, petrolNum,
                                                                            petrolInputDTO.getAddressId(),petrolInputDTO.getIsSpecial()));//支付订单
        request.setNotifyUrl(AliPayConfig.PetrolRecharge_url);//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //插入购买信息
        boolean insertPetrolSalesRecords= insertPetrolSalesRecords(petrol,petrolInputDTO,orgId);
        System.out.println("新增油卡购买或充值记录完毕"+insertPetrolSalesRecords);
        return orderString;
    }

    @Override
    public boolean isTodayHadBuy(PetrolInputDTO petrolInputDTO) {
        PetrolSalesRecords petrolSalesRecords=petrolSalesRecordsMapperExtra.selectPetrolSalesRecords(petrolInputDTO);
        if(petrolSalesRecords==null||petrolSalesRecords.getCreateAt()==null)
        {
            System.out.println("今日是否买过油卡"+petrolSalesRecords==null);
            return false;
        }
        else
        {
            Date date = new Date();
            DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
            if(df1.format(petrolSalesRecords.getCreateAt()).equals(df1.format(date))){
                System.out.println("今日已经购买了油卡,或充值了一次，请明天再来");
                return true;
            }
            else {
                System.out.println("今日没有购买油卡");
                return false;
            }
        }
    }

    @Override
    public boolean isBuyRepeat(PetrolInputDTO petrolInputDTO) {
        if(petrolMapperExtra.selectDifferentPetrol(petrolInputDTO)==null)
            return false;
        else
            return true;
    }

    @Override
    public Map<String,Object> PurchaseControl(PetrolInputDTO petrolInputDTO) {
       //判断是否为vip
        User user=userMapper.selectByPrimaryKey(petrolInputDTO.getOwnerId());
        if(user.getIsVip()==0){
            Map<String,Object> info=new HashMap<>();
            info.put("-1","不是vip");
            return info;
        }
        //判断今日是否购买过油卡is_continuous_buy
        Dict dict=dictMapperExtra.selectDictByName("is_continuous_buy");
        if(dict.getContent().equals("否")){
            Boolean res= isTodayHadBuy(petrolInputDTO);
            if(res==true){
                Map<String,Object> info=new HashMap<>();
                info.put("-1","每日购油只限一次");
                return info;
            }
        }

        if(StringUtil.isNullOrEmpty(petrolInputDTO.getArea())) {
            petrolInputDTO.setArea("重庆市");
        }
        //判断是哪种油卡
        if(petrolInputDTO.getPetrolKind()==0){//0代表国通卡
            System.out.println("购买国通卡：0");
            Petrol petrol=PetrolCache.randomPetrol(petrolInputDTO); //随机获取一张卡
            if(petrol==null)
            {
                Map<String,Object> info=new HashMap<>();
                info.put("-1","油卡申请失败，无此类油卡");
                return info;
            }
            petrolInputDTO.setPayType("0");//0为购买油卡，2为充值
            String buyPetrol=null;//用于保存生成的订单串
            JSONObject WeChatPayOrder=null;//用于保存微信生成的订单
            if(petrolInputDTO.getPaymentMethod()==1){//支付宝支付
                buyPetrol=AlipayBuyPetrol(petrol,petrolInputDTO);//返回生成的支付单串
            }else if(petrolInputDTO.getPaymentMethod()==2){//微信支付
                WeChatPayOrder=WechatBuyPetrol(petrol,petrolInputDTO);//返回生成的支付单串
            }
            if(buyPetrol!=null){
                Map<String,Object> info=new HashMap<>();
                AliPetrolBackInfoDTO petrolBackInfoDTO=new AliPetrolBackInfoDTO();
                petrolBackInfoDTO.setPaymentOrder(buyPetrol);
                Petrol petrol1=PetrolInfo.getNewPetrol(petrol);
                petrol1.setPetrolPrice(petrolInputDTO.getPetrolPrice());
                petrolBackInfoDTO.setPetrol(petrol1);
                info.put("0",petrolBackInfoDTO);
                return info;
            }else if(WeChatPayOrder!=null){
                Map<String,Object> info=new HashMap<>();
                WeChatPetrolBackInfoDTO weChatPetrolBackInfoDTO=new WeChatPetrolBackInfoDTO();
                weChatPetrolBackInfoDTO.setWeChatPaymentOrder(WeChatPayOrder);
                Petrol petrol1=PetrolInfo.getNewPetrol(petrol);
                petrol1.setPetrolPrice(petrolInputDTO.getPetrolPrice());
                weChatPetrolBackInfoDTO.setPetrol(petrol1);
                info.put("0",weChatPetrolBackInfoDTO);
                return info;
            }else {
                    Map<String,Object> info=new HashMap<>();
                    info.put("-1","油卡申请失败，信息有误，无此法生成订单");
                    return info;
            }
        }else if(petrolInputDTO.getPetrolKind()==1||petrolInputDTO.getPetrolKind()==2){
            System.out.println("购买中石油1或中石化2："+petrolInputDTO.getPetrolKind());
            //判断是否已经买了相应的卡
            Petrol petrol1= petrolMapperExtra.selectDifferentPetrol(petrolInputDTO);
            if(petrol1==null){//为空则没有购买过相应的卡————执行购买油卡
                //随机产生相应的卡
                Petrol petrol2=PetrolCache.randomPetrol(petrolInputDTO); //随机获取一张卡
                if(petrol2==null)
                {
                    Map<String,Object> info=new HashMap<>();
                    info.put("-1","油卡申请失败，无此类油卡");
                    return info;
                }
                petrolInputDTO.setPayType("0");//0为购买油卡，2为充值
                String buyPetrol=null;//用于保存生成的订单串
                JSONObject WeChatPayOrder=null;//用于保存微信生成的订单
                if(petrolInputDTO.getPaymentMethod()==1){//支付宝支付
                    buyPetrol=AlipayBuyPetrol(petrol2,petrolInputDTO);//返回生成的支付单串
                }else if(petrolInputDTO.getPaymentMethod()==2){//微信支付
                    WeChatPayOrder=WechatBuyPetrol(petrol2,petrolInputDTO);//返回生成的支付单串
                }

                if(buyPetrol!=null){
                    Map<String,Object> info=new HashMap<>();
                    AliPetrolBackInfoDTO petrolBackInfoDTO=new AliPetrolBackInfoDTO();
                    petrolBackInfoDTO.setPaymentOrder(buyPetrol);
                    Petrol petrol=PetrolInfo.getNewPetrol(petrol2);
                    petrol.setPetrolPrice(petrolInputDTO.getPetrolPrice());
                    petrolBackInfoDTO.setPetrol(petrol);
                    info.put("0",petrolBackInfoDTO);
                    return info;
                }else if(WeChatPayOrder!=null){
                    Map<String,Object> info=new HashMap<>();
                    WeChatPetrolBackInfoDTO weChatPetrolBackInfoDTO=new WeChatPetrolBackInfoDTO();
                    weChatPetrolBackInfoDTO.setWeChatPaymentOrder(WeChatPayOrder);
                    Petrol petrol=PetrolInfo.getNewPetrol(petrol2);
                    petrol.setPetrolPrice(petrolInputDTO.getPetrolPrice());
                    weChatPetrolBackInfoDTO.setPetrol(petrol);
                    info.put("0",weChatPetrolBackInfoDTO);
                    return info;
                }else {
                    Map<String,Object> info=new HashMap<>();
                    info.put("-1","油卡申请失败，信息有误，无此法生成订单");
                    return info;
                }
            }else{//不为空则————执行充值
                petrolInputDTO.setPayType("2");//0为购买油卡，2为充值
                String buyPetrol=null;//用于保存生成的订单串
                JSONObject WeChatPayOrder=null;//用于保存微信生成的订单
                if(petrolInputDTO.getPaymentMethod()==1){//支付宝支付
                    buyPetrol=AlipayBuyPetrol(petrol1,petrolInputDTO);//返回生成的支付单串
                }else if(petrolInputDTO.getPaymentMethod()==2){//微信支付
                    WeChatPayOrder=WechatBuyPetrol(petrol1,petrolInputDTO);//返回生成的支付单串
                }

                if(buyPetrol!=null){
                    Map<String,Object> info=new HashMap<>();
                    AliPetrolBackInfoDTO petrolBackInfoDTO=new AliPetrolBackInfoDTO();
                    petrolBackInfoDTO.setPaymentOrder(buyPetrol);
                    Petrol newPetrol=PetrolInfo.getNewPetrol(petrol1);
                    newPetrol.setPetrolPrice(petrolInputDTO.getPetrolPrice());
                    petrolBackInfoDTO.setPetrol(newPetrol);
                    info.put("2",petrolBackInfoDTO);
                    return info;
                }else if(WeChatPayOrder!=null){
                    Map<String,Object> info=new HashMap<>();
                    WeChatPetrolBackInfoDTO weChatPetrolBackInfoDTO=new WeChatPetrolBackInfoDTO();
                    weChatPetrolBackInfoDTO.setWeChatPaymentOrder(WeChatPayOrder);
                    Petrol newPetrol=PetrolInfo.getNewPetrol(petrol1);
                    newPetrol.setPetrolPrice(petrolInputDTO.getPetrolPrice());
                    weChatPetrolBackInfoDTO.setPetrol(newPetrol);
                    info.put("0",weChatPetrolBackInfoDTO);
                    return info;
                }else {
                    Map<String,Object> info=new HashMap<>();
                    info.put("-1","油卡申请失败，信息有误，无此法生成订单");
                    return info;
                }
            }

        }
        return null;
    }

    @Override
    public boolean insertPetrolSalesRecords(Petrol petrol, PetrolInputDTO petrolInputDTO, String orgId) {
        PetrolSalesRecords petrolSalesRecords=new PetrolSalesRecords();
        petrolSalesRecords.setPetrolId(petrol.getPetrolId());
        petrolSalesRecords.setBuyerId(petrol.getOwnerId());
        petrolSalesRecords.setPaymentMethod(petrolInputDTO.getPaymentMethod());//1为支付宝支付
        petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());//油卡种类
        petrolSalesRecords.setPetrolNum(petrol.getPetrolNum());//卡号
        petrolSalesRecords.setRecordId(orgId);
        petrolSalesRecords.setState(0);//1为已支付
        petrolSalesRecords.setTurnoverAmount(petrolInputDTO.getPetrolPrice());
        petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());
        petrolSalesRecords.setCurrentPrice(petrolInputDTO.getPetrolPrice());//售价
        petrolSalesRecords.setDenomination(petrolInputDTO.getPetrolPrice());//面额
        if(petrolInputDTO.getPayType()=="0"){//0为普通购买
            petrolSalesRecords.setRecordType(0);
        }else {
            petrolSalesRecords.setRecordType(1);
        }
        petrolSalesRecords.setIsRecharged(0);
        boolean insertPetrolSalesRecords=petrolSalesRecordsMapperExtra.insert(petrolSalesRecords)>0;
        return insertPetrolSalesRecords;
    }


}
