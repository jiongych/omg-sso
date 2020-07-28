package cn.uce.omg.portal.vo;

import java.util.List;

import cn.uce.base.vo.BaseVo;

public class TreeNodeVo<T> extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String text;
	
	private String parentId;
	
	private Boolean checked;
	
	private Boolean open; 
	
	private Boolean leaf;
	
	private List<T> children;

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		if (open == null) {
			//如果不是叶子节点，默认“closed”
			if (leaf != null && !leaf) {
				return "closed";
			}
			return null;
		} else if (open) {
			return "open";
		} else {
			return "closed";
		}
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	
}
