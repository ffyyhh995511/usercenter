package org.cloud.usercenter.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 用BufferedReader的缓存功能读取大文件数据 实测读取3G基站数据文件，内存占用在100M左右
 * 
 * @author fangyunhe
 *
 *         2017年8月28日 下午2:59:07
 */
public class ReadFileUtil {
	
	/**
	 * 读取文件的字符串格式内容
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	public static String readFileString(String filepath) throws Exception {
		StringBuffer sb = new StringBuffer();
		File file = new File(filepath);
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"), 5 * 1024 * 1024);// 用5M的缓冲读取文本文件
		String line = "";
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		reader.close();
		fis.close();
		return sb.toString();
	}
}
