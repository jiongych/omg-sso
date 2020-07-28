/** 
 * @项目名称: FSP
 * @文件名称: Role extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;


/**
 * Role extends BaseEntity  
 * @Description: Role extends BaseEntity  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class Role extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
   
    
    /** 角色code. */
    private String roleCode;

    /** 角色名称. */
    private String roleName;

    /** 删除标识. */
    private Boolean deleteFlag;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;
    
    /** 备注. */
    private String remark;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private Integer updateOrg;

	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @return the deleteFlag
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
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
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return the updateEmp
	 */
	public String getUpdateEmp() {
		return updateEmp;
	}

	/**
	 * @return the updateOrg
	 */
	public Integer getUpdateOrg() {
		return updateOrg;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @param updateEmp the updateEmp to set
	 */
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	/**
	 * @param updateOrg the updateOrg to set
	 */
	public void setUpdateOrg(Integer updateOrg) {
		this.updateOrg = updateOrg;
	}
}
