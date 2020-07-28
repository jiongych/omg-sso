/** 
 * @项目名称: FSP
 * @文件名称: UserRoleVoConverter extends ResponseXmlConverter 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.converter.impl;

import java.util.ArrayList;
import java.util.List;

import uce.sso.sdk.converter.ResponseXmlConverter;
import uce.sso.sdk.vo.EmpRoleRelVo;
import uce.sso.sdk.vo.ResultRoleVo;
import uce.sso.sdk.vo.UserRoleVo;
import uce.sso.sdk.vo.base.UserRoleResponseVo;

/**
 * 认证结果
 * 
 * @author tanchong
 *
 */
public class UserRoleVoConverter extends ResponseXmlConverter {

	public UserRoleVoConverter() {
		this("xml");
	}

	public UserRoleVoConverter(String contentType) {
		super(contentType, UserRoleResponseVo.class);
		this.xstream.ignoreUnknownElements();
		this.xstream.aliasSystemAttribute(null, "class");
		changeField();
	}

	public void changeField() {
		this.aliasClass("result", ResultRoleVo.class);
		this.aliasClass("role", UserRoleVo.class);
		this.aliasClass("roleRel", EmpRoleRelVo.class);
	}

	public static void main(String[] args) {
		ResultRoleVo result = new ResultRoleVo();
		result.setUserName("b");
		List<UserRoleVo> roleList = new ArrayList<UserRoleVo>();
		List<EmpRoleRelVo> relList = new ArrayList<EmpRoleRelVo>();
		UserRoleVo role1 = new UserRoleVo();
		role1.setRoleName("a1");
		EmpRoleRelVo rel1 = new EmpRoleRelVo();
		rel1.setRoleCode("rel001");
		UserRoleVo role2 = new UserRoleVo();
		role2.setRoleName("a2");
		EmpRoleRelVo rel2 = new EmpRoleRelVo();
		rel2.setRoleCode("rel002");
		roleList.add(role1);
		roleList.add(role2);
		relList.add(rel1);
		relList.add(rel2);
		result.setUserRoleList(roleList);
		result.setRoleRelList(relList);
		UserRoleVoConverter converter = new UserRoleVoConverter();
		System.out.println(converter.marshal(result));

		String str = "<result><userName>b</userName><userRoleList><role><roleName>a1</roleName></role><role><roleName>a2</roleName></role></userRoleList><roleRelList><roleRel><roleCode>rel001</roleCode></roleRel><roleRel><roleCode>rel002</roleCode></roleRel></roleRelList></result>";
		ResultRoleVo temp = (ResultRoleVo) converter.unmarshal(str);
		System.out.println(temp.getUserRoleList().size());
		System.out.println(temp.getRoleRelList().get(0).getRoleCode());
	}
}
