/** 
 * @项目名称: FSP
 * @文件名称: AuthCheckVo implements Serializable
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限检查VO
 * @author tanchong
 *
 */
public class AuthCheckVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 系统id */
	private String systemCode;
	
	/** 登录凭证：tokenid */
	private String tokenId;
	
	/** 用户编码 */
	private String empCode;
	
	/** 当前时间 */
	private Date currentTime;


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

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

}

