package cn.uce.omg.portal.entity.jira;

import java.util.List;

public class IssueDetail extends Issue {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 联系人姓名
	 */
	private String empName;
	/**
	 * 联系人手机号码
	 */
	private String empTel;
	
	/**
	 * 上报人姓名
	 */
	private String reportEmpName;
	
	
	/**
	 * 上报人手机号码
	 */
	private String reportTel;
	
	private List<Attachment> attachments;
	/**
	 * 附件列表
	 */
	private List<String> picUrl;
	/**
	 * 处理人列表信息
	 */
	private List<JiraHandle> jiraHandles;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpTel() {
		return empTel;
	}

	public void setEmpTel(String empTel) {
		this.empTel = empTel;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<String> getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(List<String> picUrl) {
		this.picUrl = picUrl;
	}

	public List<JiraHandle> getJiraHandles() {
		return jiraHandles;
	}

	public void setJiraHandles(List<JiraHandle> jiraHandles) {
		this.jiraHandles = jiraHandles;
	}

	public String getReportEmpName() {
		return reportEmpName;
	}

	public void setReportEmpName(String reportEmpName) {
		this.reportEmpName = reportEmpName;
	}

	public String getReportTel() {
		return reportTel;
	}

	public void setReportTel(String reportTel) {
		this.reportTel = reportTel;
	}

}
