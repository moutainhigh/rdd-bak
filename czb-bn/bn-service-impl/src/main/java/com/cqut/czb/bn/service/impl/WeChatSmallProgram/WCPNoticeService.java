package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxTemplateMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class WCPNoticeService implements com.cqut.czb.bn.service.weChatSmallProgram.WCPNoticeService {

    @Autowired
    private RestTemplate restTemplate;

    /** * * @param access_token app的token * @param openid 用户openid * @param formId 表单ID * @param templateId 模板ID * @param keywords {与模板字段一一对应} * @return */
    @Override
    public String pushOneUser(String openid, String templateid, String[] values, String first, String remark) {
        String access_token = getAccess_token();

        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=" + access_token;
        //拼接推送的模版
        WxTemplateMsg msg = new WxTemplateMsg();
        msg.setAppid(WCProgramConfig.public_id);
        msg.setMiniprogram(new WxTemplateMsg.MiniProgram(WCProgramConfig.app_id, "pages/homePage/homePage"));
        msg.setTemplate_id(templateid);
        Map<String, WxTemplateMsg.MsgData> data = new HashMap<>();
        if (first!=null){
            data.put("first",new WxTemplateMsg.MsgData(first, null));
        }
        if (remark!=null){
            data.put("remark",new WxTemplateMsg.MsgData(remark, null));
        }
        if(values.length>0){
            for(int i=1;i<=values.length;i++){
                WxTemplateMsg.MsgData keyword = new WxTemplateMsg.MsgData();
                keyword.setValue(values[i-1]);
                data.put("keyword"+i, keyword);
            }
            msg.setData(data);
        }else{
            return null;
        }
        Map<String, Object> postData = new HashMap<>();
        postData.put("access_token", access_token);
        postData.put("touser", openid);
        postData.put("mp_template_msg", msg);
        if(restTemplate==null){
            restTemplate = new RestTemplate();
        }
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, postData, String.class);
        System.out.println("小程序推送结果=" + responseEntity.getBody());
        return responseEntity.getBody();
    }
    /* * 获取access_token * appid和appsecret到小程序后台获取，当然也可以让小程序开发人员给你传过来 * */
    @Override
    public String getAccess_token() {
        //获取access_token
        String appid = WCProgramConfig.app_id;
        String appsecret = WCProgramConfig.app_secret;
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential" +
                "&appid=" + appid + "&secret=" + appsecret;
        if(restTemplate==null){
            restTemplate = new RestTemplate();
        }
        String json = restTemplate.getForObject(url, String.class);
        JSONObject myJson = JSONObject.parseObject(json);
        return myJson.get("access_token").toString();
    }
//    public static void main(String[] args) {
//        System.out.println(new WCPNoticeService().getAccess_token());
//        WCPNoticeService weChatUtil = new WCPNoticeService();
//        String[] values ={ "测试","测试","测试","2019-5-8 10:10:10"};
//        System.out.println(weChatUtil.pushOneUser("otVKI5DebDtXNXtxo-nvoqUHC_9o", "wKKS8Z8goyJy75YPeSAzwmfPMcLfmKSG3mMXN4nmjk8", values, "订单处理通知", "您购买的产品已交付"));
//    }
}
