package cn.uce.omg.portal.vo;

import java.util.List;

import cn.uce.base.vo.BaseVo;

/**
 * @Description: (UserSearchVo) 
 * @author ZB
 * @date 2018年3月29日 上午11:14:56
 */
public class UserSearchVo extends BaseVo{

    private static final long serialVersionUID = 1L;
    /** 机构ID. */
    private String orgId;
	/** 机构编号.机构编号唯一 */
    private String orgCode;
    /** 员工编号.员工工号 */
    private String empCode;
    
    private String empName;
    /** 是否启用.0/1 默认为0 */
    private Boolean enabled;
    
    private List<String> idList;
    
	/**
	 * @return the idList
	 */
	public List<String> getIdList() {
		return idList;
	}
	/**
	 * @param idList the idList to set
	 */
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	/**
	 * @return the orgId
	 */
	public String getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
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
	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}
	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
    
    
   
}
