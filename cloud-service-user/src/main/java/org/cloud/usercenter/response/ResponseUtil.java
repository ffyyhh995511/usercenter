package org.cloud.usercenter.response;

/**
 * 遍历枚举
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年3月21日 下午3:51:27
 * @version 1.0	
 */
public class ResponseUtil {
	static ResponseEnum2[] values = ResponseEnum2.values();
	public static String getMsgByCode(int code) {
		for (ResponseEnum2 responseEnum : values) {
			if(responseEnum.getCode() == code) {
				return responseEnum.getMsg();
			}
		}
		return null;
	}
}
