/** 
 * @项目名称: FSP
 * @文件名称: LogoutVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import java.util.Date;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.LogoutVo;

public class LogoutVoConverter extends ResponseXmlConverter {

	public LogoutVoConverter() {
		this("xml");
	}

	public LogoutVoConverter(String contentType) {
		super(contentType);
		changeField();
	}

	public void changeField() {
		this.aliasClass("logout", LogoutVo.class);
	}

	public static void main(String[] args) {
		LogoutVo logoutVo = new LogoutVo();
		logoutVo.setEmpCode("tanchong");
		logoutVo.setLogoutTime(new Date());
		LogoutVoConverter convert = new LogoutVoConverter();
		System.out.println(convert.marshal(logoutVo));
	}
}
