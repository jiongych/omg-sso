package cn.uce.omg.sso.vo;

import java.io.Serializable;

/**
 * 员工和机构信息
 *<pre>
 * EmpOrgInfoVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年7月19日下午2:49:52
 */
public class EmpOrgInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 员工姓名.同一机构员工姓名唯一 */
	private String empName;
	
	/** 所属机构. */
	private Integer orgId;
	
	/** 所属机构名称*/
	private String orgName;
	
	/** 员工ID. */
	private Integer empId;
	
	/** 员工编号.同一机构员工编号唯一 */
	private String empCode;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

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

}
