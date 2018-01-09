/**
 * 
 */
package org.cloud.usercenter;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月8日 下午2:35:19
 */
public class TimeTest {
	public static void main(String[] args) {
		long tokenExpire = System.currentTimeMillis() + 7200 * 1000;
		// refreshToken过期默认30天
		long resreshTokenExpire = System.currentTimeMillis() + 24 * 3600 * 30 * 1000;
		System.out.println(tokenExpire);
		
		System.out.println(resreshTokenExpire);
	}
}
