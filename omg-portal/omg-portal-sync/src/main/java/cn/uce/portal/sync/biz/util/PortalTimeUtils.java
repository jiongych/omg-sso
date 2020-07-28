package cn.uce.portal.sync.biz.util;

import java.util.Date;

public class PortalTimeUtils {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 如果数据库中更新时间小于接收信息的更新时间，则返回true（可以更新）
	 * </pre>
	 * @return
	 * @return boolean
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午5:22:57
	 */
	public static boolean compareTime(Date sqlTime, Date msgTime) {
		//如果库中更新时间为空，则可以更新
		if (null == sqlTime) {
			return true;
		}
		//如果传参中更新时间为空，则可以更新
		if (null == msgTime) {
			return true;
		}
		//如果参数的更新时间大于数据库中的更新时间，则可以更新
		if (sqlTime.getTime() <= msgTime.getTime()) {
			return true;
		}
		return false;
	}
	
}
