/** 
 * @项目名称: FSP
 * @文件名称: ResetPwdVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.ResetPwdVo;

public class ResetPwdVoConverter extends ResponseXmlConverter {

	public ResetPwdVoConverter() {
		this("xml");
	}

	public ResetPwdVoConverter(String contentType) {
		super(contentType);
		changeField();
	}

	public void changeField() {
		this.aliasClass("resetPwd", ResetPwdVo.class);
	}

	public static void main(String[] args) {
//		ResetPwdVo loginVo = new ResetPwdVo();
//		loginVo.setSystemCode("OMG_CAS");
//		loginVo.setIpAddr("192.168.0.118");
//		loginVo.setUserName("tanchong");
//		loginVo.setNewPassword("JFISDFASdfjxc7990775%%*8");
		ResetPwdVoConverter convert = new ResetPwdVoConverter();
		String str = "<resetPwd><systemCode>OMG_CAS</systemCode><restTime>2017-01-03 16:14:42</restTime><userName>019327</userName><newPassword>ec1b8d01cefb52ed477e0c41834f475c</newPassword><passwordStrength>STRONG</passwordStrength><code>210124</code></resetPwd>";
		System.out.println(convert.unmarshal(str));
//		System.out.println(convert.marshal(loginVo));
	}
}
