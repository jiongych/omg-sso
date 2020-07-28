/** 
 * @项目名称: FSP
 * @文件名称: LoginVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统交互使用的登录VO
 * @author tanchong
 *
 */
public class LoginVo implements Serializable{

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
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 银河用户名
	 */
	private String yhUserName;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 账套
	 */
	private String compCode;
	/**
	 * 返回URL
	 */
	private String refUrl;

	/**
	 * 校验码信息
	 */
	private String checkCode;

	/**
	 * 登录类型，默认密码登录。
	 * 密码登录，手机验证码登录，扫码登录。
	 */
	private String loginType;
	
	/**
	 * 用户账号类型,1:快递；2：快运
	 */
	private String userAccountType;

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	/**
	 * @return the checkCode
	 */
	public String getCheckCode() {
		return checkCode;
	}

	/**
	 * @param checkCode the checkCode to set
	 */
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
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
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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

	public String getUserAccountType() {
		return userAccountType;
	}

	public void setUserAccountType(String userAccountType) {
		this.userAccountType = userAccountType;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getYhUserName() {
		return yhUserName;
	}

	public void setYhUserName(String yhUserName) {
		this.yhUserName = yhUserName;
	}

}
