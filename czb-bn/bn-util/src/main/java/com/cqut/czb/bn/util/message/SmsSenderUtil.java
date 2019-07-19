package com.cqut.czb.bn.util.message;

import com.alibaba.fastjson.JSONArray;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 发送短信工具类
 * 作者：黎鹏
 * 日期：2018-04-24
 */
public class SmsSenderUtil {
	
	private final static String SHA_256_ALGORTHM = "SHA-256";
	
	protected static Random random = new Random();
	
    public static int getRandom() {
    	
    	return random.nextInt(999999) % 900000 + 100000;
	}
    
    public static long getUnixTime() {
    	
    	return System.currentTimeMillis() / 1000;
    }
    
    public static String caculateSig(String appKey, long random, long curTime, String mobile) throws NoSuchAlgorithmException {
    	String sig = String.format("appkey=%s&random=%d&time=%d&mobile=%s", appKey, random, curTime, mobile);
    	
        return strToHash(sig);
    }
    
    private static String strToHash(String str) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(SHA_256_ALGORTHM);
        byte[] inputByteArray = str.getBytes();
        messageDigest.update(inputByteArray);
        byte[] resultByteArray = messageDigest.digest();
        return byteArrayToHex(resultByteArray);
	}

	private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
	}
	
	public static JSONArray createParams(String... params) {
		int numOfParam = params.length;
		JSONArray array = new JSONArray(numOfParam);
		for (int i = 0; i < numOfParam; i++) {
			array.add(params[i]);
		}
		return array;
	}
	
	public static String post(String url, String requestPacket) {
		HttpRequest request = HttpRequest.post(url).body(requestPacket);
		
		request.acceptEncoding("utf-8");
		request.header("Content-Type", "application/json");
	    request.header("Accept", "application/json");
	    
	    HttpResponse response = request.send();
	    
	    return response.bodyText();
	}
}
