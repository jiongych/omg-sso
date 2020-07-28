/** 
 * @项目名称: FSP
 * @文件名称: AuthCheckVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import java.util.Date;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.AuthCheckVo;

public class AuthCheckVoConverter extends ResponseXmlConverter {

	public AuthCheckVoConverter() {
		this("xml");
	}

	public AuthCheckVoConverter(String contentType) {
		super(contentType);
		changeField();
	}

	public void changeField() {
		this.aliasClass("authCheck", AuthCheckVo.class);
	}

	public static void main(String[] args) {
		AuthCheckVoConverter convert = new AuthCheckVoConverter();
		AuthCheckVo authCheckVo = new AuthCheckVo();
		authCheckVo.setSystemCode("OMG_CAS");
		authCheckVo.setTokenId("iS/Dq64mX/v4Yq+uHKc45H6jmp+Sg56eiZAE0EXgd0f6iv5V5KT6Ag==");
		authCheckVo.setCurrentTime(new Date());
		authCheckVo.setEmpCode("019327");
		
		System.out.println(convert.marshal(authCheckVo));
		StringBuffer sbStr = new StringBuffer();
		sbStr.append("	<tokenId>kxcP3HFlFoXkZ/2zlcISR9J3XJC30sKeKA5UQW6VqM/6iv5V5KT6Ag==</tokenId>");
		System.out.println(convert.unmarshal(sbStr.toString()));
	}
}
