package cn.uce.omg.portal.vo;

import java.util.Date;
import java.util.List;

import cn.uce.base.vo.BaseVo;

/**
 * @Description: (UserRoleRelVo) 
 * @author XJ
 * @date 2017年8月31日 下午7:00:43
 */
public class PortalUserRoleRelVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	private Long Id;
    private String empCode;
    private String roleCode;
    private String createEmp;
    private String updateEmp;
    private String createOrg;
    private Date createTime;
    private Date updateTime;
    private Integer version;
    private List<String> roleCodeList;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getCreateEmp() {
		return createEmp;
	}
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}
	public String getCreateOrg() {
		return createOrg;
	}
	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public List<String> getRoleCodeList() {
		return roleCodeList;
	}
	public void setRoleCodeList(List<String> roleCodeList) {
		this.roleCodeList = roleCodeList;
	}
	/**
	 * @return the updateEmp
	 */
	public String getUpdateEmp() {
		return updateEmp;
	}
	/**
	 * @param updateEmp the updateEmp to set
	 */
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}
	
    
}
