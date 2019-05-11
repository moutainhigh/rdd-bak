package com.cqut.czb.bn.service.impl.paymentRecord;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cqut.czb.bn.entity.dto.paymentRecord.AiHuAlipayConfig;
import com.cqut.czb.bn.service.IPaymentRecordService;
import com.cqut.czb.bn.service.IRefuelingCard;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 支付账单检测
 * @author 陈德强
 */
@Service
public class PaymentRecordService implements IPaymentRecordService {

	private String characterEncoding = "UTF-8";

	@Resource(name = "refuelingCardService")
	private IRefuelingCard refuelingCard;

	@Override
	@SuppressWarnings("rawtypes")
	public String verifyAsynNoticeInfoAiHu(HttpServletRequest request) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();

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

		//是否被篡改的标识
		boolean signVerfied = false;
		try {
			//检测支付订单是否被篡改
			signVerfied = AlipaySignature.rsaCheckV1(params, AiHuAlipayConfig.alipay_public_key,
					AiHuAlipayConfig.charset, AiHuAlipayConfig.sign_type);
			System.out.println("408 " + signVerfied);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

		try {
			if (signVerfied) {
//				支付账单是否一致
				if (isCorrectDataAiHu(params)) {//交易成功
					Object[] param = { params };
					Map result = refuelingCard.payCallback(1, param);//1为支付宝支付（用于拓展）
					if (result.get("success").equals(AlipayConfig.response_success)) {
						return AlipayConfig.response_success;
					} else if (result.get("fail").equals(AlipayConfig.response_fail)) {
						return AlipayConfig.response_fail;
					} else
						return null;
				} else {//交易失败
					Object[] param = { params };
					refuelingCard.purchaseFailed(param);//油卡放回
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


	/*
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

	private boolean isCorrectDataAiHu(Map<String, String> params) {

		System.out.println("675 " + params.get("app_id"));
		// 验证app_id是否一致
		if (!params.get("app_id").equals(AiHuAlipayConfig.app_id)) {
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










}
