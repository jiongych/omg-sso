package cn.uce.omg.sso.util;

import java.io.UnsupportedEncodingException;

/**
 * 字符串util
 * @author huangting
 * @date 2017年6月9日 下午2:53:57
 */
public abstract class StringUtils {

	/**
	 * 变量是否已经初始化
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		int strLen;
		if ((value == null) || ((strLen = value.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否数字
	 * @param obj
	 * @return
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null) {
			return false;
		}
		char[] chars = obj.toString().toCharArray();
		int length = chars.length;
		if (length < 1) {
			return false;
		}
		int i = 0;
		if ((length > 1) && (chars[0] == '-')) {
			i = 1;
		}
		for (; i < length; i++) {
			if (!Character.isDigit(chars[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 数组已经初始化
	 * @param values
	 * @return
	 */
	public static boolean areNotEmpty(String[] values) {
		boolean result = true;
		if ((values == null) || (values.length == 0))
			result = false;
		else {
			for (String value : values) {
				result &= !isEmpty(value);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param unicode
	 * @return
	 * @date 2017年6月9日 下午6:19:45
	 */
	public static String unicodeToChinese(String unicode) {
		StringBuilder out = new StringBuilder();
		if (!isEmpty(unicode)) {
			for (int i = 0; i < unicode.length(); i++) {
				out.append(unicode.charAt(i));
			}
		}
		return out.toString();
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @date 2017年6月9日 下午6:19:41
	 */
	public static String stripNonValidXMLCharacters(String input) {
		if ((input == null) || ("".equals(input)))
			return "";
		StringBuilder out = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char current = input.charAt(i);
			if ((current != '\t') && (current != '\n') && (current != '\r') && ((current < ' ') || (current > 55295)) && ((current < 57344) || (current > 65533))
					&& ((current < 65536) || (current > 1114111))) {
				continue;
			}
			out.append(current);
		}
		return out.toString();
	}

	/**
	 * 
	 * @param string
	 * @return
	 * @date 2017年6月9日 下午6:22:42
	 */
	public static byte[] getBytesIso8859_1(String string) {
		return getBytesUnchecked(string, "ISO-8859-1");
	}
	
	/**
	 * 
	 * @param string
	 * @return
	 * @date 2017年6月9日 下午6:22:42
	 */
	public static byte[] getBytesUsAscii(String string) {
		return getBytesUnchecked(string, "US-ASCII");
	}

	/**
	 * 
	 * @param string
	 * @return
	 * @date 2017年6月9日 下午6:22:42
	 */
	public static byte[] getBytesUtf16(String string) {
		return getBytesUnchecked(string, "UTF-16");
	}

	/**
	 * 
	 * @param string
	 * @return
	 * @date 2017年6月9日 下午6:22:42
	 */
	public static byte[] getBytesUtf16Be(String string) {
		return getBytesUnchecked(string, "UTF-16BE");
	}

	/**
	 * 
	 * @param string
	 * @return
	 * @date 2017年6月9日 下午6:22:42
	 */
	public static byte[] getBytesUtf16Le(String string) {
		return getBytesUnchecked(string, "UTF-16LE");
	}

	/**
	 * 
	 * @param string
	 * @return
	 * @date 2017年6月9日 下午6:22:42
	 */
	public static byte[] getBytesUtf8(String string) {
		return getBytesUnchecked(string, "UTF-8");
	}

	/**
	 * 
	 * @param string
	 * @param charsetName
	 * @return
	 * @date 2017年6月9日 下午6:19:33
	 */
	public static byte[] getBytesUnchecked(String string, String charsetName) {
		if (string == null)
			return null;
		try {
			return string.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {

			throw newIllegalStateException(charsetName, e);
		}
	}

	/**
	 * 
	 * @param charsetName
	 * @param e
	 * @return
	 * @date 2017年6月9日 下午6:20:01
	 */
	private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e) {
		return new IllegalStateException(charsetName + ": " + e);
	}

	/**
	 * 
	 * @param bytes
	 * @param charsetName
	 * @return
	 * @date 2017年6月9日 下午6:19:23
	 */
	public static String newString(byte[] bytes, String charsetName) {
		if (bytes == null)
			return null;
		try {
			return new String(bytes, charsetName);
		} catch (UnsupportedEncodingException e) {
			throw newIllegalStateException(charsetName, e);
		}
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 * @date 2017年6月9日 下午6:20:09
	 */
	public static String newStringIso8859_1(byte[] bytes) {
		return newString(bytes, "ISO-8859-1");
	}
	
	/**
	 * 
	 * @param bytes
	 * @return
	 * @date 2017年6月9日 下午6:20:09
	 */
	public static String newStringUsAscii(byte[] bytes) {
		return newString(bytes, "US-ASCII");
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 * @date 2017年6月9日 下午6:20:09
	 */
	public static String newStringUtf16(byte[] bytes) {
		return newString(bytes, "UTF-16");
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 * @date 2017年6月9日 下午6:20:09
	 */
	public static String newStringUtf16Be(byte[] bytes) {
		return newString(bytes, "UTF-16BE");
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 * @date 2017年6月9日 下午6:20:09
	 */
	public static String newStringUtf16Le(byte[] bytes) {
		return newString(bytes, "UTF-16LE");
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 * @date 2017年6月9日 下午6:20:09
	 */
	public static String newStringUtf8(byte[] bytes) {
		return newString(bytes, "UTF-8");
	}
}
