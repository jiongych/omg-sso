/** 
 * @项目名称: FSP
 * @文件名称: ResultRoleVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.util.List;

public class ResultRoleVo {

	private String userName;
	private Integer empId;
	private List<UserRoleVo> userRoleList;
	private List<EmpRoleRelVo> roleRelList;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public List<UserRoleVo> getUserRoleList() {
		return userRoleList;
	}

	public void setUserRoleList(List<UserRoleVo> userRoleList) {
		this.userRoleList = userRoleList;
	}

	public List<EmpRoleRelVo> getRoleRelList() {
		return roleRelList;
	}

	public void setRoleRelList(List<EmpRoleRelVo> roleRelList) {
		this.roleRelList = roleRelList;
	}

}
