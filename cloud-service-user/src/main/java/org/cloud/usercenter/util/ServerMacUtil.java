/**
 * 
 */
package org.cloud.usercenter.util;

import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * @author:fangyunhe
 * @time:2018年1月4日 下午6:28:33
 */
public class ServerMacUtil {
	
	/**
	 * 获取服务器mac地址
	 * @return
	 * @throws Exception
	 */
	public static String getLocalMac() throws Exception {
		InetAddress ia = InetAddress.getLocalHost();
		// 获取网卡，获取地址
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			if (str.length() == 1) {
				sb.append("0" + str);
			} else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}
}
