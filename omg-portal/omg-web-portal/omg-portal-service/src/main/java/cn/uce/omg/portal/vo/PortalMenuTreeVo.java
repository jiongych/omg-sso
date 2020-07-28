package cn.uce.omg.portal.vo;

import java.util.List;

public class PortalMenuTreeVo {
	private Long id;  //id
    private String text; //text  
    private String orgCode;
    private Boolean checked = false; //是否选中  
    private List<PortalMenuTreeVo> children; //子节点
    private String state ;//节点状态，'open' 或 'closed'
    private String attributes;//属性
    private String permissionIcon;//权限图标
    private String permissionCode;
    private String systemCode;
    
	public String getPermissionIcon() {
		return permissionIcon;
	}
	public void setPermissionIcon(String permissionIcon) {
		this.permissionIcon = permissionIcon;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public List<PortalMenuTreeVo> getChildren() {
		return children;
	}
	public void setChildren(List<PortalMenuTreeVo> children) {
		this.children = children;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	
}
