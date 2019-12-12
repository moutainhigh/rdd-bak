package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.UserRoleMapperExtra;
import com.cqut.czb.bn.entity.dto.WCPAssessToken;
import com.cqut.czb.bn.entity.dto.WCPTabbarInfo;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.UserRoleDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.service.AppPersonalCenterService;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPUserInfoService;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-28 20:12
 */
@Service
public class WCPUserInfoServiceImpl implements WCPUserInfoService {

    @Autowired
    AppPersonalCenterService appPersonalCenterService;

    @Autowired
    UserRoleMapperExtra userRoleMapperExtra;

    @Override
    public String getRecommendQRCode(UserDTO user) {
        if(user == null)
            return null;
        String json = "{\"scene\":\"" + user.getUserAccount() + "\",\"path\":\"/pages/index/index\"}";
        return encode(initQrCodeNetWork(json));
    }

    @Override
    public String getCommodityQrCode(String userId, String commodityId) {
        String json = "{\"scene\":\"s=" + userId + "&id=" + commodityId + "\",\"path\":\"/pages/product/product\"}";
        return FileUploadUtil.putObject("小程序码.png", new ByteArrayInputStream(initQrCodeNetWork(json)));
    }

    /**
     * 传入的json参数字符
     * @param json
     * @return
     */
    private byte[] initQrCodeNetWork(String json){
        WCPAssessToken wcpAssessToken;
        WCProgramConfig config = new WCProgramConfig();
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Request req = new Request.Builder()
                .get()
                .url("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + config.app_id + "&secret=" + config.app_secret)
                .build();
        Call call = client.newCall(req);
        try {
            Response res = call.execute();
            wcpAssessToken = gson.fromJson(res.body().charStream(), WCPAssessToken.class);
            if(wcpAssessToken.getErrcode() == null && wcpAssessToken.getErrmsg() == null){
                System.out.println(json);
                byte[] data = this.post("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+wcpAssessToken.getAccess_token(),json);
                return data;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<WCPTabbarInfo> getTabBarInfo(String userId) {
        List<WCPTabbarInfo> tabbars = new ArrayList<>();
        WCPTabbarInfo tabbarHomePage = new WCPTabbarInfo();
        tabbarHomePage.setUrl("homePage");
        tabbarHomePage.setImgNormal("/static/homePage.png");
        tabbarHomePage.setImgClick("/static/homePageNo.png");
        tabbarHomePage.setText("首页");

        WCPTabbarInfo tabbarMine = new WCPTabbarInfo();
        tabbarMine.setUrl("mine");
        tabbarMine.setImgNormal("/static/mine_cilck.png");
        tabbarMine.setImgClick("/static/mine.png");
        tabbarMine.setText("我的");
        tabbars.add(tabbarHomePage);
        tabbars.add(tabbarMine);
        UserRoleDTO userRole = new UserRoleDTO();
        userRole.setUserId(userId);
        List<UserRoleDTO> userRoles = userRoleMapperExtra.selectUserRoleName(userRole); //查询用户角色信息
        for(UserRoleDTO roleDTO : userRoles){
            if("微信商家".equals(roleDTO.getRoleName())){
                WCPTabbarInfo tabbarShop = new WCPTabbarInfo();
                tabbarShop.setUrl("shopOrder");
                tabbarShop.setImgNormal("/static/order_click.png");
                tabbarShop.setImgClick("/static/order_noclick.png");
                tabbarShop.setText("订单中心");
                tabbars.add(tabbarShop);
            }
        }
        return tabbars;
    }


    public static String encode(byte[] binaryData) {
        try {
            return new String(Base64.encodeBase64(binaryData), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /* 发送 post请求 用HTTPclient 发送请求*/
    public byte[] post(String URL, String json) {
        String obj = null;
        InputStream inputStream = null;
        Buffer reader = null;
        byte[] data = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(URL);
        httppost.addHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Accept", "application/json");
        try {
            StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
            s.setContentEncoding("UTF-8");
            httppost.setEntity(s);
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                // 获取相应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    inputStream = entity.getContent();
                    data = readInputStream(inputStream);
                }
                return data;
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    /**  将流 保存为数据数组
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 把outStream里的数据写入内存
        return outStream.toByteArray();
    }


}