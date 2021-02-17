package com.cqut.czb.bn.service.impl.paymentNewServiceImpl.payBackImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cqut.czb.bn.entity.dto.PayConfig.AliPayH5Config;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatFileUtil;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;
import com.cqut.czb.bn.service.paymentNew.paybackService.OrderPaymentService;
import com.cqut.czb.bn.service.paymentNew.paybackService.PayBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("OrderPaymentService")
public class OrderPaymentServiceImpl implements OrderPaymentService {

    @Autowired
    PayBackService payBackService;

    @Override
    public String AliOrderPaymentNotify(HttpServletRequest request, String consumptionType) {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        params=parseOrder(params,requestParams);
        //是否被篡改的标识
        boolean signVerfied = false;
        try {
            signVerfied = AlipaySignature.rsaCheckV1(params, AliPayH5Config.alipay_public_key,
                    AliPayH5Config.charset, AliPayH5Config.sign_type);
            System.out.println("408 " + signVerfied);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        try {
            if (signVerfied) {
//				支付账单是否一致
                if (isCorrectDataAiHu(params)) {//交易成功
                    Object[] param = { params };
                    Map result = payBackService.AliPayback(param,consumptionType);//1为支付宝支付（用于拓展）
                    if (AlipayConfig.response_success.equals(result.get("success"))) {
                        return AlipayConfig.response_success;
                    } else if (AlipayConfig.response_fail.equals(result.get("fail"))) {
                        return AlipayConfig.response_fail;
                    } else {
                        return null;
                    }
                } else {//交易失败
                    Object[] param = { params };
//                    refuelingCard.purchaseFailed(param);//油卡放回
                    return AlipayConfig.response_fail;
                }
            } else {
                System.out.println("被篡改"+AlipayConfig.response_fail);
                return AlipayConfig.response_fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("被篡改外"+AlipayConfig.response_fail);
        return AlipayConfig.response_fail;
    }

    private boolean isCorrectDataAiHu(Map<String, String> params) {

        System.out.println("675 " + params.get("app_id"));
        // 验证app_id是否一致
        if (!params.get("app_id").equals(AliPayH5Config.app_id)) {
            System.out.println("错误app_id:" + params.get("app_id"));
            return false;
        }
        // 判断交易状态是否为TRADE_SUCCESS
        if (!params.get("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("错误交易状态：" + params.get("trade_status"));
            return false;
        }
        return true;
    }
    //解析订单
    @Override
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

    @Override
    public String WeChatOrderPaymentNotify(HttpServletRequest request, String consumptionType) {
        try {
            ServletInputStream in = request.getInputStream();
            String resxml = WeChatFileUtil.inputStream2String(in);
            Map<String, Object> restmap = WeChatUtils.xml2Map(resxml);
            if ("SUCCESS".equals(restmap.get("result_code"))) {
                // 订单支付成功 业务处理
                if (checkSign(restmap)) {
                    // 进行业务处理
                    Object[] param = { restmap };
                    Map<String, Integer> result = payBackService.WeChatPayBack(param,consumptionType);
                    if (result.get("success") == 1) {
                        return getSuccess();
                    } else {
                        return AlipayConfig.response_fail;
                    }
                } else {
                    return AlipayConfig.response_fail;
                }
            } else {
                return AlipayConfig.response_fail;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getSuccess();
        }
    }

    // 微信异步通知成功
    public String getSuccess() {
        SortedMap<String, Object> respMap = new TreeMap<>();
        respMap = new TreeMap<String, Object>();
        respMap.put("return_code", "SUCCESS"); // 响应给微信服务器
        respMap.put("return_msg", "OK");
        String resXml = WeChatUtils.map2xml(respMap);
        return resXml;
    }

    // 验证签名（微信）
    public boolean checkSign(Map<String, Object> restmap) {
        String sign = (String) restmap.get("sign"); // 返回的签名
        restmap.remove("sign");
        SortedMap<String, Object> sortedMap = new TreeMap<String, Object>();
        for (Map.Entry<String, Object> entry : restmap.entrySet()) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        //爱动key
        String signNow = WeChatUtils.createSign("UTF-8", sortedMap);
        //人多多key
        String signNowRdd = WeChatUtils.createRddSign("UTF-8", sortedMap);
        if (sign.equals(signNow)||sign.equals(signNowRdd)) {
            return true;
        } else {
            return false;
        }
    }
}
