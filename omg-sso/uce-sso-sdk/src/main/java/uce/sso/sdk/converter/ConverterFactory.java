/** 
 * @项目名称: FSP
 * @文件名称: ConverterFactory 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter;

import java.util.Map;

import uce.sso.sdk.constant.GatewayConstants;

/**
 * DataConverter工厂类
 * 
 * @author xiezhh
 * 
 */
public class ConverterFactory {

	/** 对象转换成字符串Converter映射定义,通过Spring进行注入，<content_type,<class,converter>> */
	private Map<String, Map<String, IConverter>> toStringConverterMap;

	/** 字符串转换成对象Converter映射定义,通过Spring进行注入，<content_type,<service,converter>> */
	private Map<String, Map<String, IConverter>> toObjectConverterMap;

	/** 默认转换器 */
	private IConverter defaultConverter;

	/**
	 * @param toStringConverterMap the toStringConverterMap to set
	 */
	public void setToStringConverterMap(Map<String, Map<String, IConverter>> toStringConverterMap) {
		this.toStringConverterMap = toStringConverterMap;
	}

	/**
	 * @param toObjectConverterMap the toObjectConverterMap to set
	 */
	public void setToObjectConverterMap(Map<String, Map<String, IConverter>> toObjectConverterMap) {
		this.toObjectConverterMap = toObjectConverterMap;
	}

	public IConverter getToObjectConverter(String serviceName, String contentType) throws Exception {
		Map<String, IConverter> converterMap = toObjectConverterMap.get(contentType);

		IConverter converter = null;
		if (converterMap != null) {
			converter = converterMap.get(serviceName);
		}

		if (converter == null) {
			converter = getDefaultConverter();
		}

		return converter;
	}

	public IConverter getToStringConverter(Class<?> clazz, String contentType) throws Exception {
		Map<String, IConverter> converterMap = toStringConverterMap.get(contentType);

		IConverter converter = null;
		if (converterMap != null) {
			converter = converterMap.get(clazz.getName());
		}

		if (converter == null) {
			converter = getDefaultConverter();
		}

		return converter;
	}

	public IConverter getToStringConverter(Class<?> clazz, String contentType, String key) throws Exception {
		Map<String, IConverter> converterMap = toStringConverterMap.get(contentType);

		IConverter converter = null;
		if (converterMap != null) {
			converter = converterMap.get(key + clazz.getName());
		}

		if (converter == null) {
			converter = getDefaultConverter();
		}

		return converter;
	}

	public IConverter getResponseConverter(String contentType) throws Exception {
		return new ResponseXmlConverter(contentType);
	}

	private IConverter getDefaultConverter() {
		if (this.defaultConverter == null) {
			this.defaultConverter = new XStreamConverter(GatewayConstants.CONTENT_TYPE_XML);
		}
		return this.defaultConverter;
	}

	public void setDefaultConverter(IConverter defaultConverter) {
		this.defaultConverter = defaultConverter;
	}
}
