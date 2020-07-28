/** 
 * @项目名称: FSP
 * @文件名称: NoConverter implements IConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter;


public class NoConverter implements IConverter {

	public String marshal(Object object) {
		if (object == null) {
			return null;
		}

		return object.toString();
	}

	public Object unmarshal(String source) {
		return source;
	}

}
