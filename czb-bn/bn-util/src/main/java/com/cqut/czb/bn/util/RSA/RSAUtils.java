package com.cqut.czb.bn.util.RSA;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 袁菘壑
 * 创建时间 2020/12/10
 */
public class RSAUtils {

    public static final String ALGORITHM = "RSA";

    public static final String PADDING = "RSA";

    public static final String PROVIDER = "BC";

    public static final String publicKey = "MDAwDQYJKoZIhvcNAQEBBQADHwAwHAIVALuhCQQxuB5gL79hRy8d9jIM7mbjAgMBAAE=";

    public static final String privateKey = "MIGLAgEAMA0GCSqGSIb3DQEBAQUABHcwdQIBAAIVALuhCQQxuB5gL79hRy8d9jIM7mbjAgMBAAECFA0GB38wCEyrdoTewi8WPZZzCUZZAgsA5RXEzk+Q1sRP9QILANGsYyPi6zxezHcCCwCnndXyXqVLfzhBAgsApj/FrCK02n1gKQIKIL1ZtNoIzlC2nA==";

    public static Map<String, String> createKeys(int keySize) {
        // 修改加密组件
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
        } catch (NoSuchProviderException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + ALGORITHM + "]");
        }

        // 初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        // 生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        // 得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        // 得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        // map装载公钥和私钥
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        // 返回map
        return keyPairMap;
    }

    /**
     * 得到公钥
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // 通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        byte[] cipherText = null;
        Base64 base64 = new Base64();
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final Cipher cipher = Cipher.getInstance(PADDING);

            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherText = cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64.encodeToString(cipherText);
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        Base64 base64 = new Base64();
        byte[] dectyptedText = null;
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);

            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            dectyptedText = cipher.doFinal(base64.decode(data));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        byte[] cipherText = null;
        Base64 base64 = new Base64();
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);

            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            cipherText = cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64.encodeToString(cipherText);
    }

    /**
     * 公钥解密
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        Base64 base64 = new Base64();
        byte[] dectyptedText = null;
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final Cipher cipher = Cipher.getInstance(PADDING, PROVIDER);

            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            dectyptedText = cipher.doFinal(base64.decode(data));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    // 1 -> / 2-> +
    public static String cipherToEXcode(String cipher) {
        StringBuffer cipherModel = new StringBuffer(cipher);
        for (int i = 0; i < cipher.length(); i++) {
            if (cipher.charAt(i) == '/') {
                if ((65 + i) > 90) {
                    cipherModel.append((char)(65 + i + 6));
                }
                else {
                    cipherModel.append((char)(65 + i));
                }
                cipherModel.append(1);
            }
            if (cipher.charAt(i) == '+') {
                if ((65 + i) > 90) {
                    cipherModel.append((char)(65 + i + 6));
                }
                else {
                    cipherModel.append((char)(65 + i));
                }
                cipherModel.append(2);
            }
        }
        cipher = String.valueOf(cipherModel).replaceAll("[+, /]", "").replaceAll("=", "-");

        return cipher;
    }

    public static String eXcodeToCipher(String cipher) {
        StringBuffer stringBuffer = new StringBuffer(cipher);
        int index = cipher.indexOf("-");
        for (int n = index; n < cipher.length(); n += 2) {
            if (cipher.charAt(n) == '2') {
                if (cipher.charAt(n - 1) > 90) {
                    stringBuffer.insert(cipher.charAt(n - 1) - 65 - 6,'+');
                }
                else {
                    stringBuffer.insert(cipher.charAt(n - 1) - 65,'+');
                }
            }
            if (cipher.charAt(n) == '1') {
                if (cipher.charAt(n - 1) > 90) {
                    stringBuffer.insert(cipher.charAt(n - 1) - 65 - 6,'/');
                }
                else {
                    stringBuffer.insert(cipher.charAt(n - 1) - 65,'/');
                }
            }
        }
        cipher = String.valueOf(stringBuffer).replaceAll("-", "=").substring(0,28);
        return cipher;
    }

    // 获取密钥后将密钥保存在21 22行key中
    public static void main(String[] args) {
        // 创建密钥对
        Map<String, String> keys = RSAUtils.createKeys(160);
        // 从Map中获取密钥对
        String publicKey = keys.get("publicKey");
        String privateKey = keys.get("privateKey");
        // 获取公钥
        System.out.println("publicKey:" + publicKey);
        // 获取私钥
        System.out.println("privateKey:" + privateKey);
        // 验证
//        System.out.println(RSAUtils.cipherToEXcode("CPv8/xpLgMdLyBT/HSRSikIEIzo="));
//        System.out.println(RSAUtils.eXcodeToCipher("CPv8xpLgMdLyBTHSRSikIEIzo-E1P1").equals("CPv8/xpLgMdLyBT/HSRSikIEIzo="));
        // 实例加密解密方案
//        try {
//            final String originalText = "25261754166600546112";
//            String cipherText = RSAUtils.publicEncrypt(originalText, RSAUtils.getPublicKey(RSAUtils.publicKey));
//            String plainText = RSAUtils.privateDecrypt(cipherText, RSAUtils.getPrivateKey(RSAUtils.privateKey));
//            System.out.println(cipherText);
//            // c42HvOUoKTcu2wXwR/pZXDibAd4=
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
