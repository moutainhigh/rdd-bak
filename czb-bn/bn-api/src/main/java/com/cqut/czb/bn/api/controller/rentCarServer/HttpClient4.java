package com.cqut.czb.bn.api.controller.rentCarServer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.Map.Entry;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClient4 {

    public static String doGet(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(url);
            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    //Map<String, Object> paramMap
    public static String doPost(String url, JSONObject json) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        // 创建httpClient实例
        httpClient = HttpClients.createDefault();
        // 创建httpPost远程连接实例
        HttpPost httpPost = new HttpPost(url);
        // 配置请求参数实例
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                .setSocketTimeout(60000)// 设置读取数据连接超时时间
                .build();
        // 为httpPost实例设置配置
        httpPost.setConfig(requestConfig);

        // 在请求头设置方法中 设置请求头
        httpPost = getContractTokenHeader(httpPost);

        // 封装post请求参数
//        if (null != paramMap && paramMap.size() > 0) {
//            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//            // 通过map集成entrySet方法获取entity
//            Set<Entry<String, Object>> entrySet = paramMap.entrySet();
//            // 循环遍历，获取迭代器
//            Iterator<Entry<String, Object>> iterator = entrySet.iterator();
//            while (iterator.hasNext()) {
//                Entry<String, Object> mapEntry = iterator.next();
//                nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
//            }
//
//            // 为httpPost设置封装好的请求参数
//            try {
//                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }

        StringEntity requestEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        try {
            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容(调用相应的响应信息处理方法)
            result = getContractTokenResponse(httpResponse);
//            HttpEntity entity = httpResponse.getEntity();
//            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 获取token时，设置请求头信息
     */
    private static HttpPost getContractTokenHeader(HttpPost httpPost){
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("charset", "UTF-8");

        return httpPost;
    }

    /**
     * 获取token时，响应信息处理方法
     */
    private static String getContractTokenResponse(CloseableHttpResponse httpResponse){
        return httpResponse.getFirstHeader("token").toString();
    }
}
