package com.cqut.czb.bn.entity.dto.weChatAppletPushNotification.v2;


import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WxgzhUtil {
    @Autowired
    private static final Logger log = LoggerFactory.getLogger(WxgzhUtil.class);
    private static Token token = null;

    /**
     * http请求方法
     * @param requestUrl
     * @param requestMethod
     * @param outputStr
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            if (outputStr != null) {
                OutputStream outputStream = conn.getOutputStream();

                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
            System.out.println("连接超时：{}");
        } catch (Exception e) {
            System.out.println("https请求异常：{}");
        }
        return null;
    }

    /**
     * 发送模板消息
     *
     * @param template
     * @return
     */
    public static boolean sendTemplateMsg(Template template) {
        boolean flag = false;
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=ACCESS_TOKEN";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", getToken().getAccessToken());
        String Result = httpsRequest(requestUrl, "POST", template.toJSON());
        System.out.println(Result);
        new JSONObject();
        JSONObject jsonResult = JSONObject.fromObject(Result);
        if (jsonResult != null) {
            int errorCode = jsonResult.getInt("errcode");
            String errorMessage = jsonResult.getString("errmsg");
            if (errorCode == 0) {
                flag = true;
            } else {
                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
            }
        }
        return flag;
    }
    //  测试
    public static void main(String[] args) {
        Template template=new Template();
        //这里填写模板ID
        template.setTemplate_id("BQSxLKET6Pp-jqHg06CwsO1eo4ncTP3Kp_5QO3q1hQk");//已改
        //这里填写用户的openid
        template.setTouser("otVKI5K7GYwBjx5JR0bfaA0l9d2I");//已改
        //这里填写点击订阅消息后跳转小程序的界面
        template.setPage("pages/homePage/homePage");
        List<TemplateParam> paras=new ArrayList<>();
        paras.add(new TemplateParam("character_string2","89745612"));
        paras.add(new TemplateParam("thing4","一件衣服"));
        paras.add(new TemplateParam("amount1","59"));
        paras.add(new TemplateParam("amount5","45"));
        paras.add(new TemplateParam("date3","2019-12-18 09:23:09"));
        template.setTemplateParamList(paras);
        sendTemplateMsg(template);
    }
    //  测试
    public static void sendMessageUtils(String openid) {
        Template template=new Template();
        //这里填写模板ID
        template.setTemplate_id("BQSxLKET6Pp-jqHg06CwsO1eo4ncTP3Kp_5QO3q1hQk");//已改
        //这里填写用户的openid
        template.setTouser(openid);//已改
        //这里填写点击订阅消息后跳转小程序的界面
        template.setPage("pages/homePage/homePage");
        List<TemplateParam> paras=new ArrayList<>();
        paras.add(new TemplateParam("character_string2","89745612"));
        paras.add(new TemplateParam("thing4","一件衣服"));
        paras.add(new TemplateParam("amount1","59"));
        paras.add(new TemplateParam("amount5","45"));
        paras.add(new TemplateParam("date3","2019-12-18 09:23:09"));
        template.setTemplateParamList(paras);
        sendTemplateMsg(template);
    }
    /**
     * 获取有效的token
     * @return
     */
    public static Token getToken() {
        if (token != null) {
            if (token.getGetTokenTime() + token.getExpiresIn() > new Date()
                    .getTime()) {
                return token;
            }
        }
        //记得补充上appid和appsecret
        String appid="wxd2d0171429ac208f";
        String secret="f3bf5f0e40c5a76eb9e84c9f683bcf1c";
        String result = httpsRequest(
                //appid 小程序的id,appsecret,请替换当前的
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret,
                "GET", null);
        new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(result);
        if (jsonObject != null) {
            try {
                token = new Token();
                token.setGetTokenTime(new Date().getTime());
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
                System.out.println("成功");
            } catch (JSONException e) {
                System.out.println("失败");
                token = null;
                log.error("获取token失败 errcode:{} errmsg:{}",
                        Integer.valueOf(jsonObject.getInt("errcode")),
                        jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
}