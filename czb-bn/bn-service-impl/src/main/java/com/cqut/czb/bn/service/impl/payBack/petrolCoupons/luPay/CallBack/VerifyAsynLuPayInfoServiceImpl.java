package com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.CallBack;

import com.cqut.czb.bn.service.ThirdBusinessService.ChangeOrderInfoService;
import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.LuPayApiConfig;
import com.cqut.czb.bn.service.thirdPartyCallBack.LuPay.petrolCoupons.VerifyAsynLuPayInfoService;
import com.cqut.czb.bn.util.md5.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class VerifyAsynLuPayInfoServiceImpl implements VerifyAsynLuPayInfoService {

    @Autowired
    ChangeOrderInfoService changeOrderInfoService;
    @Override
    public String VerifyInfo(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        params=parseOrder(params,requestParams);
        System.out.println(params);
        //订单交易成功状态码为10027
        if(!params.get("State").equals("10027")){
            System.out.println("判断状态failure");
            return "failure";
        }else {
            if(checkSign(params)){
                System.out.println("修改信息");
                System.out.println(params);
                changeOrderInfoService.updateOrderInfo(params);
                System.out.println("changeOrderInfoService.updateOrderInfo(params)success");
                return "success";
            }
        }
        return "failure";
    }

    public Map<String, String>  parseOrder(Map<String, String> params, Map requestParams){
        //解读相应的信息
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            if (name.equals("fund_bill_list")) {
                valueStr = valueStr.replace("&quot;", "\"");
            }
            params.put(name, valueStr);
        }
        return params;
    }


    public Boolean checkSign(Map<String, String> params ){

        String str="APIID=" +params.get("APIID") +
                "&Account=" +params.get("Account") +
                "&OrderID=" +params.get("OrderID") +
                "&OutID=" +params.get("OutID") +
                "&State=" +params.get("State") +
                "&TradeType=" +params.get("TradeType") +
                "&TotalPrice=" +params.get("TotalPrice") +
                "&APIKEY=" + LuPayApiConfig.APIKEY;

        String Sign= MD5Util.MD5Encode(str,"UTF-8").toUpperCase();
        System.out.println("Sign："+Sign+"  STR:"+str);
        //验证签名是否被篡改
        if(Sign.equals(params.get("Sign"))){
            System.out.println("true");
            return true;
        }

        return false;
    }
}
