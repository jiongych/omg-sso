package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class SimpleCmsOrgVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private Integer cmsOrgId;
	private String cmsBaseOrgCode;
	private Integer cmsOrgType;
	private String cmsOrgName;
	
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
