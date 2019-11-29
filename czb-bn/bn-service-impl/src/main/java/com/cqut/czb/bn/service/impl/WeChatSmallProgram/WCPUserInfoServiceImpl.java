package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.entity.dto.WCPAssessToken;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPUserInfoService;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.Charset;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-28 20:12
 */
@Service
public class WCPUserInfoServiceImpl implements WCPUserInfoService {

    @Override
    public String getRecommendQRCode(UserDTO user) {
        if(user == null)
            return null;
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

                String json = "{\"scene\":\" " + user.getUserId() + "\"}";
                byte[] data = this.post("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+wcpAssessToken.getAccess_token(),json);
                return getImageString(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getImageString(byte[] data) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        return data != null ? encoder.encode(data) : "";
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