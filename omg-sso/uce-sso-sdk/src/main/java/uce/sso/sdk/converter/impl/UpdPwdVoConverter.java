/** 
 * @项目名称: FSP
 * @文件名称: UpdPwdVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.UpdPwdVo;

public class UpdPwdVoConverter extends ResponseXmlConverter {

	public UpdPwdVoConverter() {
		this("xml");
	}

	public UpdPwdVoConverter(String contentType) {
		super(contentType);
		changeField();
	}

	public void changeField() {
		this.aliasClass("updPwd", UpdPwdVo.class);
	}

	public static void main(String[] args) {
		UpdPwdVo loginVo = new UpdPwdVo();
		loginVo.setSystemCode("OMG_CAS");
		loginVo.setIpAddr("192.168.0.118");
		loginVo.setEmpCode("tanchong");
		loginVo.setNewPassword("JFISDFASdfjxc7990775%%*8");
		UpdPwdVoConverter convert = new UpdPwdVoConverter();
		System.out.println(convert.marshal(loginVo));
	}
}
