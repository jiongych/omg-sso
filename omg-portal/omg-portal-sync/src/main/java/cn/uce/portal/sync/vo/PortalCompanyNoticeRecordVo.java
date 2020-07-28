package cn.uce.portal.sync.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告查看记录
 *<pre>
 * PortalCompanyNoticeRecord
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月31日下午2:01:11
 */
public class PortalCompanyNoticeRecordVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**id. */
	private Long id;
	
	/**公告id */
	private Long noticeId;
	
	/**查看公告人员 */
	private String viewUser;
	/**查看公告人员 */
	private String empCode;
	/**查看公告部门ID */
	private String orgCode;
	/**查看公告人员姓名 */
	private String empName;
	
	private Date createTime;
	
	/**查看公告时间 */
	private Date viewTime;
	
	/**查看标志 */
	private boolean viewFlag;
	
	/**
	 * 数据来源
	 */
	private int fromSource;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getViewUser() {
		return viewUser;
	}

	public void setViewUser(String viewUser) {
		this.viewUser = viewUser;
	}

	public Date getViewTime() {
		return viewTime;
	}

	public void setViewTime(Date viewTime) {
		this.viewTime = viewTime;
	}

	public boolean isViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(boolean viewFlag) {
		this.viewFlag = viewFlag;
	}

	public int getFromSource() {
		return fromSource;
	}

	public void setFromSource(int fromSource) {
		this.fromSource = fromSource;
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

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
