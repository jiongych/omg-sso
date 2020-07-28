package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class SimpleOrgVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	private Integer orgId;
	private String orgCode;
	private Integer orgType;
	private String orgName;
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
	
}
