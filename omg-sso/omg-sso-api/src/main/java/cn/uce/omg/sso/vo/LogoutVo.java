/** 
 * @项目名称: FSP
 * @文件名称: LogoutVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统登出交互VO
 * @author huangting
 *
 */
public class LogoutVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 系统id
	 */
	private String systemCode;
	/**
	 * 登录凭证：tokenid
	 */
	private String tokenId;
	/**
	 * ip地址
	 */
	private String ipAddr;
	/**
	 * mac地址 
	 */
	private String macAddr;
	/**
	 * 机器码
	 */
	private String machineCode;
	/**
	 * 员工编码
	 */
	private String empCode;
	/**
	 * 登出时间
	 */
	private Date logoutTime;

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @return the ipAddr
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * @param ipAddr the ipAddr to set
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * @return the macAddr
	 */
	public String getMacAddr() {
		return macAddr;
	}

	/**
	 * @param macAddr the macAddr to set
	 */
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	/**
	 * @return the machineCode
	 */
	public String getMachineCode() {
		return machineCode;
	}

	/**
	 * @param machineCode the machineCode to set
	 */
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the logoutTime
	 */
	public Date getLogoutTime() {
		return logoutTime;
	}

	/**
	 * @param logoutTime the logoutTime to set
	 */
	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	/**
	 * @return the tokenId
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * @param tokenId the tokenId to set
	 */
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}
