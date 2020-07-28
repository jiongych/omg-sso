package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

public class PortalOrgDatagridVo extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7177412355398930947L;
	private String parentOrg;
	private String parentName;
	private String parentId;
	private Integer orgType;
	private String id;
	private String text;

	public String getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(String parentOrg) {
		this.parentOrg = parentOrg;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	
}
