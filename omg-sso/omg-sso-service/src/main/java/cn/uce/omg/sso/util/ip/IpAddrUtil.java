/** 
 * @项目名称: FSP
 * @文件名称: IpAddrUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util.ip;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ip地址工具类
 * @author tanchong
 *
 */
public class IpAddrUtil {
	private static Log log = LogFactory.getLog(IpAddrUtil.class);

	public static String toHex(Integer i) throws Exception {
		return Integer.toHexString(i);
	}

	public static Long toLong(String str) throws Exception {
		return Long.parseLong(str);
	}

	/**
	 * 将ip地址转换成十六进制
	 * @return
	 * @throws Exception
	 */
	public static Long convertIpToLong(String ipAddress) throws Exception {
		if (StringUtils.isEmpty(ipAddress)) {
			return null;
		}
		long result = 0;
		try {
			String[] ipAddressInArray = ipAddress.split("\\.");
			for (int i = 3; i >= 0; i--) {
				long ip = Long.parseLong(ipAddressInArray[3 - i]);
				result |= ip << (i * 8);
			}
		} catch (Exception ex) {
			log.error("ip convert error", ex);
			return null;
		}

		return result;
	}

	/**
	 * ip转换成数字
	 * @param ipAddress
	 * @return
	 */
	public static Long ipToLong(String ipAddress) {
		String[] ipAddressInArray = ipAddress.split("\\.");
		long result = 0;
		try {
			for (int i = 0; i < ipAddressInArray.length; i++) {
				int power = 3 - i;
				int ip = Integer.parseInt(ipAddressInArray[i]);
				result += ip * Math.pow(256, power);

			}
		} catch (Exception ex) {
			log.error("ip convert error", ex);
			return null;
		}
		return result;
	}

	/**
	 * 数字转换成ip
	 * @param i
	 * @return
	 */
	public static String longToIp(long i) {
		try {
			return ((i >> 24) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + (i & 0xFF);
		} catch (Exception ex) {
			log.error("ip convert error", ex);
			return null;
		}
	}

	/**
	 * 数字转换成ip
	 * @param ip
	 * @return
	 */
	public static String longToIp2(long ip) {
		StringBuilder sb = new StringBuilder(15);
		try {
			for (int i = 0; i < 4; i++) {
				sb.insert(0, Long.toString(ip & 0xff));
				if (i < 3) {
					sb.insert(0, '.');
				}
				ip = ip >> 8;

			}
		} catch (Exception ex) {
			log.error("ip convert error", ex);
			return null;
		}
		return sb.toString();
	}

}
