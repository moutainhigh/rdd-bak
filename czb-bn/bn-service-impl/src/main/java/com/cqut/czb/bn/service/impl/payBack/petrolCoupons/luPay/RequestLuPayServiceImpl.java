package com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay;

import com.cqut.czb.bn.dao.mapper.petrolCoupons.PetrolCouponsSalesRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.paymentCallBack.AliPetrolCouponsDTO;
import com.cqut.czb.bn.entity.entity.petrolCoupons.PetrolCouponsSalesRecords;
import com.cqut.czb.bn.service.PaymentProcess.RequestLuPayService;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.LuPayApiConfig;
import com.cqut.czb.bn.util.md5.MD5Util;
import com.cqut.czb.bn.util.string.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class RequestLuPayServiceImpl implements RequestLuPayService {

    @Autowired
    PetrolCouponsSalesRecordsMapperExtra extra;

    @Override
    public String httpRequestGETLuPay(AliPetrolCouponsDTO petrolCouponsDTO){

        String URL="http://jiayou.10010.wiki/Api/PayGame.aspx";
        //人多多的订单号（由我方生成）
        String OrderID= StringUtil.createId();
        //修改时间
        Date CreateTime=new Date();
        //金额不能为浮点数
        Integer money= BigDecimal.valueOf(petrolCouponsDTO.getPetrolPrice()).
                multiply(BigDecimal.valueOf(1000)).
                setScale(2,BigDecimal.ROUND_HALF_UP).intValue();
        String string="APIID="+ LuPayApiConfig.APIID+
                "&Account="+ petrolCouponsDTO.getUserAccount()+
                "&BuyNum="+ LuPayApiConfig.BuyNum+
                "&ClientIP="+ LuPayApiConfig.ClientIP+
                "&CreateTime="+ getNowDate(CreateTime)+
                "&GoodsID="+ petrolCouponsDTO.getPetrolNum()+
                "&isCallBack="+ LuPayApiConfig.IsCallBack+
                "&OrderID="+OrderID+
                "&TotalPrice="+ money+
                "&TradeType="+ LuPayApiConfig.TradeType+
                "&UnitPrice="+ money+
                "&APIKEY="+ LuPayApiConfig.APIKEY;

        String Sign= MD5Util.MD5Encode(string,"UTF-8").toUpperCase();

        //设置请求参数
        String params=
                "APIID=" + LuPayApiConfig.APIID+
                "&Account=" + petrolCouponsDTO.getUserAccount() +
                "&BuyNum=" + LuPayApiConfig.BuyNum +
                "&ClientIP=" + LuPayApiConfig.ClientIP+
                "&CreateTime=" + getNowDate(CreateTime)+
                "&GoodsID=" + petrolCouponsDTO.getPetrolNum() +
                "&isCallBack=" + LuPayApiConfig.IsCallBack +
                "&OrderID=" + OrderID+
                "&TotalPrice=" + money+
                "&TradeType=" + LuPayApiConfig.TradeType+
                "&UnitPrice=" + money+
                "&Sign=" + Sign;

        //开始请求
        String sr= HttpRequest.httpRequestGet(URL, params);
        System.out.println(sr);
        JSONObject jsonObject=JSONObject.fromObject(sr);
        System.out.println("起吊璐付接口打印");
        System.out.println(sr);
        if(jsonObject!=null){
            if(jsonObject.get("Code").equals("10018")) {
                if (jsonObject.get("OrderID") != null) {
                    if (((String)jsonObject.get("OrderID")).equals(OrderID)) {
                        //修改数据：
                        this.updatePetrolSaleRecords(
                                (String) jsonObject.get("ReturnOrderID"),
                                (String) jsonObject.get("TradingID"),
                                petrolCouponsDTO.getPetrolPrice(),
                                CreateTime,
                                OrderID,
                                petrolCouponsDTO.getOrgId()
                        );
                    }
                }
            }
        }
        return sr;
    }

    //获取当前时间
    public static String getNowDate(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    //xiu改订单
    public  void updatePetrolSaleRecords(String TradingID, String ReturnOrderID,Double unitPrice,Date startTime,String OrderID,String orgId){
        PetrolCouponsSalesRecords records=new PetrolCouponsSalesRecords();
        records.setRecordId(orgId);
        records.setUnitPrice(unitPrice);
        records.setToLuPayStartTime(startTime);
        records.setToRddOutId(OrderID);
        records.setReturnOrderId(ReturnOrderID);
        records.setTradingId(TradingID);
        records.setToLuPayState(0);
        int is= extra.updateByPrimaryKeySelective(records);
        System.out.println("插入璐付起吊信息"+(is>0));
    }

    public static String testHttpRequestGETLuPay(){

        String URL="http://jiayou.10010.wiki/Api/PayGame.aspx";
        String param="";
        //充值号码
        String Account="";
        //该商品的面值 单位：厘(不允许小数点)(Q 币固定值 1000)
        Integer UnitPrice=0;
        //人多多的订单号（由我方生成）
        String OrderID= StringUtil.createId();
        Date CreateTime=new Date();
        Integer money= BigDecimal.valueOf(100.00).
                multiply(BigDecimal.valueOf(100)).
                setScale(2,BigDecimal.ROUND_HALF_UP).intValue();
        String string="APIID="+ LuPayApiConfig.APIID+
                "&Account="+"15730353007"+
                "&BuyNum="+1+
                "&ClientIP="+ LuPayApiConfig.ClientIP+
                "&CreateTime="+"20200429112634"+
                "&GoodsID="+ LuPayApiConfig.GoodsID+
                "&isCallBack="+ LuPayApiConfig.IsCallBack+
                "&OrderID="+"23581602782770080"+
                "&TotalPrice="+10000+
                "&TradeType="+ LuPayApiConfig.TradeType+
                "&UnitPrice="+10000+
                "&APIKEY="+ LuPayApiConfig.APIKEY;
        System.out.println(string);
        String Sign= MD5Util.MD5Encode(string,"UTF-8").toUpperCase();

        //设置请求参数
        String params=
                "APIID=" + LuPayApiConfig.APIID+
                        "&Account=" +"15730353007"+
                        "&BuyNum=" + LuPayApiConfig.BuyNum +
                        "&ClientIP=" + LuPayApiConfig.ClientIP+
                        "&CreateTime=" +"20200429112634"+
                        "&GoodsID=" + LuPayApiConfig.GoodsID +
                        "&isCallBack=" + LuPayApiConfig.IsCallBack +
                        "&OrderID=" + "23581602782770080"+
                        "&TotalPrice=" + 10000+
                        "&TradeType=" + LuPayApiConfig.TradeType+
                        "&UnitPrice=" + 10000+
                        "&Sign=" + Sign;
        System.out.println(params);
        //开始请求
        String sr= HttpRequest.httpRequestGet(URL, params);
        System.out.println(sr);
        return sr;
    }



}
