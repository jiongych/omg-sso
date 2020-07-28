package cn.uce.omg.portal.entity.jira;

import java.io.Serializable;
import java.util.Date;

/**
 * 处理详情
 *<pre>
 * JiraComment
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年3月18日上午10:05:00
 */
public class JiraHandle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 处理人名字
	 */
	private String displayName;
	
	/**
	 * 审核状态
	 */
	private Boolean active;
	
	/**
	 * 创建时间
	 */
	private Date created;
	
	/**
	 * 更新时间
	 */
	private Date updated;
	/**
	 * 处理内容
	 */
	private String body;

	/**
	 * 处理人邮箱地址
	 */
	private String emailAddress;
	
	private String status;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
