/** 
 * @项目名称: FSP
 * @文件名称: UserSearchVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.UserSearchVo;

/**
 * 认证结果
 * @author tanchong
 *
 */
public class UserSearchVoConverter extends ResponseXmlConverter {

	public UserSearchVoConverter() {
		this("xml");
	}

	public UserSearchVoConverter(String contentType) {
		super(contentType);
		this.xstream.ignoreUnknownElements();
		this.xstream.aliasSystemAttribute(null, "class");
		changeField();
	}

	public void changeField() {
		this.aliasClass("search", UserSearchVo.class);
	}

	public static void main(String[] args) {
		UserSearchVo search = new UserSearchVo();
		search.setUserName("aaa");
		UserSearchVoConverter converter = new UserSearchVoConverter();
		System.out.println(converter.marshal(search));
	}
}
