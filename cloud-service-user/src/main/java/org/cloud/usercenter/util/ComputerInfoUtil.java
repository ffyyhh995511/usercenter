package org.cloud.usercenter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ComputerInfoUtil {

	private static String macAddressStr = null;
	private static String computerName = System.getenv().get("COMPUTERNAME");

	private static final String[] WINDOWSCOMMAND = { "ipconfig", "/all" };
	private static final String[] LINUXCOMMAND = { "/sbin/ifconfig", "-a" };
	private static final Pattern MACPATTERN = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
			Pattern.CASE_INSENSITIVE);

	/**
	 * 获取多个网卡地址
	 * 
	 * @return
	 * @throws IOException
	 */
	private final static List<String> getMacAddressList() throws IOException {
		final ArrayList<String> macAddressList = new ArrayList<String>();
		final String os = System.getProperty("os.name");
		final String command[];

		if (os.startsWith("Windows")) {
			command = WINDOWSCOMMAND;
		} else if (os.startsWith("Linux")) {
			command = LINUXCOMMAND;
		} else {
			throw new IOException("Unknow operating system:" + os);
		}
		// 执行命令
		final Process process = Runtime.getRuntime().exec(command);

		BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		for (String line = null; (line = bufReader.readLine()) != null;) {
			Matcher matcher = MACPATTERN.matcher(line);
			if (matcher.matches()) {
				macAddressList.add(matcher.group(1).replaceAll("-", ":"));
			}
		}

		process.destroy();
		bufReader.close();
		return macAddressList;
	}

	/**
	 * 获取一个网卡地址（多个网卡时从中获取一个）
	 * 
	 * @return
	 */
	public static String getMacAddress() {
		if (macAddressStr == null || macAddressStr.equals("")) {
			StringBuffer sb = new StringBuffer(); // 存放多个网卡地址用，目前只取一个非0000000000E0隧道的值
			try {
				List<String> macList = getMacAddressList();
				for (Iterator<String> iter = macList.iterator(); iter.hasNext();) {
					String amac = iter.next();
					if (amac != null && !amac.equals("0000000000E0")) {
						sb.append(amac);
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			macAddressStr = sb.toString();

		}

		return macAddressStr;
	}

	/**
	 * 获取电脑名
	 * 
	 * @return
	 */
	public static String getComputerName() {
		if (computerName == null || computerName.equals("")) {
			computerName = System.getenv().get("COMPUTERNAME");
		}
		return computerName;
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @return
	 */
	public static String getIpAddrAndName() throws IOException {
		return InetAddress.getLocalHost().toString();
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @return
	 */
	public static String getIpAddr() throws IOException {
		return InetAddress.getLocalHost().getHostAddress().toString();
	}

	/**
	 * 获取电脑唯一标识
	 * 
	 * @return
	 */
	public static String getComputerID() {
		String id = getMacAddress();
		if (id == null || id.equals("")) {
			try {
				id = getIpAddrAndName();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return computerName;
	}

	/**
	 * 限制创建实例
	 */
	private ComputerInfoUtil() {

	}

//	public static void main(String[] args) throws IOException {
//		System.out.println(ComputerInfoUtil.getMacAddress());
//		System.out.println(ComputerInfoUtil.getComputerName());
//		System.out.println(ComputerInfoUtil.getIpAddr());
//		System.out.println(ComputerInfoUtil.getIpAddrAndName());
//	}
}