package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

@Table("omg_portal_permission")
public class PortalPermission extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

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

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;

    /** 是否是叶子节点 */
    private Boolean leafFlag;
    
	/**系统路径*/
    private String sysUrl;
    
    //角色级别
    private String permissionLevels;

    /**所属系统*/
    private String systemCode;
    
    /** 权限类型*/
    private String category;
    
    private int isHide;
    
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
    
	public String getPermissionLevels() {
		return permissionLevels;
	}

	public void setPermissionLevels(String permissionLevels) {
		this.permissionLevels = permissionLevels;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
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
}
