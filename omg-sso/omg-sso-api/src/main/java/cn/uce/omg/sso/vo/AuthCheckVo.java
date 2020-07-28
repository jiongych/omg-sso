/** 
 * @项目名称: FSP
 * @文件名称: AuthCheckVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限检查VO
 * @author tanchong
 *
 */
public class AuthCheckVo implements Serializable{

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
	 * 用户编码
	 */
	private String empCode;
	/**
	 * 登录码
	 */
	private String loginCode;
	/**
	 * 失效标识
	 */
	private Boolean failureFlag;

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	/**
	 * 当前时间
	 */
	private Date currentTime;

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
	 * @return the currentTime
	 */
	public Date getCurrentTime() {
		return currentTime;
	}

	/**
	 * @param currentTime the currentTime to set
	 */
	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
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

	public Boolean getFailureFlag() {
		return failureFlag;
	}

	public void setFailureFlag(Boolean failureFlag) {
		this.failureFlag = failureFlag;
	}

}
