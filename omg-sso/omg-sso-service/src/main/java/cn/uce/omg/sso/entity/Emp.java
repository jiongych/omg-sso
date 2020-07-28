/** 
 * @项目名称: FSP
 * @文件名称: Emp extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;

/**
 * Emp extends BaseEntity  
 * @Description: Emp extends BaseEntity  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class Emp extends BaseEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 员工ID. */
	private Integer empId;
	/** 员工编号.同一机构员工编号唯一 */
	private String empCode;
	/** 员工姓名.同一机构员工姓名唯一 */
	private String empName;
	/** 所属机构. */
	private Integer orgId;
	/** 是否负责人. */
	private Boolean principalFlag;
	/** 直接上级. */
	private Integer immediateSuperior;
	/** 职级. */
	private Integer positionLevel;
	/** 是否启用.0/1 默认为0 */
	private Boolean enabled;
	/** 是否删除. */
	private Boolean deleteFlag;
	/** 备注. */
	private String remark;
	/** 更新人 */
	private String createEmp;
	/** 更新机构. */
	private Integer createOrg;
	/** 更新人 */
	private String updateEmp;
	/** 更新机构. */
	private Integer updateOrg;
	/** 员工姓名.同一机构员工姓名唯一 */
	private String email;
	/** 员工手机号码.同一机构员工姓名唯一 */
	private String mobile;
	/** 员工状态-- */
	private Integer status;
	/** 性别 */
	private Integer sex;
	/** 固定电话 */
	private String telephone;

	/** 字符型保留字段1. */
	private String fldS1;

	/** 数值型保留字段1. */
	private Long fldN1;

	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}

	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}

	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @return the principalFlag
	 */
	public Boolean getPrincipalFlag() {
		return principalFlag;
	}

	/**
	 * @return the immediateSuperior
	 */
	public Integer getImmediateSuperior() {
		return immediateSuperior;
	}

	/**
	 * @return the positionLevel
	 */
	public Integer getPositionLevel() {
		return positionLevel;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @return the deleteFlag
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @return the createEmp
	 */
	public String getCreateEmp() {
		return createEmp;
	}

	/**
	 * @return the createOrg
	 */
	public Integer getCreateOrg() {
		return createOrg;
	}

	/**
	 * @return the updateEmp
	 */
	public String getUpdateEmp() {
		return updateEmp;
	}

	/**
	 * @return the updateOrg
	 */
	public Integer getUpdateOrg() {
		return updateOrg;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @return the fldS1
	 */
	public String getFldS1() {
		return fldS1;
	}

	/**
	 * @return the fldN1
	 */
	public Long getFldN1() {
		return fldN1;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * @param principalFlag the principalFlag to set
	 */
	public void setPrincipalFlag(Boolean principalFlag) {
		this.principalFlag = principalFlag;
	}

	/**
	 * @param immediateSuperior the immediateSuperior to set
	 */
	public void setImmediateSuperior(Integer immediateSuperior) {
		this.immediateSuperior = immediateSuperior;
	}

	/**
	 * @param positionLevel the positionLevel to set
	 */
	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @param createEmp the createEmp to set
	 */
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	/**
	 * @param createOrg the createOrg to set
	 */
	public void setCreateOrg(Integer createOrg) {
		this.createOrg = createOrg;
	}

	/**
	 * @param updateEmp the updateEmp to set
	 */
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	/**
	 * @param updateOrg the updateOrg to set
	 */
	public void setUpdateOrg(Integer updateOrg) {
		this.updateOrg = updateOrg;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @param fldS1 the fldS1 to set
	 */
	public void setFldS1(String fldS1) {
		this.fldS1 = fldS1;
	}

	/**
	 * @param fldN1 the fldN1 to set
	 */
	public void setFldN1(Long fldN1) {
		this.fldN1 = fldN1;
	}
}
