package cn.uce.omg.portal.entity.jira;

import java.io.Serializable;
import java.util.Map;

public class Issue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String empCode;
	private String empName;
	private String orgName;
	private String telephone;
	private String title;
	private Integer bizType;
    private String bizTypeName;
	private String issueTypeId;
	//问题描述
	private String description;
	//编号
	private String key;
	private String id;
	//状态
	private String status;
	//创建时间
	private String createTimeStr;
	//结束时间
	private String endTime;

	
	private String displayName;
	private String issueType;
	//解决方案
	private String solution;
	//注释
	private String addPromble;
	private String comment;
	private String score;
	private Map<String, Integer> issueTypeTotal;
	private int searchIssueType;
	private String[] imageBase;
	private String[] imageBaseName;
	private String incSatisfaction;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIssueTypeId() {
		return issueTypeId;
	}

	public void setIssueTypeId(String issueTypeId) {
		this.issueTypeId = issueTypeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getAddPromble() {
		return addPromble;
	}

	public void setAddPromble(String addPromble) {
		this.addPromble = addPromble;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Integer> getIssueTypeTotal() {
		return issueTypeTotal;
	}

	public void setIssueTypeTotal(Map<String, Integer> issueTypeTotal) {
		this.issueTypeTotal = issueTypeTotal;
	}

	public int getSearchIssueType() {
		return searchIssueType;
	}

	public void setSearchIssueType(int searchIssueType) {
		this.searchIssueType = searchIssueType;
	}

	public String[] getImageBase() {
		return imageBase;
	}

	public void setImageBase(String[] imageBase) {
		this.imageBase = imageBase;
	}

	public String[] getImageBaseName() {
		return imageBaseName;
	}

	public void setImageBaseName(String[] imageBaseName) {
		this.imageBaseName = imageBaseName;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getBizTypeName() {
		return bizTypeName;
	}

	public void setBizTypeName(String bizTypeName) {
		this.bizTypeName = bizTypeName;
	}
	
	public String getIncSatisfaction() {
		return incSatisfaction;
	}

	public void setIncSatisfaction(String incSatisfaction) {
		this.incSatisfaction = incSatisfaction;
	}

}
