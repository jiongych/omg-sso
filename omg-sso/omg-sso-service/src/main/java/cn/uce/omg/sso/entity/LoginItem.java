/** 
 * @项目名称: FSP
 * @文件名称: LoginItem extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import java.util.Date;
import cn.uce.base.entity.BaseEntity;

/**
 * LoginItem extends BaseEntity  
 * @Description: LoginItem extends BaseEntity  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class LoginItem extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 操作类型. */
    private String operateType;

    /** 员工ID. */
    private Integer empId;

    /** 用户名. */
    private String userName;

    /** 系统编码. */
    private String systemCode;

    /** 操作时间. */
    private Date operateTime;

    /** 操作IP地址. */
    private String ipAddr;

    /** 操作机器码. */
    private String machineCode;

	/**
	 * @return the operateType
	 */
	public String getOperateType() {
		return operateType;
	}

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
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
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
	 * @param operateType the operateType to set
	 */
	public void setOperateType(String operateType) {
		this.operateType = operateType;
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
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
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
}
