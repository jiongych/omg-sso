/** 
 * @项目名称: FSP
 * @文件名称: SecurityUtil 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.util;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uce.sso.sdk.util.codec.DigestUtils;

/**
 * 签名工具类
 */
public class SecurityUtil {

	/** request参数：签名方式 */
	static final String PARAM_SIGN_TYPE = "signType";
	/** 签名方式MD5 */
	static final String SIGN_TYPE_MD5 = "md5";
	/** request参数：签名 */
	static final String PARAM_SIGN = "dataSign";

	/**
	 * 签名
	 * @param params 签名参数
	 * @param signType 签名类型
	 * @param secrtkey 秘钥
	 * @param charset 格式
	 * @return
	 * @throws Exception
	 */
	public static String sign(Map<String, String> params, String signType, String secrtkey, String charset) throws Exception {
		params = paraFilter(params);
		String prestr = createLinkString(params); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		prestr = prestr + secrtkey; // 把拼接后的字符串再与安全校验码直接连接起来
		String sign = "";
		if (SIGN_TYPE_MD5.equalsIgnoreCase(signType)) {
			sign = md5(prestr, charset);
		}
		return sign;
	}

	/**
	 * 除去数组中的空值和签名参数
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
			if (StringUtil.isEmpty(value) || key.equalsIgnoreCase(PARAM_SIGN) || key.equalsIgnoreCase(PARAM_SIGN_TYPE)) {
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
	 * @param text 明文
	 * @param charset 明文编码
	 * @return 密文
	 * @throws GatewayException
	 */
	public static String md5(String text, String charset) throws Exception {
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
	private static byte[] getContentBytes(String content, String charset) throws Exception {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		return content.getBytes(charset);
	}
}
