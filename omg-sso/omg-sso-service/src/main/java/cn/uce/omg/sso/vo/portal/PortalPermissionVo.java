package cn.uce.omg.sso.vo.portal;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class PortalPermissionVo extends BaseVo{

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
    
    //权限级别
    private String permissionLevels;
    
    private String systemCode;
    
    private boolean isHide;
    
    
	/**
	 * @return the isHide
	 */
	public boolean getIsHide() {
		return isHide;
	}

	/**
	 * @param isHide the isHide to set
	 */
	public void setIsHide(boolean isHide) {
		this.isHide = isHide;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}

	public String getPermissionIcon() {
		return permissionIcon;
	}

	public void setPermissionIcon(String permissionIcon) {
		this.permissionIcon = permissionIcon;
	}

	public Integer getControlType() {
		return controlType;
	}

	public void setControlType(Integer controlType) {
		this.controlType = controlType;
	}

	public Long getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(Long parentPermission) {
		this.parentPermission = parentPermission;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Boolean getLeafFlag() {
		return leafFlag;
	}

	public void setLeafFlag(Boolean leafFlag) {
		this.leafFlag = leafFlag;
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

	public String getPermissionLevels() {
		return permissionLevels;
	}

	public void setPermissionLevels(String permissionLevels) {
		this.permissionLevels = permissionLevels;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
    
}
