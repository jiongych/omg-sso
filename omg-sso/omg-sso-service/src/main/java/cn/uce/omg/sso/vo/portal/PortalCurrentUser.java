package cn.uce.omg.sso.vo.portal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.uce.omg.sso.vo.RedisHashFieldExpireVo;

public class PortalCurrentUser extends RedisHashFieldExpireVo{

	private static final long serialVersionUID = 1L;
	
	private String token;
	private Integer empId;
	private String empCode;
	private String empName;
	private Date lastLoginTime;
	
	private Integer orgId;
	private String orgCode;
	private Integer orgType;
	private String orgName;
	
	private String cmsOrgId;
	private String cmsOrgCode;
	private String cmsBaseOrgCode;
	private Integer cmsOrgType;
	private String cmsOrgName;
	private String orgFullId;
	private List<Map<String,Object>> orgRefRel;
	private List<String> roleCodeList;
	
	/**
	 * 银河用户名
	 */
	private String yhEmpCode;
	
	/**
	 * 账套
	 */
	private String compCode;
	/**
	 * 集团工号
	 */
	private String ymEmpCode;
	
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

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getCmsOrgId() {
		return cmsOrgId;
	}

	public void setCmsOrgId(String cmsOrgId) {
		this.cmsOrgId = cmsOrgId;
	}

	public String getCmsBaseOrgCode() {
		return cmsBaseOrgCode;
	}

	public void setCmsBaseOrgCode(String cmsBaseOrgCode) {
		this.cmsBaseOrgCode = cmsBaseOrgCode;
	}

	public Integer getCmsOrgType() {
		return cmsOrgType;
	}

	public void setCmsOrgType(Integer cmsOrgType) {
		this.cmsOrgType = cmsOrgType;
	}

	public String getCmsOrgName() {
		return cmsOrgName;
	}

	public void setCmsOrgName(String cmsOrgName) {
		this.cmsOrgName = cmsOrgName;
	}

	public List<Map<String, Object>> getOrgRefRel() {
		return orgRefRel;
	}

	public void setOrgRefRel(List<Map<String, Object>> orgRefRel) {
		this.orgRefRel = orgRefRel;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getCmsOrgCode() {
		return cmsOrgCode;
	}

	public void setCmsOrgCode(String cmsOrgCode) {
		this.cmsOrgCode = cmsOrgCode;
	}

	public List<String> getRoleCodeList() {
		return roleCodeList;
	}

	public void setRoleCodeList(List<String> roleCodeList) {
		this.roleCodeList = roleCodeList;
	}

	public String getOrgFullId() {
		return orgFullId;
	}

	public void setOrgFullId(String orgFullId) {
		this.orgFullId = orgFullId;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public String getYhEmpCode() {
		return yhEmpCode;
	}

	public void setYhEmpCode(String yhEmpCode) {
		this.yhEmpCode = yhEmpCode;
	}

	public String getYmEmpCode() {
		return ymEmpCode;
	}

	public void setYmEmpCode(String ymEmpCode) {
		this.ymEmpCode = ymEmpCode;
	}
	
}
