/** 
 * @项目名称: FSP
 * @文件名称: LogoutVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统登出交互VO
 * @author huangting
 *
 */
public class LogoutVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 系统id */
	private String systemCode;
	/** 登录凭证：tokenid */
	private String tokenId;
	/** ip地址 */
	private String ipAddr;
	/** mac地址 */
	private String macAddr;
	/** 机器码 */
	private String machineCode;
	/** 员工编码*/
	private String empCode;
	/** 登出时间 */
	private Date logoutTime;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}
