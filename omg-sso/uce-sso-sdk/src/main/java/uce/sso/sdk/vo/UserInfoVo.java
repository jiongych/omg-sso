/** 
 * @项目名称: FSP
 * @文件名称: UserInfoVo implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package uce.sso.sdk.vo;

import java.io.Serializable;

/**
 * UserInfoVo implements Serializable  
 * @Description: UserInfoVo implements Serializable  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UserInfoVo implements Serializable {

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
	/** 性别 male,female */
	private Integer sex;
	/** 直接上级. */
	private Integer immediateSuperior;
	/** 是否为负责人. */
	private Boolean principalFlag;
	/** 职级. */
	private Integer positionLevel;
	/** 固定电话 */
	private String telephone;
	/** 员工手机号码.同一机构员工姓名唯一 */
	private String mobile;
	/** 员工姓名.同一机构员工姓名唯一 */
	private String email;
	/** 所属机构. */
	private Integer orgId;
	/** 所属主岗位 */
	private Integer positionId;
	/** 是否启用.0/1 默认为0 */
	private Boolean enabled;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getImmediateSuperior() {
		return immediateSuperior;
	}

	public void setImmediateSuperior(Integer immediateSuperior) {
		this.immediateSuperior = immediateSuperior;
	}

	public Boolean getPrincipalFlag() {
		return principalFlag;
	}

	public void setPrincipalFlag(Boolean principalFlag) {
		this.principalFlag = principalFlag;
	}

	public Integer getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(Integer positionLevel) {
		this.positionLevel = positionLevel;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
