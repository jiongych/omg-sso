package cn.uce.omg.sso.entity.portal;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

/**
 * 
 * @Description:员工常用菜单表
 * @author Ji Yongchao
 * @date 2017年7月26日 上午8:49:55
 */
@Table("omg_portal_user_menu")
public class PortalUserMenu extends BaseEntity {
	 /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    /**  员工编号*/
    private String empCode;
    
    /**  机构编号*/
	private String orgCode;
	
	/** 权限表菜单ID*/
	private Long permissionId;
    
    /**  创建机构*/
	private String createOrg;
    
    /**  更新机构*/
	private String updateOrg;

	 /** 版本. */
    private Integer version;
    
	 /** 排序. */
    private Integer sort;
    
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

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
}
