package cn.uce.omg.portal.vo;

/**
 * 
 * @Description: (PermissionTreeVo) 
 * @author XJ
 * @date 2017年6月10日 下午6:03:16
 */
public class PermissionTreeVo extends TreeNodeVo<PermissionTreeVo> {

	private static final long serialVersionUID = 1L;

	private String permissionCode;
	
	private String permissionLevels;
	
	private String systemCode;
	
	public String getPermissionLevels() {
		return permissionLevels;
	}

	public void setPermissionLevels(String permissionLevels) {
		this.permissionLevels = permissionLevels;
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
