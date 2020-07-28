package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

/**
 * 
 * @Description: (UserOrg) 
 * @author XJ
 * @date 2017年6月12日 下午2:54:47
 */
@Table("omg_portal_user_org")
public class PortalUserOrg extends BaseEntity{

	private static final long serialVersionUID = -3331877912418233885L;
	
	private String empCode;
	
	private String orgCode;
	
	private String orgName;
	
	private String createOrg;
	
	private String updateOrg;

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
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	
}
