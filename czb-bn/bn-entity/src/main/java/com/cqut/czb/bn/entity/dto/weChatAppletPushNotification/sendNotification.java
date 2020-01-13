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

    public void sendMessage() throws IOException {

        // 获取token
        String token = getAccessToken();

        String postUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + token;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", "otVKI5K7GYwBjx5JR0bfaA0l9d2I");   // openid
        jsonObject.put("template_id", "你的模板id");
        jsonObject.put("url", "http://www.baidu.com");

        JSONObject data = new JSONObject();
        JSONObject first = new JSONObject();
        first.put("value", "hello");
        first.put("color", "#173177");
        JSONObject keyword1 = new JSONObject();
        keyword1.put("value", "hello");
        keyword1.put("color", "#173177");
        JSONObject keyword2 = new JSONObject();
        keyword2.put("value", "hello");
        keyword2.put("color", "#173177");
        JSONObject keyword3 = new JSONObject();
        keyword3.put("value", "hello");
        keyword3.put("color", "#173177");
        JSONObject remark = new JSONObject();
        remark.put("value", "hello");
        remark.put("color", "#173177");

        data.put("first", first);
        data.put("keyword1", keyword1);
        data.put("keyword2", keyword2);
        data.put("keyword3", keyword3);
        data.put("remark", remark);

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

    public static void main(String[] args) throws Exception {
        System.out.println();
        System.out.println("token:"+getAccessToken());
    }
}
