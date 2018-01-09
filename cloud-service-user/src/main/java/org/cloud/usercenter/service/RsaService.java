/**
 * 
 */
package org.cloud.usercenter.service;

import javax.annotation.PostConstruct;

import org.cloud.usercenter.util.RsaUtil;
import org.cloud.usercenter.util.ReadFileUtil;
import org.springframework.stereotype.Service;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月5日 下午4:36:43
 */
@Service
public class RsaService {
	private String privateKeyPath = "src/main/resources/rsa/id_rsa";
	private String publicKeyPath = "src/main/resources/rsa/id_rsa.pub";;
	private String privateKeyStr;
	private String publicKeyStr;
	
	@PostConstruct
	private void init() throws Exception {
		privateKeyStr = ReadFileUtil.readFileString(privateKeyPath);
		publicKeyStr = ReadFileUtil.readFileString(publicKeyPath);
	}
	
	public String encryptByPublicKey(String plainTextData) throws Exception{
		String encryptData = RsaUtil.encryptByPublicKey(plainTextData, publicKeyStr);
		return encryptData;
	}
	
	public String decryptionByPrivateKey(String encryptData) throws Exception{
		String decryptData = RsaUtil.decryptionByPrivateKey(encryptData, privateKeyStr);
		return decryptData;
	}
}
