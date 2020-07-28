package cn.uce.omg.portal.vo;

import java.io.Serializable;
import java.util.Date;

public class LogVo implements Serializable {
	private static final long serialVersionUID = -8577767581615293034L;
	private int id;
	private String operatorEmp;
	private String operatorEmpName;
	private String operatorTable;
	private String operatorMenu;
	private String operatorType;
	private String operatorBefore;
	private String operatorAfter;
	private String operatorIp;
	private String requestId;
	private Date createdTime;
	private Date updatedTime;
	private String createdOrg;
	private String createdOrgName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperatorEmp() {
		return operatorEmp;
	}

	public void setOperatorEmp(String operatorEmp) {
		this.operatorEmp = operatorEmp;
	}

	public String getOperatorTable() {
		return operatorTable;
	}

	public void setOperatorTable(String operatorTable) {
		this.operatorTable = operatorTable;
	}

	public String getOperatorMenu() {
		return operatorMenu;
	}

	public void setOperatorMenu(String operatorMenu) {
		this.operatorMenu = operatorMenu;
	}

	public String getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorBefore() {
		return operatorBefore;
	}

	public void setOperatorBefore(String operatorBefore) {
		this.operatorBefore = operatorBefore;
	}

	public String getOperatorAfter() {
		return operatorAfter;
	}

	public void setOperatorAfter(String operatorAfter) {
		this.operatorAfter = operatorAfter;
	}

	public String getOperatorIp() {
		return operatorIp;
	}

	public void setOperatorIp(String operatorIp) {
		this.operatorIp = operatorIp;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedOrg() {
		return createdOrg;
	}

	public void setCreatedOrg(String createdOrg) {
		this.createdOrg = createdOrg;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOperatorEmpName() {
		return operatorEmpName;
	}

	public void setOperatorEmpName(String operatorEmpName) {
		this.operatorEmpName = operatorEmpName;
	}

	public String getCreatedOrgName() {
		return createdOrgName;
	}

	public void setCreatedOrgName(String createdOrgName) {
		this.createdOrgName = createdOrgName;
	}
}
