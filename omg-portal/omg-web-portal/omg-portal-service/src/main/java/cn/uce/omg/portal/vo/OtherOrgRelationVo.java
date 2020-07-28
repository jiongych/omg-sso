package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class OtherOrgRelationVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Integer orgId;
	
	private String orgCode;
	
	private Integer orgType;
	
	private String orgName;
	
	private Integer cmsOrgId;
	
	private String cmsBaseOrgCode;
	
	private Integer cmsOrgType;
	
	private String cmsOrgName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getCmsOrgId() {
		return cmsOrgId;
	}

	public void setCmsOrgId(Integer cmsOrgId) {
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
	
}
