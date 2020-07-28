package cn.uce.omg.portal.vo;


/**
 * 
 * @Description: (OrgZTreeNodeVo) 
 * @author XJ
 * @date 2017年4月22日 上午8:28:03
 */
public class OrgZTreeNodeVo extends ZTreeNodeVo<OrgZTreeNodeVo>{

	private static final long serialVersionUID = 1L;
	private Integer orgType;
    private String orgCode;
    private String state;
    private String empCode;
    
    
	/**
	 * @return the empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode the empCode to set
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
