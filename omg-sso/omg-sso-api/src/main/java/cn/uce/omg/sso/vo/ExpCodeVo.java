/** 
 * @项目名称: FSP
 * @文件名称: ExpCodeVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

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
	/**
	 * 系统id
	 */
	private String systemCode;
	/**
	 * ip地址
	 */
	private String ipAddr;
	/**
	 * 机器码
	 */
	private String machineCode;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 员工编号
	 */
	private String empCode;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 首次设置密码
	 */
	private Boolean initPw;
	/**
	 * 发送时间
	 */
	private Date sendTime;
	/**
	 * 验证码类型
	 */
	private String codeType;
	/**
	 * 验证码
	 */
	private String code;
	
	/**
	 * 是否本系统调用(为true则为本系统,为null或false则为外部系统)
	 */
	private Boolean isSelfCall;
	
	public Boolean getIsSelfCall() {
		return isSelfCall;
	}

	public void setIsSelfCall(Boolean isSelfCall) {
		this.isSelfCall = isSelfCall;
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

	/**
	 * @return the codeType
	 */
	public String getCodeType() {
		return codeType;
	}
 
	/**
	 * @param codeType the codeType to set
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
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
	 * @return the code
	 */
	public String getCode() {
		return code;
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
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the initPw
	 */
	public Boolean getInitPw() {
		return initPw;
	}

	/**
	 * @param initPw the initPw to set
	 */
	public void setInitPw(Boolean initPw) {
		this.initPw = initPw;
	}
}
