package com.cqut.czb.bn.service.impl.paymentRecord;

import com.cqut.czb.bn.entity.dto.AllPetrolDTO;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.service.IRefuelingCard;
import com.cqut.czb.bn.service.impl.personCenterImpl.AlipayConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class RefuelingCardService implements IRefuelingCard {

	@Autowired
	private  AppBuyPetrolService appBuyPetrolService;

	@Resource(name = "paymentRecordService")
	private PaymentRecordService paymentRecordService;

	// 同一时间只允许一个线程访问购买油卡接口
	public synchronized Map payCallback(int flag, Object[] param) {
		int count = 0;
		// 支付宝支付
			Map<String, String> result = new HashMap<>();
			Map<String, String> params = (HashMap<String, String>) param[0];
			if (getAddPaymentRecordData(params) == 1) {
				result.put("success", AlipayConfig.response_success);
				return result;
			} else {
				result.put("fail", AlipayConfig.response_fail);
				return result;
			}
	}

	/**
	 * 获取订单数据存入数据库(支付宝)
	 */
	public synchronized int getAddPaymentRecordData(Map<String, String> params) {
		String[] resDate = params.get("passback_params").split("\\^");
		String[] temp;
		// petrol_record主键
		String id = "";
		// 0代表充值，1代表购油——对应payType
		String tt = "";
		double money = 0;
		int count = 0;
		for (String data : resDate) {
			temp = data.split("\'");
			if ("orgId".equals(temp[0])) {
				id = temp[1];
			}
			if ("payType".equals(temp[0])) {
				System.out.println(temp[0] + ":" + temp[1]);
				tt = temp[1];
			}
			if ("money".equals(temp[0])) {

				money = Double.valueOf(temp[1]);
				System.out.println("充值金额： " + money);
			}
			if ("count".equals(temp[0])) {
				count = Integer.parseInt(temp[1]);
				System.out.println("购买数量 " + count);
			}
		}

		//tt对应payType支付方式
		if ("0".equals(tt)) {
			System.out.println("this is 0——充值");
		} else if ("1".equals(tt)) {
			System.out.println("this is 1——购买油卡");
//			此处插入购油的相关信息，油卡购买记录
//			修改相应油卡的信息
			appBuyPetrolService.updatePetrol(AllPetrolDTO.getCurrentPetrol());

		} else if ("2".equals(tt)) {
			System.out.println("优惠卷");
//			modifyAndInsertCoupons(count, id, money);
		}
		return 1;
	}
}
