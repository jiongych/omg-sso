/** 
 * @项目名称: FSP
 * @文件名称: ResetPwdVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统交互使用的密码找回VO
 * @author zhoujie
 *
 */
public class ResetPwdVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 系统id */
	private String systemCode;
	/** ip地址 */
	private String ipAddr;
	/** mac地址 */
	private String macAddr;
	/** 机器码 */
	private String machineCode;
	/** reset时间 */
	private Date restTime;
	/** 用户名 */
	private String empCode;
	/** 手机号*/
	private String mobile;
	/** 新密码 */
	private String newPassword;
	/** 密码强度 */
	private String passwordStrength;
	/** 返回URL */
	private String refUrl;
	/** 验证码 */
	private String code;
	/** 重置密码唯一key */
	private String resetPwdKey;
	
	public String getResetPwdKey() {
		return resetPwdKey;
	}

	public void setResetPwdKey(String resetPwdKey) {
		this.resetPwdKey = resetPwdKey;
	}

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

	public Date getRestTime() {
		return restTime;
	}

	public void setRestTime(Date restTime) {
		this.restTime = restTime;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getRefUrl() {
		return refUrl;
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

	public String getPasswordStrength() {
		return passwordStrength;
	}

	public void setPasswordStrength(String passwordStrength) {
		this.passwordStrength = passwordStrength;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
