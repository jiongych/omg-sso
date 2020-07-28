/** 
 * @项目名称: FSP
 * @文件名称: ResultRoleVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.util.List;

/**
 * 角色
 * @author huangting
 * @date 2017年6月9日 下午1:52:55
 */
public class ResultRoleVo {
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 员工id
	 */
	private Integer empId;
	/**
	 * 用户角色集合
	 */
	private List<UserRoleVo> userRoleList;
	/**
	 * 员工关系集合
	 */
	private List<EmpRoleRelVo> roleRelList;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

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
	 * @return the userRoleList
	 */
	public List<UserRoleVo> getUserRoleList() {
		return userRoleList;
	}

	/**
	 * @param userRoleList the userRoleList to set
	 */
	public void setUserRoleList(List<UserRoleVo> userRoleList) {
		this.userRoleList = userRoleList;
	}

	/**
	 * @return the roleRelList
	 */
	public List<EmpRoleRelVo> getRoleRelList() {
		return roleRelList;
	}

	/**
	 * @param roleRelList the roleRelList to set
	 */
	public void setRoleRelList(List<EmpRoleRelVo> roleRelList) {
		this.roleRelList = roleRelList;
	}

}
