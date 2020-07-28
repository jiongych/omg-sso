/** 
 * @项目名称: FSP
 * @文件名称: DateUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * DateUtil  
 * @Description: DateUtil  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class DateUtil {

	/** 长日期的字符串格式,"yyyyMMddHHmmss" */
	public static final String LONG_DATE_FORMAT_NO_SIGN_STR = "yyyyMMddHHmmss";
	/** 短日期的字符串格式,"yyyyMMdd" */
	public static final String SHORT_DATE_FORMAT_NO_SIGN_STR = "yyyyMMdd";
	/** 长日期的字符串格式 ,"yyyy-MM-dd HH:mm:ss" */
	public static final String LONG_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	/** 短日期的字符串格式 ,"yyyy-MM-dd" */
	public static final String SHORT_DATE_FORMAT_STR = "yyyy-MM-dd";
	/** 短日期的字符串格式 ,"HH:mm:ss" */
	public static final String SHORT_TIME_FORMAT_STR = "HH:mm:ss";
	/** 日期格式的Map */
	private static final HashMap<String, SimpleDateFormat> dateFormatMap = new HashMap<String, SimpleDateFormat>();

	static {
		dateFormatMap.put(LONG_DATE_FORMAT_NO_SIGN_STR, new SimpleDateFormat(LONG_DATE_FORMAT_NO_SIGN_STR));
		dateFormatMap.put(SHORT_DATE_FORMAT_NO_SIGN_STR, new SimpleDateFormat(SHORT_DATE_FORMAT_NO_SIGN_STR));
		dateFormatMap.put(LONG_DATE_FORMAT_STR, new SimpleDateFormat(LONG_DATE_FORMAT_STR));
		dateFormatMap.put(SHORT_DATE_FORMAT_STR, new SimpleDateFormat(SHORT_DATE_FORMAT_STR));
		dateFormatMap.put(SHORT_TIME_FORMAT_STR, new SimpleDateFormat(SHORT_TIME_FORMAT_STR));
	}

	public static Date parseDate(String date, String parttern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(parttern);
		return sdf.parse(date);
	}

	/**
	 * 根据日期对象,返回"yyyy-MM-dd HH:mm:ss"格式的字符串.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToLongDate(Long time) {
		Date date = new Date(time);
		return formatToDate(date, LONG_DATE_FORMAT_STR);
	}

	/**
	 * 根据日期对象,返回"yyyy-MM-dd HH:mm:ss"格式的字符串.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToLongDate(Date date) {
		return formatToDate(date, LONG_DATE_FORMAT_STR);
	}

	/**
	 * 根据日期对象,返回"yyyy-MM-dd"格式的字符串.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToShortDate(Date date) {
		return formatToDate(date, SHORT_DATE_FORMAT_STR);
	}

	/**
	 * 根据日期对象,返回"yyyy-MM-dd HH:mm:ss"格式的字符串.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToLongDateNosign(Date date) {
		return formatToDate(date, LONG_DATE_FORMAT_NO_SIGN_STR);
	}

	/**
	 * 根据日期对象,返回"yyyy-MM-dd"格式的字符串.
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToShortDateNosign(Date date) {
		return formatToDate(date, SHORT_DATE_FORMAT_NO_SIGN_STR);
	}

	public static String formatToDate(Date date, String pattern) {
		if (pattern == null) {
			return null;
		}
		SimpleDateFormat sdf = dateFormatMap.get(pattern);
		if (sdf == null) {
			sdf = new SimpleDateFormat(pattern);
			dateFormatMap.put(pattern, sdf);
		}
		return sdf.format(date);
	}

	/**
	 * 将给定的时间的时分钟清空,只保留日期部分. 如果传入的时间为空,报空指针错误.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDatePart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 将给定的时间的日期清空(0001-01-01),只保留时间部分. 如果传入的时间为空,报空指针错误.
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTimePart(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, 1);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}

	/**
	 * 将给定的时间的时分钟清空,只保留日期部分. 如果传入的时间为空,报空指针错误.
	 * 
	 * @param date
	 * @return
	 */
	public static Date setDatePart(Date date, Date datePart) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar calPart = Calendar.getInstance();
		calPart.setTime(datePart);
		cal.set(Calendar.YEAR, calPart.get(Calendar.YEAR));
		cal.set(Calendar.MONTH, calPart.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, calPart.get(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * 将给定的时间的日期清空(0001-01-01),只保留时间部分. 如果传入的时间为空,报空指针错误.
	 * 
	 * @param date
	 * @return
	 */
	public static Date setTimePart(Date date, Date timePart) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar calPart = Calendar.getInstance();
		calPart.setTime(timePart);
		cal.set(Calendar.HOUR_OF_DAY, calPart.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, calPart.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, calPart.get(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, calPart.get(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * 比较两个时间是否是同一天. 如果传入的时间为空,报空指针错误.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6)) {
			return true;
		}
		return false;
	}

	/**
	 * 比较两个时间是否是同一时间,日期可不同.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameTime(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)
				&& cal1.get(Calendar.MINUTE) == cal2.get(Calendar.MINUTE) && cal1.get(Calendar.SECOND) == cal2.get(Calendar.SECOND)
				&& cal1.get(Calendar.MILLISECOND) == cal2.get(Calendar.MILLISECOND)) {
			return true;
		}
		return false;
	}

	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 得到指定月的天数. 比如2013年2月.则year=2013,month=2.
	 * */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static String getTimeShort(Date date) {
		return formatToDate(date, SHORT_TIME_FORMAT_STR);
	}

	/**
	 * 返回second秒后的时间
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(java.util.Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 返回minute分钟后的时间
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(java.util.Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 日期加一天
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(java.util.Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 比较日期相隔自然天数 例如：data1:2016-01-18 19:00:35 data2:2016-01-19 23:59:59;返回1（次日）
	 * @param date1 比较小值日期
	 * @param data2 比较大值日期
	 * @return
	 * @author raowenbin 2016-1-20
	 */
	public static long getNaturalDayBetween(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar1 = getTheDayZero(calendar1);

		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		calendar2 = getTheDayZero(calendar2);

		return (calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 返回此日期的零点整，如2014-10-28 19:00:35 返回 2014-10-28 00:00:00
	 * @param date
	 */
	public static Calendar getTheDayZero(Calendar date) {
		Calendar result = (Calendar) date.clone();
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}

	/** 长日期的字符串格式,"yyyyMMddHHmmssSSS" */
	public static final String LONG_DATE_FORMAT_NO_SIGN_STR_SSS = "yyyyMMddHHmmssSSS";

	/**
	 * @Title: getNowTime
	 * @Description: TODO(获取当前时间，返回：20161011190748304格式)
	 * @return 设定文件
	 * @return String 返回类型
	 */
	public static String getNowTime() {
		String str = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT_NO_SIGN_STR_SSS);
		str = sdf.format(date);
		return str;
	}
}
