/** 
 * @项目名称: FSP
 * @文件名称: ResetPwdVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统交互使用的密码找回VO
 * @author zhoujie
 *
 */
public class ResetPwdVo implements Serializable{

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
	 * mac地址
	 */
	private String macAddr;
	/**
	 * 机器码
	 */
	private String machineCode;
	/**
	 * reset时间
	 */
	private Date restTime;
	/**
	 * 用户名
	 */
	private String empCode;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 新密码
	 */
	private String newPassword;
	/**
	 * 密码强度
	 */
	private String passwordStrength;
	/**
	 * 返回URL
	 */
	private String refUrl;
	/**
	 * 验证码 
	 */
	private String code;
	/**
	 * 重置密码唯一key
	 */
	private String resetPwdKey;

	/**
	 * @return the resetPwdKey
	 */
	public String getResetPwdKey() {
		return resetPwdKey;
	}

	/**
	 * @param resetPwdKey the resetPwdKey to set
	 */
	public void setResetPwdKey(String resetPwdKey) {
		this.resetPwdKey = resetPwdKey;
	}

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
	 * @return the systemCode
	 */
	public String getMacAddr() {
		return macAddr;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	/**
	 * @return the systemCode
	 */
	public String getMachineCode() {
		return machineCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	/**
	 * @return the restTime
	 */
	public Date getRestTime() {
		return restTime;
	}

	/**
	 * @param restTime the restTime to set
	 */
	public void setRestTime(Date restTime) {
		this.restTime = restTime;
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
	 * @return the refUrl
	 */
	public String getRefUrl() {
		return refUrl;
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

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
