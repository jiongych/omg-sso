/** 
 * @项目名称: FSP
 * @文件名称: UserSearchVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;

/**
 * 查询vo
 * @author tanchong
 *
 */
public class UserSearchVo implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String roleName;
	private String roleCode;
	private Integer empId;
	
	public String getUserName() {
		return userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
