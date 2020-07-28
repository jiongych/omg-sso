/** 
 * @项目名称: FSP
 * @文件名称: ExpCodeVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.ExpCodeVo;


public class ExpCodeVoConverter extends ResponseXmlConverter {

	public ExpCodeVoConverter() {
		this("xml");
	}

	public ExpCodeVoConverter(String contentType) {
		super(contentType);
		changeField();
	}

	public void changeField() {
		this.aliasClass("expCodeVo", ExpCodeVo.class);
	}

	public static void main(String[] args) {
		ExpCodeVo loginVo = new ExpCodeVo();
//		loginVo.setSystemCode("OMG_CAS");
//		loginVo.setIpAddr("192.168.0.118");
//		loginVo.setUserName("tanchong");
//		loginVo.setNewPassword("JFISDFASdfjxc7990775%%*8");
//		ExpCodeVoConverter convert = new ExpCodeVoConverter();
//		System.out.println(convert.marshal(loginVo));
	}
}
