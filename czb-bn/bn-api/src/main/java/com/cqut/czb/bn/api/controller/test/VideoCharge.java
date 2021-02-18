package com.cqut.czb.bn.api.controller.test;

import com.cqut.czb.bn.api.controller.test.model.GameChargeDTO;
import com.cqut.czb.bn.api.controller.test.model.VideoChargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.RequestLuPayServiceImpl.getNowDate;

/**
 * @author Liyan
 */
@RestController
@RequestMapping("/videoCharge")
public class VideoCharge {

    @GetMapping("/charge")
    public JSONResult<String> charge(VideoChargeDTO videoChargeDTO){
        String URL="http://open.jiaofei100.com/Api/PayVideo.aspx";
        //人多多的订单号（由我方生成）
        String orderId = videoChargeDTO.getOrderId();

        // 充值账号
        String account = videoChargeDTO.getAccount();

        // 商品编号
        String productCode = videoChargeDTO.getProductCode();

        Integer buyNum = videoChargeDTO.getBuyNum();

        Integer isCallBack = videoChargeDTO.getIsCallBack();

        // appId
        String appId = "20201605199061";

        String appKey = "124FEE100E3FAE06BBEF09A59C72E5CD";

        String params = "APIID=" + appId +
                "&Account=" + account +
                "&BuyNum=" + buyNum +
                "&CreateTime=" + getNowDate(new Date()) +
                "&IsCallBack=" + isCallBack +
                "&OrderID=" + orderId +
                "&ProductCode=" + productCode;

        // sign
        String sign = MD5Util.MD5Encode(params + "&APIKEY=" + appKey,"UTF-8").toUpperCase();

        //设置请求参数
        params = params + "&Sign=" + sign;

        System.out.println(params);

        String sr= HttpRequest.httpRequestGet(URL, params);

        return new JSONResult<String>(sr);
    }

    @GetMapping("/gameCharge")
    public JSONResult<String> gameCharge(GameChargeDTO gameChargeDTO){
        String URL="http://open.jiaofei100.com/Api/PayGame.aspx";

        String appId = "20201605199061";
        Integer tradeType = gameChargeDTO.getTradeType();
        String account = gameChargeDTO.getAccount();
        Integer unitPrice = gameChargeDTO.getUnitPrice();
        Integer buyNum = gameChargeDTO.getBuyNum();
        int totalPrice = unitPrice * buyNum;
        String orderId = gameChargeDTO.getOrderId();
        String createTime = getNowDate(new Date());
        Integer isCallBack = gameChargeDTO.getIsCallBack();
        String goodsId = gameChargeDTO.getGoodsId();
        String clientIP = "";
        try {
            clientIP = InetAddress.getLocalHost().getHostAddress();
        }catch (Exception e){

        }

        String params = "APIID=" + appId +
                "&Account=" + account +
                "&BuyNum=" + buyNum +
                "&ClientIP=" + clientIP +
                "&CreateTime=" + createTime +
                "&GoodsID=" + createTime +
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
