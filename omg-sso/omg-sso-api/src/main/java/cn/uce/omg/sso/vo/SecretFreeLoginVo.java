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
public class SecretFreeLoginVo implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 系统编码
	 */
	private String systemCode;
	
	/**
	 * 请求系统编码
	 */
	private String partnerSystemCode;
	
	/**
	 * ip地址
	 */
	private String ipAddr;
	/**
	 * 登录时间
	 */
	private Date loginTime;
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 账套
	 */
	private String compCode;
	
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 返回URL
	 */
	private String refUrl;
	
	/**
	 * tooken
	 */
	private String tokenId;


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

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getPartnerSystemCode() {
		return partnerSystemCode;
	}

	public void setPartnerSystemCode(String partnerSystemCode) {
		this.partnerSystemCode = partnerSystemCode;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

}
