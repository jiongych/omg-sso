/** 
 * @项目名称: FSP
 * @文件名称: UserRoleRelVo extends ResponseVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import uce.sso.sdk.vo.base.ResponseVo;

/**
 * 用户角色关系
 * @author tanchong
 *
 */
public class UserRoleRelVo extends ResponseVo {

	/**  */
	private static final long serialVersionUID = 1L;

	/** 员工ID. */
	private Integer empId;

	/** 角色编码. */
	private String roleCode;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
