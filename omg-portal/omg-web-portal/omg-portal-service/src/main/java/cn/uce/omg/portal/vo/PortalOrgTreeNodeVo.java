package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

/**
 * @Description: (机构树节点) 
 * @author XJ
 * @date 2017年8月10日 下午4:07:58
 */
public class PortalOrgTreeNodeVo extends BaseVo{

	private static final long serialVersionUID = 5416095555860699558L;
	
	private String id;
	
	private String text;
	
	private String orgType;
	
	private String state; 
	
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	

}
