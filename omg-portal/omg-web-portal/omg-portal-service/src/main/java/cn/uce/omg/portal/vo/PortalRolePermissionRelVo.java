package cn.uce.omg.portal.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class PortalRolePermissionRelVo extends BaseVo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ID. */
    private Long id;

    /** 权限id. */
    private String permissionCode;
    /** 权限name. */
    private String permissionName;

    /** 角色id. */
    private String roleCode;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;

    /** 创建时间. */
    private Date createTime;

    /** 版本. */
    private Integer version;

    /** 更新时间. */
    private Date updateTime;
    
    /** 系统编码*/
    private String systemCode;

    /** 取得 ID. */
    public Long getId() {
        return this.id;
    }

    /** 设置 ID. */
    public void setId(Long id) {
        this.id = id;
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
    public Integer getCreateOrg() {
        return this.createOrg;
    }

    /** 设置 创建机构. */
    public void setCreateOrg(Integer createOrg) {
        this.createOrg = createOrg;
    }
    /** 取得 创建时间. */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间. */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /** 取得 版本. */
    public Integer getVersion() {
        return this.version;
    }

    /** 设置 版本. */
    public void setVersion(Integer version) {
        this.version = version;
    }
    /** 取得 更新时间. */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /** 设置 更新时间. */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}
