/** 
 * @项目名称: FSP
 * @文件名称: ExpCodeVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 验证码交互vo
 * @author zhoujie
 *
 */
public class ExpCodeVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/** 系统id */
	private String systemCode;
	/** ip地址 */
	private String ipAddr;
	/** 机器码 */
	private String machineCode;
	/** 用户名 */
	private String userName;
	/** 手机号码 */
	private String mobile;
	/** 员工编号 */
	private String empCode;
	/** 发送时间 */
	private Date sendTime;
	/** 验证码类型 */
	private String codeType;
	/** 验证码 */
	private String code;
    
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getCode() {
		return code;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
}
