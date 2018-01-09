package org.cloud.usercenter.util;
/**
 * @author:fangyunhe
 * @time:2018年1月4日 下午6:28:33
 */
public class RandomCodeUtil {
	public static final String codes[] = { "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z","a", "b", "c", "d", "e", "f", "g",
			"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
			"u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7",
			"8", "9" };
	
	/**
	 * 指定长度随机数
	 * @param length
	 * @return
	 */
	public static String getUniqueCode(int length){
		// Math函数库重的random()方法可以产生0至1的随机数
		StringBuffer uniqueCode = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int index = (int) (60 * Math.random());
			uniqueCode.append(codes[index]);
		}
		return uniqueCode.toString();
	
	}
	
	/**
	 * 默认是10长度随机数
	 * @return
	 */
	public static String getUniqueCode() {
		return getUniqueCode(10);
	}
	
}

