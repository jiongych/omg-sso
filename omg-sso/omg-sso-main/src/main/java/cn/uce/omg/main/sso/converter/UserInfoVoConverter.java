/** 
 * @项目名称: FSP
 * @文件名称: UserInfoVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.sso.converter;

import cn.uce.omg.main.converter.ResponseXmlConverter;
import cn.uce.omg.sso.vo.UserInfoVo;

/**
 * 用户信息转换类
 * @author huangting
 * @date 2017年6月8日 下午1:50:48
 */
public class UserInfoVoConverter extends ResponseXmlConverter {

	public UserInfoVoConverter() {
		this("xml");
	}

	/**
	 * 数据转换
	 * 
	 * @author huangting
	 * @date 2017年6月9日 下午12:09:59
	 */
	public UserInfoVoConverter(String contentType) {
		super(contentType);
		this.xstream.ignoreUnknownElements();
		this.xstream.aliasSystemAttribute(null, "class");
		changeField();
	}
	/**
	 * 数据转换
	 * 
	 * @author huangting
	 * @date 2017年6月9日 下午12:09:59
	 */
	public void changeField() {
		this.aliasClass("userInfo", UserInfoVo.class);
	}
}
