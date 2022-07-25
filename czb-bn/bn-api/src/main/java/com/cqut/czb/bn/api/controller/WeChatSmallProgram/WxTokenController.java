package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Arrays;

@RestController
@RequestMapping("/api/wxpublic")
public class WxTokenController {
    @RequestMapping("/verify_wx_token")
    public String verifyWXToken(HttpServletRequest request) throws Exception {
        String msgSignature = request.getParameter("signature");
        String msgTimestamp = request.getParameter("timestamp");
        String msgNonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (verifyUrl(msgSignature, msgTimestamp, msgNonce)) {
            return echostr;
        }
        return null;
    }

    private final static String TOKEN = "rdd2019";

    private static boolean verifyUrl(String msgSignature, String timeStamp, String nonce)
            throws Exception {
        // 这里的 WXPublicConstants.TOKEN 填写你自己设置的Token就可以了
        String signature = getSHA1(TOKEN, timeStamp, nonce);
        if (!signature.equals(msgSignature)) {
            throw new Exception();
        }
        return true;
    }

    private static String getSHA1(String token, String timestamp, String nonce) throws Exception {
        try {
            String[] array = new String[]{token, timestamp, nonce};
            StringBuffer sb = new StringBuffer();
            // 字符串排序
            Arrays.sort(array);
            for (int i = 0; i < 3; i++) {
                sb.append(array[i]);
            }
            String str = sb.toString();
            // SHA1签名生成
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            byte[] digest = md.digest();

            StringBuffer hexstr = new StringBuffer();
            String shaHex = "";
            for (int i = 0; i < digest.length; i++) {
                shaHex = Integer.toHexString(digest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexstr.append(0);
                }
                hexstr.append(shaHex);
            }
            return hexstr.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
