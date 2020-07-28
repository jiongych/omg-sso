/** 
 * @项目名称: FSP
 * @文件名称: UserInfoVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.io.Serializable;

/**
 * 用户信息
 * @author huangting
 * @date 2017年6月9日 上午11:33:02
 */
public class UserInfoVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 员工ID
	 */
	private Integer empId;
	/**
	 * 员工编号.同一机构员工编号唯一
	 */
	private String empCode;
	/**
	 * 员工姓名.同一机构员工姓名唯一
	 */
	private String empName;
	/**
	 * 性别 male,female
	 */
	private Integer sex;
	/**
	 * 直接上级
	 */
	private Integer immediateSuperior;
	/**
	 * 是否为负责人
	 */
	private Boolean principalFlag;
	/**
	 * 职级
	 */
	private Integer positionLevel;
	/**
	 * 固定电话
	 */
	private String telephone;
	/**
	 * 员工手机号码.同一机构员工姓名唯一
	 */
	private String mobile;
	/**
	 * 员工姓名.同一机构员工姓名唯
	 */
	private String email;
	/**
	 * 所属机构
	 */
	private Integer orgId;
	/**
	 * 所属主岗位
	 */
	private Integer positionId;
	/**
	 * 是否启用.0/1 默认为0
	 */
	private Boolean enabled;
	
	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
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

	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return the immediateSuperior
	 */
	public Integer getImmediateSuperior() {
		return immediateSuperior;
	}

	/**
	 * @param immediateSuperior the immediateSuperior to set
	 */
	public void setImmediateSuperior(Integer immediateSuperior) {
		this.immediateSuperior = immediateSuperior;
	}

	/**
	 * @return the principalFlag
	 */
	public Boolean getPrincipalFlag() {
		return principalFlag;
	}

	/**
	 * @param principalFlag the principalFlag to set
	 */
	public void setPrincipalFlag(Boolean principalFlag) {
		this.principalFlag = principalFlag;
	}

	/**
	 * @return the positionLevel
	 */
	public Integer getPositionLevel() {
		return positionLevel;
	}

	/**
	 * @param positionLevel the positionLevel to set
	 */
	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the positionId
	 */
	public Integer getPositionId() {
		return positionId;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
