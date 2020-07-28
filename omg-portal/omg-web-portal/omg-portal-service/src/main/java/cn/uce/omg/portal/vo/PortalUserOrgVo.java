package cn.uce.omg.portal.vo;

import cn.uce.base.vo.BaseVo;

/**
 * 
 * @Description: (UserOrgVo) 
 * @author XJ
 * @date 2017年6月10日 上午10:21:14
 */
public class PortalUserOrgVo extends BaseVo{

	private static final long serialVersionUID = -5816938817296317593L;
	
	private Long id;
	
	private String empCode;
	
	private String orgCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
}
