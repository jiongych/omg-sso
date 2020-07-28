/** 
 * @项目名称: FSP
 * @文件名称: AuthUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 认证系统工具类
 * @author tanchong
 *
 */
public class AuthUtil {
	/** 达到最大登录失败次数,下一次登录时间间隔，单位：分钟 */
	private static int intervalTime = 5;

	/**
	 * @param intervalTime the intervalTime to set
	 */
	public static void setIntervalTime(int intervalTime) {
		AuthUtil.intervalTime = intervalTime;
	}

	/**
	 * 根据发送次数获取下一次的推送时间，第一次为5分钟。第二次为10分钟，第三次为20分钟，如果时间超过1天，则返回最大为1天的数据
	 * @param pushNum
	 * @return
	 */
	public static Date getExpireTime(Integer failCount) {
		if (failCount == null) {
			return new Date();
		}
		Calendar cale = Calendar.getInstance();
		Calendar maxCale = Calendar.getInstance();
		maxCale.add(Calendar.DAY_OF_MONTH, 1);
		int minutes = getNextMinutes(failCount.intValue());
		cale.add(Calendar.MINUTE, minutes);
		if (maxCale.getTime().after(cale.getTime())) {
			return cale.getTime();
		} else {
			return maxCale.getTime();
		}
	}

	/**
	 * 获取下一次运行的分钟数
	 * @param num
	 * @return
	 */
	private static int getNextMinutes(int num) {
		int minutes = intervalTime;
		for (int i = 1; i < num; i++) {
			minutes = minutes * 2;
		}
		// 数据溢出
		if (minutes <= 0) {
			return Integer.MAX_VALUE;
		} else {
			return minutes;
		}
	}

}
