package cn.uce.omg.portal.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class PortalEmpUserVo extends BaseVo{

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    //员工ID
    private String empId;
	/** 员工编号.员工工号 */
    private String empCode;

    /** 员工姓名. */
  	private String empName;
    
  	/** 组织ID. */
  	private Integer orgId;
  	
  	/** 组织名称. */
  	private String orgName;
  	
  	/**乾坤机构基准码*/
  	private String baseOrgCode;
  	
  	/**机构类型*/
  	private Integer orgType;
  	
  	/** 手机号码. */
  	private String mobile;
   
  	/** 邮箱. */
  	private String email;
  	
  	/** 最后登陆时间. */
  	private Date lastLoginTime;
  	
  	/** 是否启用.0/1 默认为0 */
    private Boolean enabled;
    
    /** 创建时间. */
  	private Date createTime;
  	
  	private String orgNameStr;
    
    public String getOrgNameStr() {
		return orgNameStr;
	}

	public void setOrgNameStr(String orgNameStr) {
		this.orgNameStr = orgNameStr;
	}
	
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getBaseOrgCode() {
		return baseOrgCode;
	}

	public void setBaseOrgCode(String baseOrgCode) {
		this.baseOrgCode = baseOrgCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
