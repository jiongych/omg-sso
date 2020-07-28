/** 
 * @项目名称: FSP
 * @文件名称: IPEntry 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo.portal;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
public class PortalBrowserVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键ID */
	public Long id;
	/** 浏览器名称 */
	public String browserName;
	/** 浏览器版本号 */
	public String browserVersion;
	/** 浏览器操作系统 */
	public String browserOperatingSystems;
	/** 浏览器操作系统 */
	public String browserScreen;
	/** 浏览器IP */
	public String operatingIp;
	/** 浏览器操作人员 */
	public String createEmp;
	/** 浏览器操作时间 */
	public Date createDate;
	/** 浏览器操作机构 */
	public String createOrg;
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
}
