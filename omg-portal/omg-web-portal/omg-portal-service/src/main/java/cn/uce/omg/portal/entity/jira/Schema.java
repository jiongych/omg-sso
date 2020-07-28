package cn.uce.omg.portal.entity.jira;

import java.io.Serializable;

public class Schema implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String system;
	private String type;

	public void setSystem(String system) {
		this.system = system;
	}

	public String getSystem() {
		return this.system;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
