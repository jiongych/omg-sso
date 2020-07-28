/** 
 * @项目名称: FSP
 * @文件名称: EmpRoleRelVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;

/**
 * 用户角色关系
 * @author tanchong
 *
 */
public class EmpRoleRelVo implements Serializable {

	/**  
	 * serialVersionUID
	 * */
	private static final long serialVersionUID = 1L;
	/**  
	 * 员工ID
	 * */
	private Integer empId;
	/**  
	 * 角色编码
	 * */
	private String roleCode;

	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
