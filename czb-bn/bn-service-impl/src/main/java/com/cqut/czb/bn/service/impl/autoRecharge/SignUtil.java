package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.entity.dto.directChargingSystem.ThirdOrderCallback;
import com.cqut.czb.bn.util.md5.MD5Util;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {

    public static String sign(String key, Map<String, Object> map){
        StringBuilder string = new StringBuilder();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            if (!string.toString().equals("")) {
                string.append("&");
            }
            string.append(stringObjectEntry);
        }
        string.append("&key=").append(key);
        return stringToMD5(string.toString());
    }

    public static String signNoKey(Map<String, Object> map){
        StringBuilder string = new StringBuilder();
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            if (!string.toString().equals("")) {
                string.append("&");
            }
            string.append(stringObjectEntry);
        }
        return stringToMD5(string.toString());
    }

    public static String stringToMD5(String string) {
        return MD5Util.MD5Encode(string,"UTF-8").toUpperCase();
    }

    public static void main(String[] args) {
//        System.out.println(stringToMD5("209259892027422426"));
//        ThirdOrder order = new ThirdOrder();
//        order.setUserId("1174429240086553331");
//        order.setAmount(100d);
//        order.setOrderId("222");
//        order.setCard("111");
//        order.setPhone("13709428111");
//        order.setNotifyUrl("http://47.110.9.136:8090/oilCardRecharge/testCallBack");
//        Map<String, Object> map = new TreeMap<>();
//        map.put("userId", order.getUserId());
//        map.put("orderId", order.getOrderId());
//        map.put("phone", order.getPhone());
//        map.put("card", order.getCard());
//        map.put("amount", order.getAmount().intValue());
//        map.put("notifyUrl", order.getNotifyUrl());
//        String key = stringToMD5("1174429240086553331");
//        System.out.println(sign(key, map));
        ThirdOrderCallback back = new ThirdOrderCallback(200, "充值成功", "111");
        Map<String, Object> map = new TreeMap<>();
        map.put("code", 200);
        map.put("msg", "充值成功");
        map.put("orderId", "APPME20221129135938586964");
        System.out.println(signNoKey(map));
    }
}
