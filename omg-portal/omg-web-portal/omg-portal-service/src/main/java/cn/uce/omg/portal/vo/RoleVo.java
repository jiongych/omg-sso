package cn.uce.omg.portal.vo;

import java.util.Date;
import java.util.List;

import cn.uce.base.vo.BaseVo;

public class RoleVo extends BaseVo {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String roleCode;
    private String roleName;
    private Boolean deleteFlag;
    private String remark;
    private Integer roleLevel;
    private Integer containRoleLevel;
    
    private String roleScope;
    private String roleScopeName;
    
    private String createEmp;
    private String createEmpName;
    
    private String createOrg;
    private String createOrgName;
    
    private Date createTime;
    
    private String updateEmp;
    private String updateEmpName;
    
    private String updateOrg;
    private String updateOrgName;
    
    private Date updateTime;

    private String category;
    /** 权限码 */
    private List<String> permissionCodeList;
    
	/** 权限码 */
    private List<Long> permissionIdList;

    /** 权限码对应的系统编码*/
    private List<String> systemCodeList;
    
    /** 分配机构类型*/
    private String assignsOrgType;
    
	public Integer getContainRoleLevel() {
		return containRoleLevel;
	}

	public void setContainRoleLevel(Integer containRoleLevel) {
		this.containRoleLevel = containRoleLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleScope() {
		return roleScope;
	}

	public void setRoleScope(String roleScope) {
		this.roleScope = roleScope;
	}

	public String getRoleScopeName() {
		return roleScopeName;
	}

	public void setRoleScopeName(String roleScopeName) {
		this.roleScopeName = roleScopeName;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public String getCreateEmpName() {
		return createEmpName;
	}

	public void setCreateEmpName(String createEmpName) {
		this.createEmpName = createEmpName;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	public String getUpdateEmpName() {
		return updateEmpName;
	}

	public void setUpdateEmpName(String updateEmpName) {
		this.updateEmpName = updateEmpName;
	}

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public String getUpdateOrgName() {
		return updateOrgName;
	}

	public void setUpdateOrgName(String updateOrgName) {
		this.updateOrgName = updateOrgName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<String> getPermissionCodeList() {
		return permissionCodeList;
	}

	public void setPermissionCodeList(List<String> permissionCodeList) {
		this.permissionCodeList = permissionCodeList;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getSystemCodeList() {
		return systemCodeList;
	}

	public void setSystemCodeList(List<String> systemCodeList) {
		this.systemCodeList = systemCodeList;
	}
	
	public List<Long> getPermissionIdList() {
		return permissionIdList;
	}

	public void setPermissionIdList(List<Long> permissionIdList) {
		this.permissionIdList = permissionIdList;
	}

	public String getAssignsOrgType() {
		return assignsOrgType;
	}

	public void setAssignsOrgType(String assignsOrgType) {
		this.assignsOrgType = assignsOrgType;
	}
	
}
