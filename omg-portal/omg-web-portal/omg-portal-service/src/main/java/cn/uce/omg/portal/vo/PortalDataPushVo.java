package cn.uce.omg.portal.vo;

import java.io.Serializable;
import java.util.Date;

public class PortalDataPushVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dataType;

	private String pushRange;
	
	private Date startTime;
	
	private Date endTime;
	
	private String receiver;
	
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getPushRange() {
		return pushRange;
	}

	public void setPushRange(String pushRange) {
		this.pushRange = pushRange;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
}
