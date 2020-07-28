package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class PortalNosOrgVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 机构编号.机构编号唯一 */
    private String orgCode;

    /** 机构名称.同一机构下,机构名称唯一 */
    private String orgName;

    /** 机构类型.总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 */
    private Integer orgType;

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

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/** 上一级机构ID */
    private String parentId;
}
