package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

@Table("omg_portal_role")
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
    private String createOrg;

    /** 备注. */
    private String remark;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;
    
    //角色级别
    private Integer roleLevel;
    
    //作用范围
    private String roleScope;
    
    //角色类型
    private String category;

    /** 分配机构类型*/
    private String assignsOrgType;
    
    public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleScope() {
		return roleScope;
	}

	public void setRoleScope(String roleScope) {
		this.roleScope = roleScope;
	}

	/** 取得 角色code. */
    public String getRoleCode() {
        return this.roleCode;
    }

    /** 设置 角色code. */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    /** 取得 角色名称. */
    public String getRoleName() {
        return this.roleName;
    }

    /** 设置 角色名称. */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    /** 取得 删除标识. */
    public Boolean getDeleteFlag() {
        return this.deleteFlag;
    }

    /** 设置 删除标识. */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    /** 取得 创建人. */
    public String getCreateEmp() {
        return this.createEmp;
    }

    /** 设置 创建人. */
    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }
    /** 取得 创建机构. */
    public String getCreateOrg() {
        return this.createOrg;
    }

    /** 设置 创建机构. */
    public void setCreateOrg(String createOrg) {
        this.createOrg = createOrg;
    }
    /** 取得 备注. */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注. */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /** 取得 更新人. */
    public String getUpdateEmp() {
        return this.updateEmp;
    }

    /** 设置 更新人. */
    public void setUpdateEmp(String updateEmp) {
        this.updateEmp = updateEmp;
    }
    /** 取得 更新机构. */
    public String getUpdateOrg() {
        return this.updateOrg;
    }

    /** 设置 更新机构. */
    public void setUpdateOrg(String updateOrg) {
        this.updateOrg = updateOrg;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getAssignsOrgType() {
		return assignsOrgType;
	}

	public void setAssignsOrgType(String assignsOrgType) {
		this.assignsOrgType = assignsOrgType;
	}

}
