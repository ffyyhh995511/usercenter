package org.cloud.usercenter.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesUtil {
	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);  
	
	/**
	 * 获取Properties 对象
	 * @param path
	 * @return
	 */
	public static Properties loadProperties(String path) {
		Properties properties = null;
		try {
			properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
		} catch (IOException e) {			
			logger.error(e);
		}
		return properties;
	}
	
	/**
	 * 获取 Properties 对于的值
	 * @param path
	 * @param key
	 * @return
	 */
	public static String getProperties(String path,String key){
		return loadProperties(path).getProperty(key);
	}
}
