/** 
 * @项目名称: FSP
 * @文件名称: User extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Transient;

/***
 * 用户
 * 
 * @author zhangfh
 * @since 2016-04-20
 */
public class User extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** 员工ID. */
	private Integer empId;

	/** 员工编号 */
	private String empCode;

	/** 登录密码.MD5加密 */
	private String password;

	/** 最后登录时间. */
	private Date lastLoginTime;

	/** 密码修改时间. */
	private Date pwdUpdateTime;

	/** 是否启用.0/1 默认为0 */
	private Boolean enabled;

	/** 用户角色.用户角色 1000（高级管理员），1001（总部管理员），1002（省区人资管理员），1003（省区网管管理员） */
	@Transient
	private Integer role;

	/** 机构组范围.管理多个组织机构，用英文逗号分开。 */
	private String roleOrgGroup;

	/** 版本. */
	private Integer version;

	/** 备注. */
	private String remark;

	/** 创建人. */
	private String createEmp;

	/** 创建机构. */
	private Integer createOrg;

	/** 更新机构. */
	private Integer updateOrg;

	/** 更新人. */
	private String updateEmp;

	/** 字符型保留字段1. */
	private String fldS1;

	/** 数值型保留字段1. */
	private Long fldN1;
	
	/** 用户登录锁定状态. */
	private Boolean loginLockState;
	
	/** hr员工编码. */
	private String hrEmpCode;
	
	/** 银河员工编码. */
    private String yhEmpCode;

    /** 账套. */
    private String compCode;

	/** 取得 是否启用.0/1 默认为0 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/** 设置 是否启用.0/1 默认为0 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/** 取得 用户角色.用户角色 1000（高级管理员），1001（总部管理员），1002（省区人资管理员），1003（省区网管管理员） */
	public Integer getRole() {
		return this.role;
	}

	/** 设置 用户角色.用户角色 1000（高级管理员），1001（总部管理员），1002（省区人资管理员），1003（省区网管管理员） */
	public void setRole(Integer role) {
		this.role = role;
	}

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @return the pwdUpdateTime
	 */
	public Date getPwdUpdateTime() {
		return pwdUpdateTime;
	}

	/**
	 * @return the roleOrgGroup
	 */
	public String getRoleOrgGroup() {
		return roleOrgGroup;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
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
	 * @return the updateOrg
	 */
	public Integer getUpdateOrg() {
		return updateOrg;
	}

	/**
	 * @return the updateEmp
	 */
	public String getUpdateEmp() {
		return updateEmp;
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
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @param pwdUpdateTime the pwdUpdateTime to set
	 */
	public void setPwdUpdateTime(Date pwdUpdateTime) {
		this.pwdUpdateTime = pwdUpdateTime;
	}

	/**
	 * @param roleOrgGroup the roleOrgGroup to set
	 */
	public void setRoleOrgGroup(String roleOrgGroup) {
		this.roleOrgGroup = roleOrgGroup;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
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
	 * @param updateOrg the updateOrg to set
	 */
	public void setUpdateOrg(Integer updateOrg) {
		this.updateOrg = updateOrg;
	}

	/**
	 * @param updateEmp the updateEmp to set
	 */
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
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
	
	/**
	 * 获取 用户登录锁定状态.
	 * @return loginLockState
	 */
	public Boolean getLoginLockState() {
		return loginLockState;
	}

	/**
	 * 设置 用户登录锁定状态.
	 * @param loginLockState 用户登录锁定状态.
	 */
	public void setLoginLockState(Boolean loginLockState) {
		this.loginLockState = loginLockState;
	}

	public String getYhEmpCode() {
		return yhEmpCode;
	}

	public void setYhEmpCode(String yhEmpCode) {
		this.yhEmpCode = yhEmpCode;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getHrEmpCode() {
		return hrEmpCode;
	}

	public void setHrEmpCode(String hrEmpCode) {
		this.hrEmpCode = hrEmpCode;
	}
}
