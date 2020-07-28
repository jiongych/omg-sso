/** 
 * @项目名称: FSP
 * @文件名称: IConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter;

public interface IConverter {
	
	String marshal(Object object);

	Object unmarshal(String source);

}
