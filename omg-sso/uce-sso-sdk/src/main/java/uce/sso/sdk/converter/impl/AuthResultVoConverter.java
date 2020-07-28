/** 
 * @项目名称: FSP
 * @文件名称: AuthResultVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import java.util.ArrayList;
import java.util.List;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.AuthResultVo;
import uce.sso.sdk.vo.UserRoleVo;
import uce.sso.sdk.vo.base.ResponseVo;

/**
 * 认证结果
 * @author tanchong
 *
 */
public class AuthResultVoConverter extends ResponseXmlConverter {

	public AuthResultVoConverter() {
		this("xml");
	}

	public AuthResultVoConverter(String contentType) {
		super(contentType);
		this.xstream.ignoreUnknownElements();
		this.xstream.aliasSystemAttribute(null, "class");
		changeField();
	}

	public void changeField() {
		this.aliasClass("response", ResponseVo.class);
		this.aliasField("data", Object.class, "authResultVo");
		this.aliasClass("authResultVo", AuthResultVo.class);
		
		this.aliasField("userRoleList", UserRoleVo.class, "userRoleList");
		this.aliasClass("userRole", UserRoleVo.class);
	}

	public static void main(String[] args) {
		ResponseVo response = new ResponseVo();
		response.setIsSuccess("T");
		response.setErrorCode("SCCS");

		AuthResultVo resultVo = new AuthResultVo();
		List<UserRoleVo> userRoleList = new ArrayList<UserRoleVo>();
		UserRoleVo role1 = new UserRoleVo();
		role1.setRoleCode("role_code1");
		role1.setRoleName("bbccd");
		UserRoleVo role2 = new UserRoleVo();
		role2.setRoleCode("role_code2");
		role2.setRoleName("aaadd");
		userRoleList.add(role1);
		userRoleList.add(role2);
		resultVo.setUserRoleList(userRoleList);
		resultVo.setUserName("username");

		response.setData(resultVo);
		AuthResultVoConverter convert = new AuthResultVoConverter();
		System.out.println(convert.marshal(response));

		StringBuffer sbStr = new StringBuffer();
		sbStr.append("<response>");
		sbStr.append("	<isSuccess>T</isSuccess>");
		sbStr.append("	<errorCode>SCCS</errorCode>");
		sbStr.append("	<data>");
		sbStr.append("		<isSuccess>F</isSuccess>");
		sbStr.append("		<userName>username</userName>");
		sbStr.append("		<userRoleList>");
		sbStr.append("			<userRole>");
		sbStr.append("				<roleCode>role_code1</roleCode>");
		sbStr.append("				<roleName>bbccd</roleName>");
		sbStr.append("			</userRole>");
		sbStr.append("			<userRole>");
		sbStr.append("				<roleCode>role_code2</roleCode>");
		sbStr.append("				<roleName>aaadd</roleName>");
		sbStr.append("			</userRole>");
		sbStr.append("		</userRoleList>");
		sbStr.append("	</data>");
		sbStr.append("</response>");
		ResponseVo temp = (ResponseVo) convert.unmarshal(sbStr.toString());
		AuthResultVo tempResult = (AuthResultVo) temp.getData();
		System.out.println(tempResult);
		System.out.println(convert.unmarshal(sbStr.toString()));
	}
}
