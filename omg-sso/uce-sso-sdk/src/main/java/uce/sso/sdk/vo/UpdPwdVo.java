/** 
 * @项目名称: FSP
 * @文件名称: UpdPwdVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统交互使用的修改密码VO
 * @author zhoujie
 *
 */
public class UpdPwdVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 系统id */
	private String systemCode;
	/** ip地址 */
	private String ipAddr;
	/** 登录凭证：tokenid */
	private String tokenId;
	/** mac地址 */
	private String macAddr;
	/** 机器码 */
	private String machineCode;
	/** 修改时间 */
	private Date updateTime;
	/** 员工编码 */
	private String empCode;
	/** 新密码 */
	private String newPassword;
	/** 旧密码 */
	private String oldPassword;
	/** 密码强度 */
	private String passwordStrength;
	/** 返回URL */
	private String refUrl;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRefUrl() {
		return refUrl;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPasswordStrength() {
		return passwordStrength;
	}

	public void setPasswordStrength(String passwordStrength) {
		this.passwordStrength = passwordStrength;
	}
	
}
