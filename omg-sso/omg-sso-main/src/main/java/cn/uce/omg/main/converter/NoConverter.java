/** 
 * @项目名称: FSP
 * @文件名称: NoConverter implements IConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.converter;

/**
 * xml转换类
 * @author huangting
 * @date 2017年6月9日 上午11:39:51
 */
public class NoConverter implements IConverter {

	/**
	 * 对象转字符串
	 * @param object
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 上午11:37:32
	 */
	public String marshal(Object object) {
		if (object == null) {
			return null;
		}

		return object.toString();
	}

	/**
	 * 字符串转对象
	 * @param source
	 * @return
	 * @author huangting
	 * @date 2017年6月9日 上午11:37:36
	 */
	public Object unmarshal(String source) {
		return source;
	}

}
