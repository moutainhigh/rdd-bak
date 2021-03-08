package com.cqut.czb.bn.service.impl.equityPaymentServiceImpl;

import com.cqut.czb.bn.entity.dto.PayConfig.UrlConfig;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.util.md5.MD5Util;

import java.net.InetAddress;
import java.util.Date;

import static com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.RequestLuPayServiceImpl.getNowDate;

/**
 * @author Liyan
 */
public class EquityPaymentThirdImpl {
    public static JSONResult<String> videoCharge(EquityPaymentDTO equityPayment){
        String URL="http://open.jiaofei100.com/Api/PayVideo.aspx";
        //人多多的订单号（由我方生成）
        String orderId = equityPayment.getOrderId();
        // 充值账号
        String account = equityPayment.getAccount();
        // 商品编号
        String goodsId = equityPayment.getGoodsId();
        Integer buyNum = equityPayment.getBuyNum();
        Integer isCallBack = equityPayment.getIsCallBack();
        // appId
        String apiId = "20201605199061";
        String appKey = "124FEE100E3FAE06BBEF09A59C72E5CD";

        String params = "APIID=" + apiId +
                "&Account=" + account +
                "&BuyNum=" + buyNum +
                "&CreateTime=" + getNowDate(new Date()) +
                "&IsCallBack=" + isCallBack +
                "&OrderID=" + orderId +
                "&ProductCode=" + goodsId;

        // sign
        String sign = MD5Util.MD5Encode(params + "&APIKEY=" + appKey,"UTF-8").toUpperCase();
        //设置请求参数
        params = params + "&Sign=" + sign;
        System.out.println(params);
        String sr= HttpRequest.httpRequestGet(URL, params);
        return new JSONResult<String>(sr);
    }

    public static JSONResult<String> gameCharge(EquityPaymentDTO equityPayment){
        String URL="http://open.jiaofei100.com/Api/PayGame.aspx";

        String apiId = "20201605199061";
        Integer tradeType = equityPayment.getTradeType();
        String account = equityPayment.getAccount();
        double unitPriceDouble = equityPayment.getUnitPrice() * 1000;
        int unitPrice = (int)(unitPriceDouble * 1000);

        Integer buyNum = equityPayment.getBuyNum();
        int totalPrice = unitPrice * buyNum;
        String orderId = equityPayment.getOrderId();
        String createTime = getNowDate(new Date());
        Integer isCallBack = equityPayment.getIsCallBack();
        String goodsId = equityPayment.getGoodsId();
        String clientIP = "";
        if (equityPayment.getClientIP() == null || "".equals(equityPayment.getClientIP())){
            clientIP = UrlConfig.NOTIGY_URL;
        }else{
            clientIP = equityPayment.getClientIP();
        }

        String params = "APIID=" + apiId +
                "&Account=" + account +
                "&BuyNum=" + buyNum +
                "&ClientIP=" + clientIP +
                "&CreateTime=" + createTime +
                "&GoodsID=" + goodsId +
                "&isCallBack=" + isCallBack +
                "&OrderID=" + orderId +
                "&TotalPrice=" + totalPrice +
                "&TradeType=" + tradeType +
                "&UnitPrice=" + unitPrice;

        String appKey = "124FEE100E3FAE06BBEF09A59C72E5CD";

        // sign
        String sign = MD5Util.MD5Encode(params + "&APIKEY=" + appKey,"UTF-8").toUpperCase();

        //设置请求参数
        params = params + "&Sign=" + sign;

        System.out.println(params);

        String sr= HttpRequest.httpRequestGet(URL, params);

        return new JSONResult<String>(sr);
    }
}
