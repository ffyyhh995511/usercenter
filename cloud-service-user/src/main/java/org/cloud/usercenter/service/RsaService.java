/**
 * 
 */
package org.cloud.usercenter.service;

import javax.annotation.PostConstruct;

import org.cloud.usercenter.config.BusinessConfig;
import org.cloud.usercenter.util.RsaUtil;
import org.springframework.stereotype.Service;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月5日 下午4:36:43
 */
@Service
public class RsaService {
	private String privateKeyStr;
	private String publicKeyStr;
	
	@PostConstruct
	private void init() throws Exception {
		privateKeyStr = BusinessConfig.getIdRsa();
		publicKeyStr = BusinessConfig.getIdRsaPub();
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
