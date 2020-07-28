package cn.uce.omg.sso.util;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.constant.GatewayConstants;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.utils.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据签名工具类
 * @author xiezhh
 *
 */
public final class SignUtils {
	private static Log LOG = LogFactory.getLog(SignUtils.class);
	private SignUtils() {

	}

	/**
	 * 签名
	 * @param params
	 * @param signType
	 * @param secrtkey
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author huangting
	 * @date 2017年6月9日 下午6:25:59
	 */
	public static String sign(Map<String, String> params, String signType, String secrtkey, String charset) throws UnsupportedEncodingException {
		params = paraFilter(params);
		String prestr = createLinkString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		prestr = prestr + secrtkey; // 把拼接后的字符串再与安全校验码直接连接起来

		LOG.warn("拼接后的字符串:" + prestr);
		String sign = "";
		if (AuthConstants.SYSTYPE_CLASS_ENCRYPT_TYPE_MD5.equalsIgnoreCase(signType)) {
			sign = md5(prestr, charset);
		}
		return sign;
	}
	
	/**
	 * 签名
	 * @param unsignedString
	 * @param secrtkey
	 * @param charset
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年6月9日 下午6:26:11
	 */
	public static String sign(String unsignedString, String secrtkey, String charset) throws Exception {
		String prestr = unsignedString + secrtkey; // 把拼接后的字符串再与安全校验码直接连接起来
		return md5(prestr, charset);
	}

	/**
	 * 除去数组中的空值和签名参数
	 * 
	 * @param sArray 签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {

		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}

		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (StringUtil.isEmpty(value) || key.equalsIgnoreCase(GatewayConstants.PARAM_SIGN) || key.equalsIgnoreCase(GatewayConstants.PARAM_SIGN_TYPE)) {
				continue;
			}
			result.put(key, value);
		}

		return result;
	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr;
	}

	/**
	 * 对字符串进行MD5签名
	 * 
	 * @param text 明文
	 * @param charset 明文编码
	 * @return 密文
	 * @throws GatewayException
	 */
	public static String md5(String text, String charset) throws UnsupportedEncodingException {
		return DigestUtils.md5Hex(getContentBytes(text, charset));
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws GatewayException
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) throws UnsupportedEncodingException {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}

//		try {
			return content.getBytes(charset);
//		} catch (UnsupportedEncodingException e) {
//			throw new GatewayException(e, ErrorCode.ILLEGAL_ENCODING);
//		}
	}
}
