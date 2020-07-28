package cn.uce.omg.portal.vo;

import java.io.Serializable;
import java.util.List;

public class ZTreeNodeVo<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String pid ;
	
	private List<T> children;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<T> getChildren() {
		return children;
	}

	public void setChildren(List<T> children) {
		this.children = children;
	}
	
}
