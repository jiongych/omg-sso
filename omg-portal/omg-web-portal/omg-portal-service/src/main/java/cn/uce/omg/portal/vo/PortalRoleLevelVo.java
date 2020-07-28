package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

/**
 * 
 * @Description: (角色级别实体) 
 * @author XJ
 * @date 2017年6月5日 下午5:34:02
 */
public class PortalRoleLevelVo extends BaseVo {
	
	private static final long serialVersionUID = 1L;

	private String roleLevelCode;
	
	private String roleLevelName;
	
	public PortalRoleLevelVo(){}
	public PortalRoleLevelVo(String roleLevelCode, String roleLevelName) {
		super();
		this.roleLevelCode = roleLevelCode;
		this.roleLevelName = roleLevelName;
	}

	public String getRoleLevelCode() {
		return roleLevelCode;
	}

	public void setRoleLevelCode(String roleLevelCode) {
		this.roleLevelCode = roleLevelCode;
	}

	public String getRoleLevelName() {
		return roleLevelName;
	}

	public void setRoleLevelName(String roleLevelName) {
		this.roleLevelName = roleLevelName;
	}
	
	

}
