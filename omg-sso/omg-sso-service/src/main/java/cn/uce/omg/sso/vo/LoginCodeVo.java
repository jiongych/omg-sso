/** 
 * @项目名称: FSP
 * @文件名称: CodeVo extends RedisHashFieldExpireVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
 * 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package cn.uce.omg.sso.vo;

import java.util.Date;

/**
 * 登录验证码vo
 * 
 * @author majun
 * @date 2019年6月22日 下午3:55:56
 *
 */
public class LoginCodeVo extends RedisHashFieldExpireVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 用户名 */
	private String empCode;

	/** 登录码 */
	private String code;

	/** tokenId */
	private String tokenId;

	/** 登录系统 */
	private String systemCode;
	/** 扫描系统 */
	private String scanSystemCode;
	/** 验证失败次数 */
	private Integer verifyCount;
	/** 验证码发送间隔时间 */
	private Date sendTime;
	/** 失效标识 */
	private Boolean failureFlag;

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getScanSystemCode() {
		return scanSystemCode;
	}

	public void setScanSystemCode(String scanSystemCode) {
		this.scanSystemCode = scanSystemCode;
	}
	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode
	 *            the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the verifyCount
	 */
	public Integer getVerifyCount() {
		return verifyCount;
	}

	/**
	 * @param verifyCount
	 *            the verifyCount to set
	 */
	public void setVerifyCount(Integer verifyCount) {
		this.verifyCount = verifyCount;
	}

	/**
	 * @return the sendTime
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Boolean getFailureFlag() {
		return failureFlag;
	}

	public void setFailureFlag(Boolean failureFlag) {
		this.failureFlag = failureFlag;
	}

}
