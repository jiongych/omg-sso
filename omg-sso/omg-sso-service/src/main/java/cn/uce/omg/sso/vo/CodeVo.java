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
 * 验证码vo
 * @author huangting
 * @date 2017年6月8日 下午2:47:47
 */
public class CodeVo extends RedisHashFieldExpireVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 用户名 */
	private String empCode;
	/** 验证码 */
	private String code;
	/** 验证失败次数 */
	private Integer verifyCount;
	/** 验证码发送间隔时间 */
	private Date sendTime;
	

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
	 * @return the verifyCount
	 */
	public Integer getVerifyCount() {
		return verifyCount;
	}

	/**
	 * @param verifyCount the verifyCount to set
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
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
}
