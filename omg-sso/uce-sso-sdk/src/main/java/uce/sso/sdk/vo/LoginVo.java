/** 
 * @项目名称: FSP
 * @文件名称: LoginVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统交互使用的登录VO
 * @author tanchong
 *
 */
public class LoginVo implements Serializable{

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
	/** 登录时间 */
	private Date loginTime;
	/** 用户名 */
	private String userName;
	/** 密码 */
	private String password;
	/** 返回URL */
	private String refUrl;

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

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRefUrl() {
		return refUrl;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

}
