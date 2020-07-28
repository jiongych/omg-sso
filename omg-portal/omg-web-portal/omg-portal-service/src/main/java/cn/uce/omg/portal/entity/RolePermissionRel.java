package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

@Table("omg_portal_role_permission_rel")
public class RolePermissionRel extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 权限id. */
    private String permissionCode;

    /** 角色id. */
    private String roleCode;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;
    
    /** 角色级别*/
    private Integer roleLevel;

    /** 系统编码*/
    private String systemCode;
    
    public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	/** 取得 权限id. */
    public String getPermissionCode() {
        return this.permissionCode;
    }

    /** 设置 权限id. */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    /** 取得 角色id. */
    public String getRoleCode() {
        return this.roleCode;
    }

    /** 设置 角色id. */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
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

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

}
