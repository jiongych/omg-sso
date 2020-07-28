/** 
 * @项目名称: FSP
 * @文件名称: XStreamConverter implements IConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.converter;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

/**
 * XSTREAM实现的转换器
 * @author xiezhh
 *
 */
public class XStreamConverter implements IConverter {
	public static final String CONVERTER_TYPE_XML = "xml";
	public static final String CONVERTER_TYPE_JSON = "json";

	protected XStream xstream;
	/** 内容 */
	private String contentType;

	public XStreamConverter() {
		this(CONVERTER_TYPE_XML);
	}

	/**
	 * 
	 * @param contentType
	 */
	public XStreamConverter(String contentType) {
		this.contentType = contentType;
		if (CONVERTER_TYPE_JSON.equalsIgnoreCase(contentType)) {
			xstream = new XStream(new JettisonMappedXmlDriver());
		} else {
			xstream = new XStream();
		}
		xstream.setMode(XStream.NO_REFERENCES);
		this.xstream.ignoreUnknownElements(); // 忽略掉未知的XML元素
		xstream.registerConverter(new DateConverter("yyyy-MM-dd HH:mm:ss", new String[] { "yyyy-MM-dd" }, TimeZone.getDefault()));
	}

  /**
	* 对象转字符串
	* @param object
	* @return 
	* @see cn.uce.omg.main.converter.IConverter#marshal(java.lang.Object)
	*/
	@Override
	public String marshal(Object object) {
		if (object == null) {
			return null;
		}

		StringWriter sw = new StringWriter();
		if (CONVERTER_TYPE_XML.equalsIgnoreCase(contentType)) {
			xstream.marshal(object, new CompactWriter(sw, new XmlFriendlyReplacer("_-", "_")));
		} else {
			xstream.toXML(object, sw);
		}
		return sw.toString();
	}

  /**
	* 字符串转对象
	* @param source
	* @return 
	* @see cn.uce.omg.main.converter.IConverter#unmarshal(java.lang.String)
	*/
	@Override
	public Object unmarshal(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		return xstream.fromXML(source);
	}

	/**
	 * 类名称绑定
	 * @param name 别名
	 * @param type 类
	 */
	public void aliasClass(String name, Class<?> type) {
		xstream.alias(name, type);
	}

	/**
	 * 字段名称绑定
	 * @param alias 别名
	 * @param definedIn 类
	 * @param fieldName 字段名
	 */
	public void aliasField(String alias, Class<?> definedIn, String fieldName) {
		xstream.aliasField(alias, definedIn, fieldName);
	}
	/**
	 * 测试
	 * @param 
	 * @param 
	 * @param 
	 */
	public static void main(String[] args) {
		/*XStreamConverter converter = new XStreamConverter("json");
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "aaa");
		System.out.println(converter.marshal(map));

		XStream xstream = new XStream(new JettisonMappedXmlDriver());
		xstream.setMode(XStream.NO_REFERENCES);

		System.out.println(xstream.toXML(map));*/
	}
}
