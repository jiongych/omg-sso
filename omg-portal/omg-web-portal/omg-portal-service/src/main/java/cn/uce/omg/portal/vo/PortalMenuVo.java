package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;


public class PortalMenuVo extends BaseVo {
	
	private static final long serialVersionUID = 8204215052602820708L;
	private String menuId;
	//功能编码
	private String menuCode;

	//功能名称
	private String menuName;

	//功能入口URI
	private String menuUri;

	//功能层次
	private String menuLevel;

	//父功能
	private String parentCode;

	//是否有效
	private boolean enabled;
	
	//显示顺序
	private String menuSort;
	
	//功能类型
	private String menuType;
	
	//是否叶子节点
	private String leaf;
	
	//功能描述
	private String menuDesc;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUri() {
		return menuUri;
	}

	public void setMenuUri(String menuUri) {
		this.menuUri = menuUri;
	}

	public String getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
	
	
	
}
