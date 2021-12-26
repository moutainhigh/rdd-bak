package com.cqut.czb.bn.service.impl.personCenterImpl;

import com.cqut.czb.bn.entity.dto.PayConfig.WeChatH5PayConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class WechatParameterConfig {

    // 得到32位随机数
    public static String getRandomStr() {
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        String str = "QWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        for (int i = 0; i < 32; i++) {
            sb.append(str.charAt(r.nextInt(str.length())));
        }
        return sb.toString();
    }

    public static SortedMap<String, Object> getParametersWithDraw(String nonceStrTemp,Integer amount,String name,String orderId,String openId) {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters = getParameters();

        parameters.put("nonce_str",nonceStrTemp);
        parameters.put("amount",amount);//单位为分
        parameters.put("check_name","NO_CHECK");
        parameters.put("partner_trade_no",orderId);
        parameters.put("desc","RDD微信提现");
        parameters.put("openid",openId);
        parameters.put("sign", WeChatUtils.createH5Sign("UTF-8", parameters));//编码格式

        return parameters;
    }

    private static SortedMap<String, Object> getParameters() {
        SortedMap<String, Object> parameters = new TreeMap<String, Object>();
        parameters.put("mch_appid", WCProgramConfig.app_id);
        parameters.put("mchid", WCProgramConfig.mchid);
        return parameters;
    }
}
