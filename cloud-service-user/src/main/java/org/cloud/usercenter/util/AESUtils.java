package org.cloud.usercenter.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * java aes 128 ecb 模式
 * 
 * @author fangyunhe
 *
 */
public class AESUtils {
	private static final Logger log = Logger.getLogger(AESUtils.class);

	/**
	 * 原生byte数组加密
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] Encrypt(byte sSrc[], byte sKey[]) throws Exception {
		if (sKey == null) {
			log.error("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length != 16) {
			log.error("Key长度不是16位");
			return null;
		}
		SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc);
		// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		return encrypted;
	}

	/**
	 * 原生byte数组解密
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] Decrypt(byte sSrc[], byte sKey[]) {
		byte[] original = null;
		try {
			// 判断Key是否正确
			if (sKey == null) {
				log.error("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length != 16) {
				log.error("Key长度不是16位");
				return null;
			}
			SecretKeySpec skeySpec = new SecretKeySpec(sKey, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			try {
				original = cipher.doFinal(sSrc);
			} catch (Exception e) {
				log.error("AES解密失败 "+e.getMessage(),e);
				return null;
			}
		} catch (Exception ex) {
			log.error("AES解密失败 "+ex.getMessage(),ex);
		}
		return original;
	}
	

	/**
	 * 字符串加密
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			log.error("Key为空null");
			return null;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			log.error("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		return new Base64().encodeToString(encrypted);
	}

	/**
	 * 字符串解码
	 * 
	 * @param sSrc
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				log.error("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				log.error("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] encrypted1 = new Base64().decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original, "utf-8");
				return originalString;
			} catch (Exception e) {
				log.error(e.toString());
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/*
     * 解密
     * 解密过程：
     * 1.同加密1-4步
     * 2.将加密后的字符串反纺成byte[]数组
     * 3.将加密内容解密
     */
    public static String AESDncode(String encodeRules,String content){
        try {
            //1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            //2.根据ecnodeRules规则初始化密钥生成器
            //生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
              //3.产生原始对称密钥
            SecretKey original_key=keygen.generateKey();
              //4.获得原始对称密钥的字节数组
            byte [] raw=original_key.getEncoded();
            //5.根据字节数组生成AES密钥
            SecretKey key=new SecretKeySpec(raw, "AES");
              //6.根据指定算法AES自成密码器
            Cipher cipher=Cipher.getInstance("AES");
              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            //8.将加密并编码后的内容解码成字节数组
            byte [] byte_content= Base64.decodeBase64(content);
            /*
             * 解密
             */
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        //如果有错就返加nulll
        return null;         
    }

}
