/** 
 * @项目名称: FSP
 * @文件名称: GatewayService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.gateway.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.ConversionException;

import cn.uce.omg.main.biz.ServiceProxy;
import cn.uce.omg.main.biz.ServiceProxyFactory;
import cn.uce.omg.main.converter.ConverterFactory;
import cn.uce.omg.main.converter.IConverter;
import cn.uce.omg.main.gateway.util.RequestUtils;
import cn.uce.omg.main.gateway.vo.RequestVo;
import cn.uce.omg.sso.biz.ISystemInfoBiz;
import cn.uce.omg.sso.constant.ErrorCode;
import cn.uce.omg.sso.constant.GatewayConstants;
import cn.uce.omg.sso.exception.GatewayException;
import cn.uce.omg.sso.util.SignUtils;
import cn.uce.omg.sso.vo.ResponseVo;
import cn.uce.omg.vo.SystemInfoVo;

/**
 * http请求service
 * @author huangting
 * @date 2017年6月9日 下午12:05:18
 */
public class GatewayService {

	private Log log = LogFactory.getLog(GatewayService.class);
	/** 数据转换工厂  */
	private ConverterFactory converterFactory;
	/** service代理工厂 */
	private ServiceProxyFactory serviceProxyFactory;
	/** 系统信息缓存  */
	private ISystemInfoBiz systemInfoBiz;
	/** 下载路径  */
	private String downloadPath;

	/**
	 * 参数检查
	 * @param requestVo
	 * @param request
	 * @throws GatewayException
	 */
	public void requestParamCheck(RequestVo requestVo, HttpServletRequest request) throws GatewayException {
		if (requestVo == null || StringUtils.isEmpty(requestVo.getSystemCode()) || StringUtils.isEmpty(requestVo.getServiceName()) || StringUtils.isEmpty(requestVo.getSign())) {
			if (request != null) {
				log.warn("必填参数为空:" + JSONObject.toJSONString(requestVo));
			}
			throw new GatewayException(ErrorCode.ILLEGAL_PARAM);
		}
        if (log.isInfoEnabled()) {
            log.info("http请求参数为:" + JSONObject.toJSONString(requestVo));
        }
		// 默认charset为UTF-8
		if (StringUtils.isEmpty(requestVo.getCharset())) {
			requestVo.setCharset(GatewayConstants.DEFAULT_CHARSET);
		}

		// 默认content_type为XML
		if (StringUtils.isEmpty(requestVo.getContentType())) {
			requestVo.setContentType(GatewayConstants.CONTENT_TYPE_XML);
		}

		// 合作伙伴身份检查
		partnerCheck(requestVo);

		// 签名检查
		signCheck(request, requestVo);
	}

	/**
	 * 系统身份检查
	 * @param requestVo
	 * @throws GatewayException
	 * @author huangting
	 * @date 2017年6月9日 下午12:06:38
	 */
	private void partnerCheck(RequestVo requestVo) throws GatewayException {
//		String securityKey = (String) PropertyConfigurer.getContextProperty(requestVo.getPartnerId());
		SystemInfoVo systemInfo;
		try {
			systemInfo = systemInfoBiz.findSystemBySystemCode(requestVo.getSystemCode());
		} catch (Exception e) {
			log.error("查询systemInfo信息异常：" + e, e);
			throw new GatewayException(e, ErrorCode.SYSTEM_ERROR);
		}
		// 从缓存中获取数据
		if (systemInfo == null || StringUtils.isEmpty(systemInfo.getSecretKey())) {
			throw new GatewayException(ErrorCode.ILLEGAL_PARTNER);
		}
		String securityKey = systemInfo.getSecretKey();
		requestVo.setSecrtKey(securityKey);
//		if (StringUtils.isNotBlank(securityKey)) {
//		} else {
//			throw new GatewayException(ErrorCode.ILLEGAL_PARTNER);
//		}
	}

	/**
	 * 签名检查
	 * @param request
	 * @param requestVo
	 * @throws GatewayException
	 * @author huangting
	 * @date 2017年6月9日 下午12:06:22
	 */
	private void signCheck(HttpServletRequest request, RequestVo requestVo) throws GatewayException {
		// 固定签名方式为MD5
		requestVo.setSignType(GatewayConstants.SIGN_TYPE_MD5);

		Map<String, String> params = RequestUtils.getRequestParamMap(request);
		try {
			String mysign = SignUtils.sign(params, requestVo.getSignType(), requestVo.getSecrtKey(), requestVo.getCharset());
			if (!mysign.equals(requestVo.getSign())) {
				log.warn("sso签名为:" + mysign + ",外部签名为:" +requestVo.getSign());
				throw new GatewayException(ErrorCode.ILLEGAL_SIGN);
			}

		} catch (UnsupportedEncodingException e) {
			throw new GatewayException(ErrorCode.ILLEGAL_ENCODING);
		}
	}

	/**
	 * 调用业务服务组件
	 * 
	 * @param requestVo
	 * @return ResponseDto
	 * @throws GatewayException
	 */
	public String serviceInvokeString(RequestVo requestVo) throws GatewayException {
		try {
			IConverter converter = converterFactory.getToObjectConverter(requestVo.getServiceName(), requestVo.getContentType());
			Object data = null;
			// 根据content的值,获取VO对象.
			if (!StringUtils.isEmpty(requestVo.getContent())) {
				data = converter.unmarshal(requestVo.getContent());
			}
//			Object result = null;
			// 调用远程接口,取得返回信息.
			ServiceProxy service = serviceProxyFactory.getServiceProxy(requestVo.getServiceName());
//			if(StringUtils.isEmpty(requestVo.getCustomerId())){
			Object result = service.execute(requestVo.getSystemCode(), data);
//			}else{//客户编号不为空时，调用的为网上营业厅登陆后所有接口，都需要传客户编号(userId)过来
//				result = service.execute(requestVo.getPartnerId(),requestVo.getCustomerId(), data);
//			}
			ResponseVo responseVo = new ResponseVo(ResponseVo.TRUE);
			String resultString = null;
			if (null != result) {
				if (result instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> obj = (List<Object>) result;
					if (!obj.isEmpty()) {
						responseVo.setDataList(obj);
						converter = converterFactory.getToStringConverter(obj.get(0).getClass(), requestVo.getContentType(), "list:");
					}
				} else if (result instanceof Boolean) {
					Boolean bl = (Boolean) result;
					responseVo.setIsSuccess(bl ? ResponseVo.TRUE : ResponseVo.FALSE);
					converter = converterFactory.getResponseConverter(requestVo.getContentType());
				} else if (result instanceof String) {
					responseVo.setData(result);
					converter = converterFactory.getResponseConverter(requestVo.getContentType());
				} else {
					responseVo.setData(result);
					converter = converterFactory.getToStringConverter(result.getClass(), requestVo.getContentType());
				}
			} else {
				converter = converterFactory.getResponseConverter(requestVo.getContentType());
			}
//			if ("auth_check".equals(requestVo.getServiceName())) {
//				AuthResultVoConverter conver = new AuthResultVoConverter();
//				resultString = conver.marshal(responseVo.getData());
//			} else {
				resultString = converter.marshal(responseVo);
//			}
			// 处理html字符转义问题，由于query_bill_trace接口需返回html文本给调用方，需将XStream转换后的特殊字符还原
//			if ("query_bill_trace".equalsIgnoreCase(requestVo.getServiceName())) {
//				return decodeString(resultString);
//			}
			return resultString;
		} catch (GatewayException gatewayException) {
			log.error("业务异常：", gatewayException);
			throw gatewayException;
		} catch (Exception e) {
			if (e instanceof ConversionException) {
				log.error("调用转换器异常：" + e);
				throw new GatewayException(e, ErrorCode.ILLEGAL_PARAM);
			}
			log.error("系统异常：" + e, e);
			throw new GatewayException(e, ErrorCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 调用业务服务组件 REQ #1350
	 * @param requestVo
	 * @return ResponseDto
	 * @throws GatewayException
	 * @author majun 2015-07-17
	 */
	public ResponseVo serviceInvoke(RequestVo requestVo) throws GatewayException {
		try {
			IConverter converter = converterFactory.getToObjectConverter(requestVo.getServiceName(), requestVo.getContentType());
			Object data = null;
			// 根据content的值,获取VO对象.
			if (!StringUtils.isEmpty(requestVo.getContent())) {
				data = converter.unmarshal(requestVo.getContent());
			}
			// 调用远程接口,取得返回信息.
			ServiceProxy service = serviceProxyFactory.getServiceProxy(requestVo.getServiceName());
			Object result = service.execute(requestVo.getSystemCode(), data);
			ResponseVo responseVo = new ResponseVo(ResponseVo.TRUE);
			if (null != result) {
				if (result instanceof List) {
					@SuppressWarnings("unchecked")
					List<Object> obj = (List<Object>) result;
					if (!obj.isEmpty()) {
						responseVo.setDataList(obj);
					}
				} else if (result instanceof Boolean) {
					Boolean bl = (Boolean) result;
					responseVo.setIsSuccess(bl ? ResponseVo.TRUE : ResponseVo.FALSE);
				} else if (result instanceof String) {
					responseVo.setData(result);
				} else {
					responseVo.setData(result);
				}
			}
			return responseVo;
		} catch (GatewayException gatewayException) {
			throw gatewayException;
		} catch (Exception e) {
			log.error("系统异常：" + e);
			throw new GatewayException(e, ErrorCode.SYSTEM_ERROR);
		}
	}

	/**
	 * 输出应答结果
	 * 
	 * @param requestVo 请求参数对象
	 * @param responseVo 应签结果对象
	 * @param response HttpServletResponse
	 */
	public void outputResponse(RequestVo requestVo, String responseString, HttpServletResponse response) {

		//设置内容格式
		String contentType = GatewayConstants.CONTENT_TYPE_XML;
		if (requestVo != null && !StringUtils.isEmpty(requestVo.getContentType())) {
			contentType = requestVo.getContentType();
		}

		//设置字符编码
		String charset = GatewayConstants.DEFAULT_CHARSET;
		if (requestVo != null && !StringUtils.isEmpty(requestVo.getCharset())) {
			charset = requestVo.getCharset();
		}

		try {
			if (contentType.equalsIgnoreCase(GatewayConstants.CONTENT_TYPE_JSON)) {
				contentType = "application/json";
			} else {
				contentType = "text/xml";
			}
			response.setHeader("Cache-Control", "no-cache");
			response.setContentType(contentType + ";charset=" + charset);
			response.setCharacterEncoding(charset);
			response.getWriter().print(responseString);
		} catch (Exception e) {
			log.error("系统异常：" + e, e);
		}
	}

	/**
	 * 获取返回数据字符串
	 * @param responseVo
	 * @param contentType
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 下午12:06:53
	 */
	public String getResponseString(ResponseVo responseVo, String contentType) {
		try {
			IConverter converter = converterFactory.getResponseConverter(contentType);
			return converter.marshal(responseVo);
		} catch (Exception e) {
			log.error("系统异常：" + e, e);
		}
		return "";
	}

	/**
	 * @param converterFactory the converterFactory to set
	 */
	public void setConverterFactory(ConverterFactory converterFactory) {
		this.converterFactory = converterFactory;
	}

	/**
	 * @param serviceProxyFactory the serviceProxyFactory to set
	 */
	public void setServiceProxyFactory(ServiceProxyFactory serviceProxyFactory) {
		this.serviceProxyFactory = serviceProxyFactory;
	}
	/**
	 * @param systemInfoBiz the systemInfoBiz to set
	 */
	public void setSystemInfoBiz(ISystemInfoBiz systemInfoBiz) {
		this.systemInfoBiz = systemInfoBiz;
	}

	/**
	 * @param downloadPath the downloadPath to set
	 */
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	/**
	 * @param downloadPath the downloadPath to get
	 */
	public String getDownloadPath() {
		return downloadPath;
	}
	
	/**
	 * 特殊字符还原
	 * @param str
	 * @return
	 */
//	private static String decodeString(String str) {
//		if (StringUtil.isEmpty(str)) {
//			return "";
//		}
//		if (str.indexOf("&lt;") > 0) {
//			str = str.replaceAll("&lt;", "<");
//		}
//		if (str.indexOf("&gt;") > 0) {
//			str = str.replaceAll("&gt;", ">");
//		}
//		if (str.indexOf("&quot;") > 0) {
//			str = str.replaceAll("&quot;", "\\\"");
//		}
//		if (str.indexOf("&#xd;") > 0) {
//			str = str.replaceAll("&#xd;", "");
//		}
//		return str;
//	}
}
