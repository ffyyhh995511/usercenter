/**
 * 
 */
package org.cloud.usercenter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 业务上的配置
 * @author:fangyunhe
 * @time:2018年1月9日 下午3:40:31
 */

@Component
@ConfigurationProperties(prefix="")
@PropertySource("classpath:config-${spring.profiles.active}/common-config.properties")
public class BusinessConfig {
	public static String redisMasterIp;
	
	public static String redisMasterPort;
	
	public static String redisMasterAuth;
	
	public static String env;
	/**
	 * @return the redisMasterIp
	 */
	public String getRedisMasterIp() {
		return redisMasterIp;
	}
	/**
	 * @param redisMasterIp the redisMasterIp to set
	 */
	public void setRedisMasterIp(String redisMasterIp) {
		this.redisMasterIp = redisMasterIp;
	}
	/**
	 * @return the redisMasterPort
	 */
	public String getRedisMasterPort() {
		return redisMasterPort;
	}
	/**
	 * @param redisMasterPort the redisMasterPort to set
	 */
	public void setRedisMasterPort(String redisMasterPort) {
		this.redisMasterPort = redisMasterPort;
	}
	/**
	 * @return the redisMasterAuth
	 */
	public String getRedisMasterAuth() {
		return redisMasterAuth;
	}
	/**
	 * @param redisMasterAuth the redisMasterAuth to set
	 */
	public void setRedisMasterAuth(String redisMasterAuth) {
		this.redisMasterAuth = redisMasterAuth;
	}
	/**
	 * @return the env
	 */
	public String getEnv() {
		return env;
	}
	/**
	 * @param env the env to set
	 */
	public void setEnv(String env) {
		this.env = env;
	}
	
	
}
