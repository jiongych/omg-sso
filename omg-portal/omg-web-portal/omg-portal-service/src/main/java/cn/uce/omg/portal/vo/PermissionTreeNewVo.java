package cn.uce.omg.portal.vo;

/**
 * 
 * @Description: (PermissionTreeVo) 
 * @author XJ
 * @date 2017年6月10日 下午6:03:16
 */
public class PermissionTreeNewVo extends TreeNodeVo<PermissionTreeNewVo> {

	private static final long serialVersionUID = 1L;

	private String permissionCode;
	
	private String permissionLevels;
	
	private String systemCode;
	
	private String name;

	private String controlType;

	/**
	 * @return the permissionCode
	 */
	public String getPermissionCode() {
		return permissionCode;
	}

	/**
	 * @param permissionCode the permissionCode to set
	 */
	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	/**
	 * @return the permissionLevels
	 */
	public String getPermissionLevels() {
		return permissionLevels;
	}

	/**
	 * @param permissionLevels the permissionLevels to set
	 */
	public void setPermissionLevels(String permissionLevels) {
		this.permissionLevels = permissionLevels;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the systemCode
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * @param systemCode the systemCode to set
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getControlType() {
		return controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	
}
