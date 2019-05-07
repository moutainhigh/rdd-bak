package com.cqut.czb.bn.api.controller.verifyAsynNoticeInfo;

import com.cqut.czb.bn.service.IPaymentRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈德强
 * @date 2019/5/7
 * */

@RestController
@RequestMapping("/verifyAsyn")
public class VerifyAsynNoticeInfoController {
	
	@Resource(name="paymentRecordService")
	private IPaymentRecordService paymentRecordService;

	/**
	 * 验证异步通知信息(支付宝(爱虎))
	 */
	@RequestMapping(value="/verifyAsynNoticeInfoAiHu", method= RequestMethod.POST)
	public void verifyAsynNoticeInfoAiHu(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("支付宝成功回调我的接口");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		try {
			response.getWriter().print(paymentRecordService.verifyAsynNoticeInfoAiHu(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
