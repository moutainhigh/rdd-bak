package com.cqut.czb.auth.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtils {

    // 加密模式
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";

    //解决java.security.NoSuchAlgorithmException: Cannot find any provider supporting AES/CBC/PKCS7Padding
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 解密
     *
     * @param content 目标密文
     * @param key     秘钥
     * @param iv      偏移量
     * @return
     */
    public static String decrypt(@NotNull String content, @NotNull String key, @NotNull String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            byte[] sessionKey = java.util.Base64.getDecoder().decode(key);
            SecretKeySpec keySpec = new SecretKeySpec(sessionKey, AES_NAME);
            byte[] ivByte = java.util.Base64.getDecoder().decode(iv);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(ivByte);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return new String(cipher.doFinal(Base64.decodeBase64(content)), CHARSET_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    public static void main(String[] args) {
        String sessionKey = "zF6lhhRqTdWJ8sb45RTxsw==";
        String encryptedData = "JXZ5dxBn7EqgRWTbqt50rxrN69Y9okDdL0YzvrSwNjKA9blYJagZbhovcwbhFy8vVaqjVVEjIl451JOCXIB2fpNpq0sbIxV+B28pKWLA8y2jn7R1iTE7O7k/tW1yVDMZwqRQyTw9lV/qlISw+HX887DeVWCfem6lx8jZ/C+kshJdig4Li06AIA9A9smToZYI";
        String iv = "CO5eq/F5TTv9SuwiMLDNaA==";
        String decrypt = AESUtils.decrypt(encryptedData, sessionKey, iv);
        System.out.println(decrypt);
    }

}

