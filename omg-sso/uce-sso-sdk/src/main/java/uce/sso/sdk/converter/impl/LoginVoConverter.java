/** 
 * @项目名称: FSP
 * @文件名称: LoginVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.LoginVo;

public class LoginVoConverter extends ResponseXmlConverter {

	public LoginVoConverter() {
		this("xml");
	}

	public LoginVoConverter(String contentType) {
		super(contentType);
		changeField();
	}

	public void changeField() {
		this.aliasClass("login", LoginVo.class);
	}

}
