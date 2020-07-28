package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class PortalUserMenuVo extends BaseVo {

private static final long serialVersionUID = -5816938817296317593L;
	
	private Long id;
	
	private String empCode;
	
	private String orgCode;
	
	private Long permissionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	
}
