package com.cqut.czb.bn.entity.dto.weChatAppletPushNotification;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;

import java.io.IOException;

public class sendNotification {

    //获取access_token 接口
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?"
            + "grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public  static void sendMessage() throws IOException {

        // 获取token
        String token = getAccessToken();

//        subscribeState(token);

        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/subscribe?access_token=" + token;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", "otVKI5K7GYwBjx5JR0bfaA0l9d2I");   // openid
        jsonObject.put("template_id", "BQSxLKET6Pp-jqHg06CwsO1eo4ncTP3Kp_5QO3q1hQk");
        jsonObject.put("appid",WCProgramConfig.app_id);
        jsonObject.put("pagepath","pages/homePage/homePage");
        jsonObject.put("scene", "585");
        jsonObject.put("title", "订单下单提醒");
        JSONObject data = new JSONObject();

//        订单号
        JSONObject character_string2 = new JSONObject();
        character_string2.put("value", "89745612");
        character_string2.put("color", "#173177");

//        商品详情
        JSONObject thing4 = new JSONObject();
        thing4.put("value", "hello");
        thing4.put("color", "#173177");

//        支付金额
        JSONObject amount1 = new JSONObject();
        amount1.put("value", "hello");
        amount1.put("color", "#173177");

//        收益佣金
        JSONObject amount5 = new JSONObject();
        amount5.put("value", "hello");
        amount5.put("color", "#173177");

//        支付时间
        JSONObject date3 = new JSONObject();
        date3.put("value", "hello");
        date3.put("color", "#173177");

        data.put("character_string2", character_string2);
        data.put("thing4", thing4);
        data.put("amount1", amount1);
        data.put("amount5", amount5);
        data.put("date3", date3);

        jsonObject.put("data", data);

        String string = HttpClientUtils.sendPostJsonStr(postUrl, jsonObject.toJSONString());
        JSONObject result = JSON.parseObject(string);
        int errcode = result.getIntValue("errcode");
        if (errcode == 0) {
            // 发送成功
            System.out.println("发送成功");
        } else {
            // 发送失败
            System.out.println("发送失败");
        }
    }

    /**
     * 获取token
     * @return
     */
    public static String  getAccessToken() {
        AccessToken accessToken = null;
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", WCProgramConfig.app_id).replace("APPSECRET", WCProgramConfig.app_secret);
        net.sf.json.JSONObject jsonObject = HttpUtil.doGetstr(requestUrl);
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setAccess_token(jsonObject.getString("access_token"));
                accessToken.setExpires_in(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
//                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken.getAccess_token();
    }

    /**
     * 获取微信分享token
     * @return
     */
    public static String  getWXAccessToken() {
        AccessToken accessToken = null;
        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", WCProgramConfig.app_id).replace("APPSECRET", WCProgramConfig.app_secret);
        net.sf.json.JSONObject jsonObject = HttpUtil.doGetstr(requestUrl);
        if (null != jsonObject) {
            try {
                accessToken = new AccessToken();
                accessToken.setAccess_token(jsonObject.getString("access_token"));
                accessToken.setExpires_in(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败
//                logger.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken.getAccess_token();
    }

    public static void main(String[] args) throws Exception {
        System.out.println();
        sendMessage();
    }

    public static Integer subscribeState(String token){
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+token +"&openid=otVKI5K7GYwBjx5JR0bfaA0l9d2I";
        net.sf.json.JSONObject result = HttpUtil.doGetstr(tmpurl);
        JSONObject resultJson = new JSONObject(result);
//        System.out.println("获取用户是否订阅 errcode:{} errmsg:{}", resultJson.getInteger("errcode"), resultJson.getString("errmsg"));
        String errmsg = (String) resultJson.get("errmsg");
        System.out.println(errmsg);
        if(errmsg==null){
            //用户是否订阅该公众号标识（0代表此用户没有关注该公众号 1表示关注了该公众号）。
            Integer subscribe = (Integer) resultJson.get("subscribe");
            return subscribe;
        }
        return -1;
    }

}
