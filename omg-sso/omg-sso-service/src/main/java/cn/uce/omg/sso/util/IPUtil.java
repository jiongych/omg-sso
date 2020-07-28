/** 
 * @项目名称: FSP
 * @文件名称: IPUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.uce.utils.StringUtil;

/**
 * ip
 * @author huangting
 * @date 2017年6月9日 下午6:17:26
 */
public class IPUtil {
	protected static final Log LOG = LogFactory.getLog(IPUtil.class);

	/**
	 * 获取远程主机
	 * @param request
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午6:17:40
	 */
	public static String getRemoteHost(HttpServletRequest request) {
		String ip = null;
		try {
			ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} catch (Exception e) {
			LOG.error("获取ip失败", e);
		}
		return ip;
	}

	/**
	 * 判断是否是一个IP
	 * @param ip
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午6:18:36
	 */
	public static boolean isIp(String ip) {
		if (StringUtil.isBlank(ip)) {
			return false;
		}
		boolean b = false;
		ip = ip.trim();
		if (ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String s[] = ip.split("\\.");
			if (Integer.parseInt(s[0]) < 255
					&& Integer.parseInt(s[1]) < 255
					&& Integer.parseInt(s[2]) < 255
					&& Integer.parseInt(s[3]) < 255) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 根据ip地址获取mac地址
	 * @param ip
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午6:18:53
	 */
	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			if (StringUtil.isBlank(ip)) {
				return null;
			}
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			LOG.error("getMACAddress error", e);
		}
		return macAddress;
	}
}
