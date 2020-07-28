package cn.uce.omg.portal.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class PortalBrowserInfoVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键ID */
	private Long id;
	/** 浏览器名称 */
	private String browserName;
	/** 浏览器版本号 */
	private String browserVersion;
	/** 浏览器操作系统 */
	private String browserOperatingSystems;
	/** 浏览器操作系统 */
	private String browserScreen;
	/** 浏览器IP */
	private String operatingIp;
	/** 浏览器操作人员 */
	private String createEmp;
	/** 浏览器操作时间 */
	private Date createDate;
	/** 浏览器操作机构 */
	private String createOrg;
	/** 结束时间 */
	private Date updatedTime;
	
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	private String empName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getBrowserOperatingSystems() {
		return browserOperatingSystems;
	}

	public void setBrowserOperatingSystems(String browserOperatingSystems) {
		this.browserOperatingSystems = browserOperatingSystems;
	}

	public String getBrowserScreen() {
		return browserScreen;
	}

	public void setBrowserScreen(String browserScreen) {
		this.browserScreen = browserScreen;
	}

	public String getOperatingIp() {
		return operatingIp;
	}

	public void setOperatingIp(String operatingIp) {
		this.operatingIp = operatingIp;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
