package org.cloud.usercenter.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;


/**
 * 用rsa做数据的加解密
 * 
 * @author fangyunhe 
 * 2017年7月24日下午10:05:05
 */
public class RsaUtil {

	public static final String KEY_ALGORITHM = "RSA";
	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	
	/**
	 * 初始化
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {
		// 获得对象 KeyPairGenerator 参数 RSA 1024个字节
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		// 通过对象 KeyPairGenerator 获取对象KeyPair
		KeyPair keyPair = keyPairGen.generateKeyPair();

		// 通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		// 公私钥对象存入map中
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	
	/**
	 * base64对字符串解码返回byte数组
	 * @param key
	 * @return
	 * @throws Exception
	 */
    public static byte[] decryptBASE64(String key) throws Exception {
    	return Base64.decodeBase64(key);
    }

    /**
     * base64对byte数组编码为字符串
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
    	return Base64.encodeBase64String(key);
    }
    
    /**
     * 获取公钥
     * 获得map中的公钥对象 转为key对象
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        //byte[] publicKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }
    
    /**
     * 获得私钥
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        //获得map中的私钥对象 转为key对象
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        //byte[] privateKey = key.getEncoded();
        //编码返回字符串
        return encryptBASE64(key.getEncoded());
    }
    
    /** 
     * 公钥加密过程 
     *  
     * @param publicKey 
     *            公钥 
     * @param plainTextData 
     *            明文数据 
     * @return 
     * @throws Exception 
     *             加密过程中的异常信息 
     */  
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)  
            throws Exception {  
        if (publicKey == null) {  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  
            return null;
        } catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");  
        }  
    } 
    
    
    
    /** 
     * 私钥解密过程 
     *  
     * @param privateKey 
     *            私钥 
     * @param cipherData 
     *            密文数据 
     * @return 明文 
     * @throws Exception 
     *             解密过程中的异常信息 
     */  
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }  
    }  
    
    
    /**
     * 公钥字符串转换为公钥对象
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = decryptBASE64(publicKeyStr);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }
    
    /**
     * 私钥字符串转换为私钥对象
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = decryptBASE64(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("私钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥数据为空");  
        }  
    }
    
    /**
     * 读取公钥的字符串内容，对原始数据进行加密
     * @param plainTextData
     * @param publicKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String plainTextData,String publicKeyStr) throws Exception {
    	byte[] buffer = decryptBASE64(publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        // 使用默认RSA
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");  
        // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(plainTextData.getBytes("UTF-8"));  
        String encryptData = Base64.encodeBase64String(output);
        return encryptData;
    }
    
    /**
     * 读取私钥的字符串内容，对数据解密
     * @param encryptData
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String decryptionByPrivateKey(String encryptData,String privateKeyStr) throws Exception{
    	byte[] buffer = decryptBASE64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        byte[] encryptByteData = Base64.decodeBase64(encryptData);
        // 使用默认RSA
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] output = cipher.doFinal(encryptByteData);
        String plainTextData = new String(output,"UTF-8");
        return plainTextData;
    }
    
//    public static void main(String[] args) {
//        Map<String, Object> keyMap;
//        try {
//            keyMap = initKey();
//            String publicKey = getPublicKey(keyMap);
//            System.out.println("公钥");
//            System.out.println(publicKey);
//            String privateKey = getPrivateKey(keyMap);
//            System.out.println("私钥");
//            System.out.println(privateKey);
//            
//            String str = "NeK1Csc9cxK/zc7OjxnfWTiy/WrWs1PdW5JWhGUnDmq+oXMXMxcNxPhIZ/F27dwJhtgVRIwpji94fDI2nf5YUMPvnDeU6GwOSWp123456789101112134";
//            System.out.println("明文数据："+str);
//            
//			RSAPublicKey loadPublicKeyByStr = loadPublicKeyByStr(publicKey);
//            byte[] encrypt = encrypt(loadPublicKeyByStr, str.getBytes("UTF-8"));
//            String encryptBASE64 = encryptBASE64(encrypt);
//            System.out.println("加密后的密文："+encryptBASE64);
//            
//            RSAPrivateKey loadPrivateKeyByStr = loadPrivateKeyByStr(privateKey);
//            byte[] decrypt = decrypt(loadPrivateKeyByStr, decryptBASE64(encryptBASE64));
//            String a = new String(decrypt,"UTF-8");
//            System.out.println("密文解密："+a);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
