package cn.uce.omg.sso.entity.portal;

import cn.uce.base.entity.BaseEntity;

/**
 * 各系统首页跳转信息实体类.
 *<pre>
 * PortalHomePageInfo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年4月2日上午11:03:41
 */
public class PortalHomePageInfo extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 系统名称. */
	private String sysName;

	/** 系统URL. */
	private String sysUrl;

	/** 系统类型1.内部 0.外部. */
	private Boolean sysType;

	/** 系统图标. */
	private String sysIcon;

	/** 排序. */
	private Integer sort;
	
	/**是否启用：1-启用 0-禁用*/
	private boolean deleteFlag;
	
	/** 备注. */
    private String remark;
    
    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;

    /** 系统查看级别. */
    private String sysLevels;
    
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

	public Boolean getSysType() {
		return sysType;
	}

	public void setSysType(Boolean sysType) {
		this.sysType = sysType;
	}

	public String getSysIcon() {
		return sysIcon;
	}

	public void setSysIcon(String sysIcon) {
		this.sysIcon = sysIcon;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public boolean isDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getSysLevels() {
		return sysLevels;
	}

	public void setSysLevels(String sysLevels) {
		this.sysLevels = sysLevels;
	}
    
}
