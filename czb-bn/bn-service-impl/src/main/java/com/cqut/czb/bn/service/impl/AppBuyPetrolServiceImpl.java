package com.cqut.czb.bn.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.AliPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolSalesRecordsDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.WeChatPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.dto.paymentRecord.HttpUtil;
import com.cqut.czb.bn.entity.dto.paymentRecord.WXPayConfig;
import com.cqut.czb.bn.entity.dto.paymentRecord.WXUtils;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.entity.dto.paymentRecord.GetAlipayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.*;

@Service
public class AppBuyPetrolServiceImpl implements AppBuyPetrolService {

    private String characterEncoding = "UTF-8";

    @Autowired
    private PetrolSalesRecordsMapperExtra petrolSalesRecordsMapperExtra;

    @Autowired
    private PetrolMapperExtra petrolMapperExtra;

    @Override
    public JSONObject WechatBuyPetrol(Petrol petrol, PetrolInputDTO petrolInputDTO) {

        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String rs = null;//用于保存起调参数,
        String orgId = WXUtils.getRandomStr();
        // 设置参数
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = new PetrolSalesRecordsDTO();
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("appid", WXPayConfig.app_id);
        parameters.put("mch_id", WXPayConfig.mch_id);
        parameters.put("device_info", WXPayConfig.device_info);
        String nonceStrTemp = WXUtils.getRandomStr();
        parameters.put("nonce_str", nonceStrTemp);
        parameters.put("sign_type", WXPayConfig.sign_type);
        parameters.put("body", WXPayConfig.body);
        parameters.put("out_trade_no", orgId);
        BigInteger totalFee = BigDecimal.valueOf(petrolInputDTO.getPetrolPrice()).multiply(new BigDecimal(100))
                .toBigInteger();
        System.out.println(totalFee);
        parameters.put("total_fee", totalFee);
        parameters.put("spbill_create_ip", WXPayConfig.spbill_create_ip);
        parameters.put("notify_url", WXPayConfig.notify_url);//通用一个接口（购买和充值）
        parameters.put("trade_type", WXPayConfig.trade_type);
        parameters.put("detail","微信支付购买油卡");//支付的类容备注
        String k=petrolSalesRecordsDTO.getWeChatPassbackParams(
                orgId,petrolInputDTO.getPayType(),
                petrolInputDTO.getOwnerId(),
                petrol.getPetrolNum(),petrolInputDTO.getAddressId());
//        parameters.put("attach", "payType'0^petrolNum'2019053127^^ownerId'155786053583269^addressId'155887601466326^orgId'GM6X9A7AMC3471XDZP6VUQESZZU6DQB8");
        parameters.put("attach",k);
        parameters.put("sign", WXUtils.createSign(characterEncoding, parameters));//编码格式
        // 转为xml格式
        String info = WXUtils.map2xml(parameters);
        rs = HttpUtil.httpsRequest(WXPayConfig.URL, "POST", info);
        System.out.println(rs);
        // 获取xml结果转换为jsonobject
        Map<String, Object> result = new TreeMap<String, Object>();
        result = WXUtils.xml2Map(rs);
        JSONObject jo = (JSONObject) JSONObject.toJSON(result);

        // 生成调起支付sign
        SortedMap<String, Object> signParam = new TreeMap<String, Object>();
        signParam.put("appid", jo.getString("appid"));
        signParam.put("partnerid", jo.getString("mch_id"));
        signParam.put("prepayid", jo.getString("prepay_id"));
        signParam.put("packageValue", "Sign=WXPay");
        signParam.put("noncestr", nonceStrTemp);
        String a = System.currentTimeMillis() + "";
        signParam.put("timestamp", a.substring(0, 10));
        signParam.put("sign", WXUtils.createSign(characterEncoding, signParam));
        JSONObject joo = (JSONObject) JSONObject.toJSON(signParam);
        return joo;
    }

    @Override
    public String AlipayBuyPetrol(Petrol petrol, PetrolInputDTO petrolInputDTO) {
        //检验是否都为空
        if (petrol == null && petrolInputDTO == null)
            return "没有油卡或传入数据有误（为空）";
        /**
         * 生成起调参数串——返回给app（支付宝的支付订单）
         */
        String rs = null;//用于保存起调参数,
        GetAlipayClient getAlipayClient = GetAlipayClient.getInstance(petrolInputDTO.getPayType());//"0"代表的是购油
        AlipayClient alipayClient = getAlipayClient.getAlipayClient();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //来源合同——合同id
        String contractId=petrolInputDTO.getContractId();
        System.out.println("contractId" + contractId);
        //订单标识
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        System.out.println("orgId" + orgId);
        //支付类型
        String payType =petrolInputDTO.getPayType();
        //支付的金额
        Double money = petrol.getPetrolPrice();
        System.out.println("money" + money);
        //购买的油卡类型
        Integer petrolKind = petrol.getPetrolKind();
        System.out.println("petrolKind" + petrolKind);
        //购买的油卡号
        String petrolNum = petrol.getPetrolNum();
        System.out.println("petrolNum" + petrolNum);
        //购买人的id
        String ownerId = petrol.getOwnerId();
        System.out.println("ownerId" + ownerId);

        //对判断是否能生成订单
        if (orgId == null || payType == null || money == null || petrolKind == null || ownerId == null || petrolNum == null) {
            return "无法生成支付订单";
        }
        PetrolSalesRecordsDTO petrolSalesRecordsDTO = new PetrolSalesRecordsDTO();
        request.setBizModel(petrolSalesRecordsDTO.toAlipayTradeAppPayModel(orgId, payType,contractId ,money,
                                                                             petrolKind, ownerId, petrolNum,
                                                                            petrolInputDTO.getAddressId()));//支付订单

        request.setNotifyUrl(getAlipayClient.getCallBackUrl());//支付回调接口
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            rs = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //插入购买信息
        PetrolSalesRecords petrolSalesRecords=new PetrolSalesRecords();
        petrolSalesRecords.setPetrolId(petrol.getPetrolId());
        petrolSalesRecords.setBuyerId(ownerId);
        petrolSalesRecords.setPaymentMethod(petrolInputDTO.getPaymentMethod());//1为支付宝支付
        petrolSalesRecords.setPetrolKind(petrolKind);//油卡种类
        petrolSalesRecords.setPetrolNum(petrolNum);//卡号
        petrolSalesRecords.setRecordId(orgId);
        petrolSalesRecords.setState(0);//1为已支付
        petrolSalesRecords.setTurnoverAmount(petrol.getPetrolPrice());
        petrolSalesRecords.setPetrolKind(petrol.getPetrolKind());
        if(petrolInputDTO.getPayType()=="0"){//0为普通购买
            petrolSalesRecords.setRecordType(0);
        }else {
            petrolSalesRecords.setRecordType(1);
        }
        petrolSalesRecords.setIsRecharged(0);
        petrolSalesRecords.setContractId(contractId);
        boolean insertPetrolSalesRecords=petrolSalesRecordsMapperExtra.insert(petrolSalesRecords)>0;
        System.out.println("新增油卡购买或充值记录完毕"+insertPetrolSalesRecords);
        return rs;
    }

    @Override
    public boolean isTodayHadBuy(PetrolInputDTO petrolInputDTO) {
        PetrolSalesRecords petrolSalesRecords=petrolSalesRecordsMapperExtra.selectPetrolSalesRecords(petrolInputDTO);
        if(petrolSalesRecords==null||petrolSalesRecords.getTransactionTime()==null)
        {
            System.out.println("今日是否买过油卡"+petrolSalesRecords==null);
            return false;
        }
        else
        {
            Date date = new Date();
            DateFormat df1 = DateFormat.getDateInstance();//日期格式，精确到日
            if(df1.format(petrolSalesRecords.getTransactionTime()).equals(df1.format(date))){
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
                petrolBackInfoDTO.setPetrol(petrol);
                info.put("0",petrolBackInfoDTO);
                return info;
            }else if(WeChatPayOrder!=null){
                Map<String,Object> info=new HashMap<>();
                WeChatPetrolBackInfoDTO weChatPetrolBackInfoDTO=new WeChatPetrolBackInfoDTO();
                weChatPetrolBackInfoDTO.setWeChatPaymentOrder(WeChatPayOrder);
                weChatPetrolBackInfoDTO.setPetrol(petrol);
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
                    petrolBackInfoDTO.setPetrol(petrol2);
                    info.put("0",petrolBackInfoDTO);
                    return info;
                }else if(WeChatPayOrder!=null){
                    Map<String,Object> info=new HashMap<>();
                    WeChatPetrolBackInfoDTO weChatPetrolBackInfoDTO=new WeChatPetrolBackInfoDTO();
                    weChatPetrolBackInfoDTO.setWeChatPaymentOrder(WeChatPayOrder);
                    weChatPetrolBackInfoDTO.setPetrol(petrol2);
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
                    petrolBackInfoDTO.setPetrol(petrol1);
                    info.put("2",petrolBackInfoDTO);
                    return info;
                }else if(WeChatPayOrder!=null){
                    Map<String,Object> info=new HashMap<>();
                    WeChatPetrolBackInfoDTO weChatPetrolBackInfoDTO=new WeChatPetrolBackInfoDTO();
                    weChatPetrolBackInfoDTO.setWeChatPaymentOrder(WeChatPayOrder);
                    weChatPetrolBackInfoDTO.setPetrol(petrol1);
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
    public boolean insertPetrolSalesRecords(PetrolSalesRecords petrolSalesRecords) {
        return false;
    }

}
