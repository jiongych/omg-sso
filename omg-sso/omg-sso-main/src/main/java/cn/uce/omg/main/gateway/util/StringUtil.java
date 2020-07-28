/** 
 * @项目名称: FSP
 * @文件名称: StringUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.gateway.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符工具类
 * @author tanchong
 *
 */
public class StringUtil {

	/**
	 * string<code>""</code>.
	 * @since 2.0
	 */
	public static final String EMPTY = "";

	/**
	 * 检查字符串是否为null或空值
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.trim().length() == 0;
	}

	/**
	 * 检查字符串是否存在值
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * string属性进行去除前后空格，如果为空返回<code>""</code>
	 * @param str
	 * @return
	 */
	public static String clean(String str) {
		return str == null ? EMPTY : str.trim();
	}

	/**
	 * 检查字符串是否为邮箱格式
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (isEmpty(email))
			return false;
		boolean bool = true;
		String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			bool = false;
		}
		return bool;
	}

	/**
	 * 检查字符串是否为数值类型
	 * @param number
	 * @author liuhaibo
	 * @return
	 */
	public static boolean isNumeric(String number) {
		if (number == null) {
			return false;
		}
		if (number.matches("([1-9]+[0-9]*|0)(\\.[\\d]+)?")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查字符串是否为整数
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		if (number == null) {
			return false;
		}
		if (number.matches("([1-9]+[0-9]*|0)?")) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 测试
	 * @param 
	 * @return
	 */
	public static void main(String[] args) {
		System.err.println(isNumber("65255"));
	}

}
