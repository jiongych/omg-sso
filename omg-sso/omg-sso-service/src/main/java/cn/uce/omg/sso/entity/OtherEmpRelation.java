/** 
 * @项目名称: FSP
 * @文件名称: OtherEmpRelation extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;

/**
 * OtherEmpRelation extends BaseEntity  
 * @Description: OtherEmpRelation extends BaseEntity  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class OtherEmpRelation extends BaseEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 员工ID.
	 */
	private Integer empId;
	/**
	 * 机构ID.
	 */
	private Integer orgId;
	
	/**
	 * 系统标识.1:乾坤系统
	 */
	private Integer systemId;
	/**
	 * 其他系统员工ID.
	 */
	private Integer otherEmpId;
	/**
	 * 其他系统机构ID.
	 */
	private Integer otherOrgId;
	/**
	 * 其他系统员工编号.
	 */
	private String otherEmpCode;
	/**
	 * 其他系统员工名称.
	 */
	private String otherEmpName;
	/**
	 * 备注.
	 */
	private String remark;
	/** 更新人 */
	private String updateEmp;
	/** 更新机构 */
	private Integer updateOrg;
	/**
	 * @return the empId
	 */
	public Integer getEmpId() {
		return empId;
	}
	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}
	/**
	 * @return the systemId
	 */
	public Integer getSystemId() {
		return systemId;
	}
	/**
	 * @return the otherEmpId
	 */
	public Integer getOtherEmpId() {
		return otherEmpId;
	}
	/**
	 * @return the otherOrgId
	 */
	public Integer getOtherOrgId() {
		return otherOrgId;
	}
	/**
	 * @return the otherEmpCode
	 */
	public String getOtherEmpCode() {
		return otherEmpCode;
	}
	/**
	 * @return the otherEmpName
	 */
	public String getOtherEmpName() {
		return otherEmpName;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
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
	 * @param empId the empId to set
	 */
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(Integer systemId) {
		this.systemId = systemId;
	}
	/**
	 * @param otherEmpId the otherEmpId to set
	 */
	public void setOtherEmpId(Integer otherEmpId) {
		this.otherEmpId = otherEmpId;
	}
	/**
	 * @param otherOrgId the otherOrgId to set
	 */
	public void setOtherOrgId(Integer otherOrgId) {
		this.otherOrgId = otherOrgId;
	}
	/**
	 * @param otherEmpCode the otherEmpCode to set
	 */
	public void setOtherEmpCode(String otherEmpCode) {
		this.otherEmpCode = otherEmpCode;
	}
	/**
	 * @param otherEmpName the otherEmpName to set
	 */
	public void setOtherEmpName(String otherEmpName) {
		this.otherEmpName = otherEmpName;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
}
