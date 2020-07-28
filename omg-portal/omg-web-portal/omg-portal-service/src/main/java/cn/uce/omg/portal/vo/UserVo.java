package cn.uce.omg.portal.vo;

import java.util.Date;
import java.util.List;

import cn.uce.base.vo.BaseVo;

/**
 * @Description: (UserVo) 
 * @author XJ
 * @date 2018年3月29日 上午11:14:56
 */
public class UserVo extends BaseVo{

    private static final long serialVersionUID = 1L;
    private Long id;
	/** 员工ID. */
    private Integer empId;
	/** 员工编号.员工工号 */
    private String empCode;
    
    private String empName;
    
    private String empOrg;
    
    /** 是否启用.0/1 默认为0 */
    private Boolean enabled;
    
    private Date lastLoginTime;
    
    //岗位名称
    private String positionName;
    
    /**
     * 主机构
     */
    private OrgVo orgVo;
    /**
     * 主机构映射乾坤机构
     */
    private CmsOrgVo cmsOrgVo;
    
    /**
     * 机构绑定关系集
     */
    private List<OtherOrgRelationVo> otherOrgRelationList;
    
    
    private Date createTime;
    
    private EmpVo createEmp;
    
    private OrgVo createOrg;
    
    /**
     * 用户角色管理中角色下的用户
     */
    private String roleCode;
    
    
    /**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public UserVo(){
    	
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public OrgVo getOrgVo() {
		return orgVo;
	}

	public void setOrgVo(OrgVo orgVo) {
		this.orgVo = orgVo;
	}

	public CmsOrgVo getCmsOrgVo() {
		return cmsOrgVo;
	}

	public void setCmsOrgVo(CmsOrgVo cmsOrgVo) {
		this.cmsOrgVo = cmsOrgVo;
	}

	public List<OtherOrgRelationVo> getOtherOrgRelationList() {
		return otherOrgRelationList;
	}

	public void setOtherOrgRelationList(List<OtherOrgRelationVo> otherOrgRelationList) {
		this.otherOrgRelationList = otherOrgRelationList;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public EmpVo getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(EmpVo createEmp) {
		this.createEmp = createEmp;
	}

	public OrgVo getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(OrgVo createOrg) {
		this.createOrg = createOrg;
	}

	public String getEmpOrg() {
		return empOrg;
	}

	public void setEmpOrg(String empOrg) {
		this.empOrg = empOrg;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
