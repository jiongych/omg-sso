/** 
 * @项目名称: FSP
 * @文件名称: UserRoleRel extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;


/**
 * UserRoleRel extends BaseEntity  
 * @Description: UserRoleRel extends BaseEntity  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UserRoleRel extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /** 员工编号*/
    private String empCode;

    /** 角色编码. */
    private String roleCode;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @return the createEmp
	 */
	public String getCreateEmp() {
		return createEmp;
	}

	/**
	 * @return the createOrg
	 */
	public Integer getCreateOrg() {
		return createOrg;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @param createEmp the createEmp to set
	 */
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	/**
	 * @param createOrg the createOrg to set
	 */
	public void setCreateOrg(Integer createOrg) {
		this.createOrg = createOrg;
	}
}
