package com.cqut.czb.bn.service.impl.personCenterImpl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapper;
import com.cqut.czb.bn.dao.mapper.VipRechargeRecordsMapperExtra;
import com.cqut.czb.bn.entity.dto.appVIP.VipRechargeRecordDTO;
import com.cqut.czb.bn.entity.dto.paymentRecord.AiHuAlipayConfig;
import com.cqut.czb.bn.entity.dto.paymentRecord.GetAlipayClient;
import com.cqut.czb.bn.entity.entity.VipRechargeRecords;
import com.cqut.czb.bn.service.personCenterService.IVIPService;
import com.cqut.czb.bn.util.constants.SystemConstants;
import com.cqut.czb.bn.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class VIPServiceImpl implements IVIPService {

    @Autowired
    VipRechargeRecordsMapperExtra vipRechargeRecordsMapperExtra;

    @Autowired
    VipRechargeRecordsMapper vipRechargeRecordsMapper;

    @Override
    public String createVIPOrder(String userId) {
        VipRechargeRecordDTO vipRechargeRecordDTO = new VipRechargeRecordDTO();
        vipRechargeRecordDTO.setRecordId(StringUtil.createId());
        vipRechargeRecordDTO.setUserId(userId);
        vipRechargeRecordDTO.setRechargeWay(1);
        vipRechargeRecordDTO.setIsReceived(0);
        vipRechargeRecordDTO.setAccount(SystemConstants.VIP_PRICE);
        vipRechargeRecordsMapperExtra.insertVipRechargeRecord(vipRechargeRecordDTO);

        /**
         * 生成起调参数串
         */
        String rs=null;
        GetAlipayClient getAlipayClient=GetAlipayClient.getInstance();
        AlipayClient alipayClient=getAlipayClient.getAlipayClient();
        AlipayTradeAppPayRequest request=new AlipayTradeAppPayRequest();

        request.setBizModel(toAlipayTradeAppPayModel(vipRechargeRecordDTO.getRecordId()));
        request.setNotifyUrl(getAlipayClient.getCallBackUrl());

        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            rs = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public boolean purchaseVIP(HttpServletRequest request) {
        Map<String, String> params = convertRequestParamsToMap(request); // 将异步通知中收到的待验证所有参数都存放到map中
        boolean signVerified = false;
        try {
            // 调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key,
                    AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if (signVerified) {
            // 支付宝回调签名认证成功
            if(isCorrectData(params)) {
                VipRechargeRecords vipRechargeRecords = vipRechargeRecordsMapper.selectByPrimaryKey(params.get("recordId"));
                vipRechargeRecords.setIsReceived(1);
                return vipRechargeRecordsMapper.updateByPrimaryKeySelective(vipRechargeRecords) > 0;
            }
        }
        return false;
    }

    /**
     * 验证数据是否正确
     */
    private boolean isCorrectData(Map<String, String> params) {

        // 验证app_id是否一致
        if (!params.get("app_id").equals(AlipayConfig.app_id)) {
            return false;
        }

        // 判断交易状态是否为TRADE_SUCCESS
        if (!params.get("trade_status").equals("TRADE_SUCCESS")) {
            return false;
        }

        return true;
    }

    // 将request中的参数转换成Map
    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;
    }

    public String getPassbackParams(String recordId) {
        Map<String, Object> pbp = new HashMap<>();

        pbp.put("recordId", recordId);

        return StringUtil.transMapToStringOther(pbp);
    }


    /**
     * 转换为支付宝支付实体
     *
     * @return
     */
    public AlipayTradeAppPayModel toAlipayTradeAppPayModel(String orgId) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject("车租宝支付宝支付");
        model.setOutTradeNo(orgId);
        model.setTimeoutExpress(AiHuAlipayConfig.timeout_express);
//        turnoverAmount.toString()——死数据（方便测试）
        model.setTotalAmount("0.01");
        model.setProductCode(AiHuAlipayConfig.product_code);
        model.setPassbackParams(getPassbackParams(orgId));
        return model;
    }
}
