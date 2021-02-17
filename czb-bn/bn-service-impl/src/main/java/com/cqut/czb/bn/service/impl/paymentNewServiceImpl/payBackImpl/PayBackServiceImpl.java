package com.cqut.czb.bn.service.impl.paymentNewServiceImpl.payBackImpl;

import com.cqut.czb.bn.dao.mapper.integral.IntegralPurchaseMapperExtra;
import com.cqut.czb.bn.entity.dto.integral.IntegralLogDTO;
import com.cqut.czb.bn.entity.dto.integral.IntegralRechargeDTO;
import com.cqut.czb.bn.entity.entity.integral.IntegralPurchaseRecord;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;
import com.cqut.czb.bn.service.integral.IntegralService;
import com.cqut.czb.bn.service.paymentNew.paybackService.PayBackService;
import com.cqut.czb.bn.util.config.SendMesConfig.MesInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("PayBackService")
public class PayBackServiceImpl implements PayBackService {

    @Autowired
    IntegralPurchaseMapperExtra integralPurchaseMapperExtra;

    @Autowired
    public IntegralService integralService;

    @Override
    public Map AliPayback(Object[] param, String consumptionType) {
        // 支付宝支付
        Map<String, String> result = new HashMap<>();
        Map<String, String> params = (HashMap<String, String>) param[0];
        if(consumptionType.equals("Integral")){//购油
            if (getIntegralOrderAli(params) == 1) {
                result.put("success", AlipayConfig.response_success);
                return result;
            }
        }else {
            result.put("fail", AlipayConfig.response_fail);
            return result;
        }
        result.put("fail", AlipayConfig.response_fail);
        return result;
    }

    @Override
    public Map<String, Integer> WeChatPayBack(Object[] param, String consumptionType) {
        Map<String, Object> restmap = (HashMap<String, Object>) param[0];
        Map<String, Integer> result = new HashMap<>();
        if(consumptionType.equals("Integral")){
            result.put("success", getAddBuyIntegralOrderWechat(restmap));
        } else {
            result.put("fail",0);
        }
        return result;
    }

    private int getIntegralOrderAli(Map<String, String> params) {
        String[] resDate = params.get("passback_params").split("\\^");
        String[] temp;
        String thirdOrderId = params.get("trade_no");
        System.out.println("第三方订单" + params.get("trade_no"));
        String orgId = "";
        String userId = "";
        double amount = 0;
        int integralAmount = 0;
        String ownerId = "";
        double actualPayment = 0;

        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("trade_no".equals(temp[0])) {
                System.out.println("该交易在支付宝系统中的交易流水号:" + temp[1]);
            }
            if ("out_trade_no".equals(temp[0])) {
                System.out.println("商户网站唯一订单号:" + temp[1]);
            }
            if ("orgId".equals(temp[0])) {
                orgId = temp[1];
                System.out.println("商家订单orgId:" + orgId);
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("amount".equals(temp[0])) {
                amount = Double.valueOf(temp[1]);
            }
            if ("actualPayment".equals(temp[0])) {
                actualPayment = Double.valueOf(temp[1]);
                System.out.println("实际支付actualPayment:" + actualPayment);
            }
        }

        // 更新
        IntegralRechargeDTO integralRechargeDTO = new IntegralRechargeDTO();
        integralRechargeDTO.setThirdOrderId(thirdOrderId);
        integralRechargeDTO.setUpdateAt(new Date());
        integralRechargeDTO.setIsReceived(1);
        System.out.println("更新成功");
        boolean update = integralPurchaseMapperExtra.updateIntegralPurchaseRecord(integralRechargeDTO) > 0;

        //插入log记录
        IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
        integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        integralLogDTO.setUserId(userId);
        integralLogDTO.setIntegralLogType(4);
        integralLogDTO.setIntegralAmount(integralAmount);
        integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

        integralPurchaseMapperExtra.updateIntegralInfo(integralLogDTO.getBeforeIntegralAmount() + integralLogDTO.getBeforeIntegralAmount(), userId);
        return 1;

    }

    // 积分购买（微信）
    public int getAddBuyIntegralOrderWechat(Map<String, Object> restmap){
        String[] resDate = restmap.get("attach").toString().split("\\^");
        String[] temp;
        String thirdOrderId = restmap.get("transaction_id").toString();
        String orderId = "";
        String userId = "";
        double amount = 0;
        int integralAmount = 0;
        for (String data : resDate) {
            temp = data.split("\'");
            if (temp.length < 2) {//判空
                continue;
            }
            if ("orderId".equals(temp[0])) {
                orderId = temp[1];
            }
            if ("userId".equals(temp[0])) {
                userId = temp[1];
            }
            if ("integralAmount".equals(temp[0])) {
                integralAmount = Integer.valueOf(temp[1]);
            }
            if ("amount".equals(temp[0])) {
                amount = Double.valueOf(temp[1]);
            }
        }
        // 更新
        IntegralRechargeDTO integralRechargeDTO = new IntegralRechargeDTO();
        integralRechargeDTO.setOrderId(orderId);
        integralRechargeDTO.setThirdOrderId(thirdOrderId);
        integralRechargeDTO.setUpdateAt(new Date());
        integralRechargeDTO.setIsReceived(1);
        System.out.println("更新成功");
        boolean update = integralPurchaseMapperExtra.updateIntegralPurchaseRecord(integralRechargeDTO) > 0;

        //插入log记录
        IntegralLogDTO integralLogDTO = integralService.getIntegralInfo(userId);
        integralLogDTO.setOrderId(orderId);
        integralLogDTO.setIntegralLogId(System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15).replace("-", ""));
        integralLogDTO.setUserId(userId);
        integralLogDTO.setIntegralLogType(4);
        integralLogDTO.setIntegralAmount(integralAmount);
        integralPurchaseMapperExtra.insertIntegralLog(integralLogDTO);

        integralPurchaseMapperExtra.updateIntegralInfo(integralLogDTO.getBeforeIntegralAmount() + integralLogDTO.getBeforeIntegralAmount(), userId);
        return 1;
    }
}
