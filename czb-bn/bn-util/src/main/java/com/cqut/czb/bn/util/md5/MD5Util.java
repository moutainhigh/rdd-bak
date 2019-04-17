package com.cqut.czb.bn.util.md5;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private final static String[] strDigits = { 
		"0", "1", "2", "3", "4", "5","6"
		, "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
   private MD5Util() {}

   // 返回形式为数字跟字符串
   private static String byteToArrayString(byte bByte) {
       int iRet = bByte;
       // System.out.println("iRet="+iRet);
       if (iRet < 0) {
           iRet += 256;
       }
       int iD1 = iRet / 16;
       int iD2 = iRet % 16;
       return strDigits[iD1] + strDigits[iD2];
   }


   // 转换字节数组为16进制字串
   private static String byteToString(byte[] bByte) {
       StringBuffer sBuffer = new StringBuffer();
       for (int i = 0; i < bByte.length; i++) {
           sBuffer.append(byteToArrayString(bByte[i]));
       }
       return sBuffer.toString();
   }

   // 获取字符串的MD5值
   public static String getMD5Code(String strObj) {
	   if(strObj == null)
		   return null;
	   
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return resultString;
	}

   // 获取文件的MD5值
	public static String getMD5CodeForFile(File file) {
		String md5 = null;
		try {
			md5 = getMD5CodeForInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		return md5;
	}
   
   // 获取输入流的MD5值
	public static String getMD5CodeForInputStream(InputStream ins) {
		byte[] buffer = new byte[1024];
		int numRead = 0;
		String md5 = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			while ((numRead = ins.read(buffer)) > 0)
				md.update(buffer, 0, numRead);
			ins.close();
			md5 = byteToString(md.digest());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	   return md5;
   }
	
	/**
	 * 获取输入流的md5值，同时在获取完全后进行重置
	 * 如果流不支持重置，则直接返回null
	 * */ 
	public static String getMD5CodeForInputStreamAndReset(InputStream ins) {
		// 如果流不支持重置
		if(!ins.markSupported())
			return null;
		
		byte[] buffer = new byte[1024];
		int numRead = 0;
		String md5 = null;
		// 在进行流的读取前进行标记
		ins.mark(0);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			while ((numRead = ins.read(buffer)) > 0)
				md.update(buffer, 0, numRead);
			// 重置流
			ins.reset();
			md5 = byteToString(md.digest());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	   
	   return md5;
	}
	
	/**
	 * 通过文件通道获取文件的md5值
	 * */
	public static String getMD5CodeForFileChannel(FileChannel channel) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			while (channel.read(buffer) > 0) {
				buffer.flip();
				md.update(buffer);
				buffer.clear();
			}
			md5 = byteToString(md.digest());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5;
	}
	
	
	/**
	 * 生成微信预支付签名
	 * */
	
	
	private static String byteArrayToHexString(byte b[]) {  
        StringBuffer resultSb = new StringBuffer();  
        for (int i = 0; i < b.length; i++)  
            resultSb.append(byteToHexString(b[i]));  
  
        return resultSb.toString();  
    }  
  
    private static String byteToHexString(byte b) {  
        int n = b;  
        if (n < 0)  
            n += 256;  
        int d1 = n / 16;  
        int d2 = n % 16;  
        return hexDigits[d1] + hexDigits[d2];  
    }  
  
    public static String MD5Encode(String origin, String charsetname) {  
        String resultString = null;  
        try {  
            resultString = new String(origin);  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            if (charsetname == null || "".equals(charsetname))  
                resultString = byteArrayToHexString(md.digest(resultString  
                        .getBytes()));  
            else  
                resultString = byteArrayToHexString(md.digest(resultString  
                        .getBytes(charsetname)));  
        } catch (Exception exception) {  
        }  
        return resultString;  
    }  
  
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
}

















