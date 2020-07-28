package cn.uce.portal.sync.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class PermissionVo extends BaseVo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ID. */
    private Long id;

    /** 权限code. */
    private String permissionCode;

    /** 权限名称. */
    private String permissionName;

    /** 权限路径. */
    private String permissionUrl;

    /** 权限图片. */
    private String permissionIcon;

    /** 组件类型.1-菜单，2-按钮 */
    private Integer controlType;

    /** 父级权限.顶级机构为null */
    private Long parentPermission;

    /** 排序.同一个级别菜单排序 */
    private Integer sort;

    /** 删除标识. */
    private Integer deleteFlag;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;

    /** 创建时间. */
    private Date createTime;

    /** 版本. */
    private Integer version;

    /** 更新人. */
    private String updateEmp;

    /** 更新时间. */
    private Date updateTime;

    /** 更新机构. */
    private String updateOrg;

    /** 是否是叶子节点 */
    private Boolean leafFlag;
    
    /**系统名称*/
    private String sysName;
    
	/**系统路径*/
    private String sysUrl;
    
    /**所属系统*/
    private String systemCode;
    
    /** 权限类型*/
    private String category;
    
    //权限级别
    private String permissionLevels;
    
    //旧权限级别
    private String OldPermissionLevels;
    
    //角色码字符串
    private String roleCodeStr;
    
    private int isHide;
    
    private String filterSystemCode;
    
    private Boolean emptyFlag;
    
	/**
	 * @return the isHide
	 */
	public int getIsHide() {
		return isHide;
	}

	/**
	 * @param isHide the isHide to set
	 */
	public void setIsHide(int isHide) {
		this.isHide = isHide;
	}

	public String getRoleCodeStr() {
		return roleCodeStr;
	}

	public void setRoleCodeStr(String roleCodeStr) {
		this.roleCodeStr = roleCodeStr;
	}

	public String getOldPermissionLevels() {
		return OldPermissionLevels;
	}

	public void setOldPermissionLevels(String oldPermissionLevels) {
		OldPermissionLevels = oldPermissionLevels;
	}

	public String getPermissionLevels() {
		return permissionLevels;
	}

	public void setPermissionLevels(String permissionLevels) {
		this.permissionLevels = permissionLevels;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysUrl() {
  		return sysUrl;
  	}

  	public void setSysUrl(String sysUrl) {
  		this.sysUrl = sysUrl;
  	}
	/** 取得 ID. */
    public Long getId() {
        return this.id;
    }

    /** 设置 ID. */
    public void setId(Long id) {
        this.id = id;
    }
    /** 取得 权限code. */
    public String getPermissionCode() {
        return this.permissionCode;
    }

    /** 设置 权限code. */
    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }
    /** 取得 权限名称. */
    public String getPermissionName() {
        return this.permissionName;
    }

    /** 设置 权限名称. */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    
    /** 取得 权限路径. */
    public String getPermissionUrl() {
        return this.permissionUrl;
    }

    /** 设置 权限路径. */
    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }
    /** 取得 权限图片. */
    public String getPermissionIcon() {
        return this.permissionIcon;
    }

    /** 设置 权限图片. */
    public void setPermissionIcon(String permissionIcon) {
        this.permissionIcon = permissionIcon;
    }
    /** 取得 组件类型.1-菜单，2-按钮 */
    public Integer getControlType() {
        return this.controlType;
    }

    /** 设置 组件类型.1-菜单，2-按钮 */
    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }
    /** 取得 父级权限.顶级机构为null */
    public Long getParentPermission() {
        return this.parentPermission;
    }

    /** 设置 父级权限.顶级机构为null */
    public void setParentPermission(Long parentPermission) {
        this.parentPermission = parentPermission;
    }
    /** 取得 排序.同一个级别菜单排序 */
    public Integer getSort() {
        return this.sort;
    }

    /** 设置 排序.同一个级别菜单排序 */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    /** 取得 删除标识. */
    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }

    /** 设置 删除标识. */
    public void setDeleteFlag(Integer deleteFlag) {
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
    /** 取得 更新人. */
    public String getUpdateEmp() {
        return this.updateEmp;
    }

    /** 设置 更新人. */
    public void setUpdateEmp(String updateEmp) {
        this.updateEmp = updateEmp;
    }
    /** 取得 更新时间. */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /** 设置 更新时间. */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /** 取得 更新机构. */
    public String getUpdateOrg() {
        return this.updateOrg;
    }

	/** 设置 更新机构. */
    public void setUpdateOrg(String updateOrg) {
        this.updateOrg = updateOrg;
    }
    
    public Boolean getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(Boolean leafFlag) {
		this.leafFlag = leafFlag;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFilterSystemCode() {
		return filterSystemCode;
	}

	public void setFilterSystemCode(String filterSystemCode) {
		this.filterSystemCode = filterSystemCode;
	}

	public Boolean getEmptyFlag() {
		return emptyFlag;
	}

	public void setEmptyFlag(Boolean emptyFlag) {
		this.emptyFlag = emptyFlag;
	}

}
