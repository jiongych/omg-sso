package cn.uce.omg.sso.vo.portal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 消息状态VO
 *<pre>
 * PortalMessageStatusVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年5月13日下午4:39:02
 */
public class PortalMessageStatusVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 消息内容
	 */
	private String message;
	
    /** 请求唯一码. */
    private String reqId;
    
    private List<String> reqIds;
    
    private List<String> empCodes;
    
    /** 消息接收者. */
	private String empCode;
	
	/** 消息变更时间. */
	private Date createTime;
	
	/** 消息状态. */
	private String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getReqIds() {
		return reqIds;
	}

	public void setReqIds(List<String> reqIds) {
		this.reqIds = reqIds;
	}

	public List<String> getEmpCodes() {
		return empCodes;
	}

	public void setEmpCodes(List<String> empCodes) {
		this.empCodes = empCodes;
	}
	
}
