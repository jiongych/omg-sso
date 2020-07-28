/** 
 * @项目名称: FSP
 * @文件名称: UpdPwdVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统交互使用的修改密码VO
 * @author zhoujie
 *
 */
public class UpdPwdVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 系统id
	 */
	private String systemCode;
	/**
	 * ip地址
	 */
	private String ipAddr;
	/**
	 * 登录凭证：tokenid
	 */
	private String tokenId;
	/**
	 * mac地址
	 */
	private String macAddr;
	/**
	 * 机器码
	 */
	private String machineCode;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 员工编码
	 */
	private String empCode;
	/**
	 * 新密码
	 */
	private String newPassword;
	/**
	 * 旧密码 
	 */
	private String oldPassword;
	/**
	 * 密码强度
	 */
	private String passwordStrength;
	/**
	 * 返回URL
	 */
	private String refUrl;

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
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the refUrl
	 */
	public String getRefUrl() {
		return refUrl;
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
	 * @param refUrl the refUrl to set
	 */
	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	/**
	 * @return the passwordStrength
	 */
	public String getPasswordStrength() {
		return passwordStrength;
	}

	/**
	 * @param passwordStrength the passwordStrength to set
	 */
	public void setPasswordStrength(String passwordStrength) {
		this.passwordStrength = passwordStrength;
	}
	
}
