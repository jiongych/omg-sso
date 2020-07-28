/** 
 * @项目名称: FSP
 * @文件名称: LoginResultVo extends BaseVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.util.List;

import cn.uce.base.vo.BaseVo;

/**
 * 登录结果vo
 * @author huangting
 * @date 2017年6月8日 下午2:44:08
 */
public class LoginResultVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 错误编码 */
	private String errorCode;
	/** 错误信息 */
	private String errorMsg;
	/** 用户token信息 */
	private String token;
	/** 用户名  */
	private String userName;
	/** 用户编码  */
	private String empCode;
	private List<String> systemCasUrlList;
	/** 修改密码标识 */
	private Boolean updPwdFlag;
	/** 修改密码间隔天数 */
	private Integer updPwdIntervalDay;
	/** 失败次数 */
	private Integer errorCount;

	private String checkCode;
	/** 扫描系统编码  */
	private String scanSystemCode;

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	/**
	 * @return the errorCount
	 */
	public Integer getErrorCount() {
		return errorCount;
	}
	/**
	 * @param errorCount the errorCode to set
	 */
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the updPwdFlag
	 */
	public Boolean getUpdPwdFlag() {
		return updPwdFlag;
	}

	/**
	 * @return the updPwdIntervalDay
	 */
	public Integer getUpdPwdIntervalDay() {
		return updPwdIntervalDay;
	}

	/**
	 * @param updPwdFlag the updPwdFlag to set
	 */
	public void setUpdPwdFlag(Boolean updPwdFlag) {
		this.updPwdFlag = updPwdFlag;
	}

	/**
	 * @param updPwdIntervalDay the updPwdIntervalDay to set
	 */
	public void setUpdPwdIntervalDay(Integer updPwdIntervalDay) {
		this.updPwdIntervalDay = updPwdIntervalDay;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the systemCasUrlList
	 */
	public List<String> getSystemCasUrlList() {
		return systemCasUrlList;
	}

	/**
	 * @param systemCasUrlList the systemCasUrlList to set
	 */
	public void setSystemCasUrlList(List<String> systemCasUrlList) {
		this.systemCasUrlList = systemCasUrlList;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public String getScanSystemCode() {
		return scanSystemCode;
	}

	public void setScanSystemCode(String scanSystemCode) {
		this.scanSystemCode = scanSystemCode;
	}
	
	
}
