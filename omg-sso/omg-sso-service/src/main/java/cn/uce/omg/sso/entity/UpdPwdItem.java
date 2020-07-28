/** 
 * @项目名称: FSP
 * @文件名称: UpdPwdItem extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import java.util.Date;
import cn.uce.base.entity.BaseEntity;

/**
 * UpdPwdItem extends BaseEntity  
 * @Description: UpdPwdItem extends BaseEntity  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UpdPwdItem extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 员工ID. */
    private Integer empId;

    /** 用户名. */
    private String userName;
    
    /** 用户修改，管理员重置，密码找回. */
    private String updateSource;
    /** 系统编码.从某个系统跳转，或者通过接口调用的系统id */
    private String systemCode;

    /** 密码.修改的旧密码 */
    private String password;

    /** 密码强度.修改强度，低，中，高 */
    private String passwordStrength;

    /** 修改IP地址. */
    private String ipAddr;

    /** 机器码. */
    private String machineCode;

    /** MAC地址. */
    private String macAddr;

    /** 操作人. */
    private String operateEmp;

    /** 操作机构.管理员可以进行重置密码 */
    private Integer operateOrg;

    /** 操作时间. */
    private Date operateTime;

	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the updateSource
	 */
	public String getUpdateSource() {
		return updateSource;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the passwordStrength
	 */
	public String getPasswordStrength() {
		return passwordStrength;
	}

	/**
	 * @return the ipAddr
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * @return the machineCode
	 */
	public String getMachineCode() {
		return machineCode;
	}

	/**
	 * @return the macAddr
	 */
	public String getMacAddr() {
		return macAddr;
	}

	/**
	 * @return the operateEmp
	 */
	public String getOperateEmp() {
		return operateEmp;
	}

	/**
	 * @return the operateOrg
	 */
	public Integer getOperateOrg() {
		return operateOrg;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param updateSource the updateSource to set
	 */
	public void setUpdateSource(String updateSource) {
		this.updateSource = updateSource;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param passwordStrength the passwordStrength to set
	 */
	public void setPasswordStrength(String passwordStrength) {
		this.passwordStrength = passwordStrength;
	}

	/**
	 * @param ipAddr the ipAddr to set
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * @param machineCode the machineCode to set
	 */
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	/**
	 * @param macAddr the macAddr to set
	 */
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	/**
	 * @param operateEmp the operateEmp to set
	 */
	public void setOperateEmp(String operateEmp) {
		this.operateEmp = operateEmp;
	}

	/**
	 * @param operateOrg the operateOrg to set
	 */
	public void setOperateOrg(Integer operateOrg) {
		this.operateOrg = operateOrg;
	}

	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
}
