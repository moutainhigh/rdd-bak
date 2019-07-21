package com.cqut.czb.bn.api.controller.verifyAsynNoticeInfo;

import com.cqut.czb.bn.service.IPaymentRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
	 * 油卡购买：验证异步通知信息(支付宝(爱虎))
	 */
	@RequestMapping(value="/verifyBuyPetrolInfoAiHu", method= RequestMethod.POST)
	public synchronized void verifyBuyPetrolInfoAiHu(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("支付宝回调——购买接口");
	    response.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		try {
			response.getWriter().print(paymentRecordService.verifyAsynNoticeInfoAiHu(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 油卡充值：验证异步通知信息(支付宝(爱虎))recharge
	 */
	@RequestMapping(value="/verifyPetrolRechargeInfoAiHu", method= RequestMethod.POST)
	public synchronized void verifyPetrolRechargeInfoAiHu(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("支付宝回调——充值接口");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		try {
			response.getWriter().print(paymentRecordService.verifyAsynNoticeInfoAiHu(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 验证异步通知信息(微信)(充值和购买的接口)
	 */
	@RequestMapping(value="/verifyBuyPetrolInfoWeChat", method=RequestMethod.POST)
	public synchronized void verifyBuyPetrolInfoWeChat(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("微信成功回调");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		try {
			response.getWriter().write(paymentRecordService.orderPayNotify(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 购买服务：验证异步通知信息(支付宝(爱虎))
	 */
	@RequestMapping(value="/verifyBuyServiceInfoAiHu", method= RequestMethod.POST)
	public synchronized void verifyBuyServiceInfoAiHu(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("支付宝回调——充值接口");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		try {
			System.out.println("购买服务购买成功");
			response.getWriter().print(paymentRecordService.verifyBuyServiceAiLi(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证异步通知信息(微信)(购买服务)
	 */
	@RequestMapping(value="/verifyBuyServiceInfoWeChat", method=RequestMethod.POST)
	public synchronized void verifyBuyServiceInfoWeChat(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("微信购买服务成功回调");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		try {
			response.getWriter().write(paymentRecordService.buyServiceOrderPayNotify(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    /**
     * vip充值：验证异步通知信息(支付宝)
     */
    @RequestMapping(value="/verifyRechargeVipInfoAiHu", method= RequestMethod.POST)
    public synchronized void verifyRechargeVipInfoAiHu(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付宝回调——充值接口");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
            System.out.println("充值vip购买成功");
            response.getWriter().print(paymentRecordService.verifyVipRechargeAiLi(request));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	/**
	 * vip充值：验证异步通知信息(微信)
	 */
	@RequestMapping(value="/verifyRechargeVipInfoWeChat", method= RequestMethod.POST)
	public synchronized void verifyRechargeVipInfoWeChat(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("微信回调—充值接口");
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=utf-8");
		try {
			System.out.println("充值vip购买成功");
			response.getWriter().print(paymentRecordService.rechargeVipOrderPayNotify(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
