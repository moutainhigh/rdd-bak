package com.cqut.czb.bn.api.controller.test;


import java.io.IOException;
import java.io.PrintWriter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/WeChat")
public class WechatController {
    private static Logger logger = Logger.getLogger(WechatController.class);

    private static String WECHAT_TOKEN = "123456";

    @RequestMapping(value = "/requestUrl", method = RequestMethod.GET)
    public synchronized void getUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.error("WechatController   ----   WechatController");

        System.out.println("========WechatController========= ");
        logger.info("请求进来了...");

        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            // out.print(name + "=" + value);
            String log = "name =" + name + "     value =" + value;
            logger.error(log);
        }

        String signature = request.getParameter("signature");/// 微信加密签名
        String timestamp = request.getParameter("timestamp");/// 时间戳
        String nonce = request.getParameter("nonce"); /// 随机数
        String echostr = request.getParameter("echostr"); // 随机字符串
        PrintWriter out = response.getWriter();

        //if (SignUtil.checkSignature(signature, timestamp, nonce)) {
        out.print(echostr);
//		}s
        out.close();
//		out = null;

    }
}