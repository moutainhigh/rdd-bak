package com.cqut.czb.bn.service.impl.autoRecharge;

import com.cqut.czb.bn.entity.dto.directChargingSystem.ThirdOrder;
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

    public static String stringToMD5(String string) {
        return MD5Util.MD5Encode(string,"UTF-8").toUpperCase();
    }

    public static void main(String[] args) {
        ThirdOrder order = new ThirdOrder();
        order.setUserId("1174429240086553331");
        order.setAmount(500d);
        order.setOrderId("111");
        order.setCard("111");
        order.setPhone("111");
        order.setNotifyUrl("111");
        order.setSign("111");
        Map<String, Object> map = new TreeMap<>();
        map.put("userId", order.getUserId());
        map.put("orderId", order.getOrderId());
        map.put("phone", order.getPhone());
        map.put("card", order.getCard());
        map.put("amount", order.getAmount().intValue());
        map.put("notifyUrl", order.getNotifyUrl());
        String key = stringToMD5("1174429240086553331");
        System.out.println(sign(key, map));
    }
}
