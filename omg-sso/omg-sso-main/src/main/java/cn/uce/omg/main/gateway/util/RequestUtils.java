package cn.uce.omg.main.gateway.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import cn.uce.omg.main.gateway.vo.RequestVo;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.constant.GatewayConstants;
import cn.uce.omg.sso.exception.GatewayException;

/**
 * HTTP Request 工具类
 * @author xiezhh
 *
 */
public final class RequestUtils {

	private RequestUtils() {

	}

	/**
	 * 获取请求参数,将参数保存到RequestDto对象中
	 * 
	 * @param request HttpServletRequest
	 * @return RequestDto对象
	 * @throws GatewayException
	 */
	public static RequestVo getRequestDto(HttpServletRequest request) throws GatewayException {

		String charset = request.getParameter(GatewayConstants.PARAM_CHARSET);
		if (StringUtils.isEmpty(charset)) {
			charset = GatewayConstants.DEFAULT_CHARSET;
		}
		/**
		 * 字符编码
		 */
		RequestVo requestVo = new RequestVo();
		requestVo.setCharset(charset);
		/**
		 * 请求接口名称
		 */
		String service = getRequestParam(request, GatewayConstants.PARAM_SERVICE, charset);
		requestVo.setServiceName(service);
		/**
		 * 系统编号
		 */
		String systemCode = getRequestParam(request, GatewayConstants.SYSTEM_CODE, charset);
		requestVo.setSystemCode(systemCode);

		/*
		 * String signType = getRequestParam(request, Constants.PARAM_SIGN_TYPE, charset); requestVo.setSignType(signType);
		 */
		/**
		 * 签名
		 */
		String sign = getRequestParam(request, GatewayConstants.PARAM_SIGN, charset);
		requestVo.setSign(sign);
		
		/**
		 * request参数：业务参数与返回结果的内容格式: XML/JSON，默认为XML
		 */
		String contentType = getRequestParam(request, GatewayConstants.PARAM_CONTENT_TYPE, charset);
		requestVo.setContentType(contentType);
		
		/**
		 * request参数：业务参数内容
		 */
		String content = getRequestParam(request, GatewayConstants.PARAM_CONTENT, charset);
		requestVo.setContent(content);

		/*
		 * String timestamp = getRequestParam(request, Constants.PARAM_TIMESTAMP, charset); requestVo.setTimestamp(timestamp);
		 */

		/**
		 * 客户编号
		 */
		String customerId = getRequestParam(request, GatewayConstants.PARAM_CUSTOMERID, charset);
		requestVo.setCustomerId(customerId);

		return requestVo;
	}

	/**
	 * 获取请求参数,并将编码转换成UTF-8
	 * 
	 * @param paramName 参数名称
	 * @param charset 请求的编码类型
	 * @return UTF-8的参数值
	 * @throws GatewayException
	 */
	private static String getRequestParam(HttpServletRequest request, String paramName, String charset) throws GatewayException {
		String paramValue = request.getParameter(paramName);
		if (paramValue == null) {
			return null;
		}

		try {
			return new String(paramValue.getBytes(charset), GatewayConstants.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new GatewayException(e, ErrorCode.ILLEGAL_ENCODING);
		}
	}

	/**
	 * 获取参数MAP
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getRequestParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (String name : requestParams.keySet()) {
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			paramMap.put(name, valueStr);
		}

		return paramMap;
	}
}
