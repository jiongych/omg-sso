package cn.uce.omg.portal.vo;

import java.util.List;

import cn.uce.base.vo.BaseVo;

public class OtherEmpRelationVo extends BaseVo{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer empId;
	private String empCode;
	private String empName;
	private Integer otherEmpId;
	private String otherEmpCode;
	private String otherEmpName;
	private List<OtherOrgRelationVo> otherOrgRelationList;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public Integer getOtherEmpId() {
		return otherEmpId;
	}
	public void setOtherEmpId(Integer otherEmpId) {
		this.otherEmpId = otherEmpId;
	}
	public String getOtherEmpCode() {
		return otherEmpCode;
	}
	public void setOtherEmpCode(String otherEmpCode) {
		this.otherEmpCode = otherEmpCode;
	}
	public String getOtherEmpName() {
		return otherEmpName;
	}
	public void setOtherEmpName(String otherEmpName) {
		this.otherEmpName = otherEmpName;
	}
	public List<OtherOrgRelationVo> getOtherOrgRelationList() {
		return otherOrgRelationList;
	}
	public void setOtherOrgRelationList(
			List<OtherOrgRelationVo> otherOrgRelationList) {
		this.otherOrgRelationList = otherOrgRelationList;
	}
	
}
