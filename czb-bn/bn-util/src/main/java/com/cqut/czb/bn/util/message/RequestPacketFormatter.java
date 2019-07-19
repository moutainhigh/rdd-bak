package com.cqut.czb.bn.util.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.security.NoSuchAlgorithmException;



/**
 * 请求数据包
 * 作者：黎鹏
 * 日期：2018-04-24
 */
public class RequestPacketFormatter {//请求报文格式
	private ShortMessage shortMessage = new ShortMessage();

	//请求数据包的格式
	public String formatRequestPacket(String mobile, String nationCode, long time, long random, 
			int templateId, JSONArray params) throws NoSuchAlgorithmException {
		JSONObject json = new JSONObject();

		json.put("tel", createTel(mobile, nationCode));
		json.put("tpl_id", templateId);
		json.put("params", params);
		json.put("sig", SmsSenderUtil.caculateSig(shortMessage.getAppKey(), random, time, mobile));
		json.put("time", time);
		json.put("extend", "");
		json.put("ext", "");
		
		return json.toJSONString();
	}
	
	private JSONObject createTel(String mobile, String nationCode) {
		JSONObject tel = new JSONObject();
		
		tel.put("nationcode", nationCode);
		tel.put("mobile", mobile);
		
		return tel;
	}
}
