package cn.uce.omg.portal.vo;

import java.io.Serializable;
import java.util.List;

public class MenuTreeNodeVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String name;
	
	private String pid ;
	
	private Boolean isParent;
	
	private List<MenuTreeNodeVo> children;
	
	 /** 权限编码. */
    private String permissionCode;

    /** 系统编码. */
    private String systemCode;
    
    /** 权限路径 . */
	private String permissionUrl;
    
	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	
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

	public List<MenuTreeNodeVo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTreeNodeVo> children) {
		this.children = children;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getPermissionUrl() {
		return permissionUrl;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}
}
