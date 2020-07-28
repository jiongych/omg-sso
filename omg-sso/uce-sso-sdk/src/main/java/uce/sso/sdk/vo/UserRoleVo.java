/** 
 * @项目名称: FSP
 * @文件名称: UserRoleVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;

/**
 * 用户角色Vo，返回用户所属的角色信息
 * @author tanchong
 *
 */
public class UserRoleVo implements Serializable {

	/**  */
	private static final long serialVersionUID = 1L;

	/** 角色code. */
	private String roleCode;

	/** 角色名称. */
	private String roleName;

	/** 备注. */
	private String remark;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
