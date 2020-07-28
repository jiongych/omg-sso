/** 
 * @项目名称: FSP
 * @文件名称: UserInfoVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.UserInfoVo;
import uce.sso.sdk.vo.base.UserInfoResponseVo;

/**
 * 认证结果
 * @author tanchong
 *
 */
public class UserInfoVoConverter extends ResponseXmlConverter {

	public UserInfoVoConverter() {
		this("xml");
	}

	public UserInfoVoConverter(String contentType) {
		super(contentType, UserInfoResponseVo.class);
		this.xstream.ignoreUnknownElements();
		this.xstream.aliasSystemAttribute(null, "class");
		changeField();
	}

	public void changeField() {
		this.aliasClass("userInfo", UserInfoVo.class);
	}

	public static void main(String[] args) {
		UserInfoVo info = new UserInfoVo();
		info.setEmpName("aaa");
		UserInfoVoConverter converter = new UserInfoVoConverter();
		System.out.println(converter.marshal(info));
	}
}
