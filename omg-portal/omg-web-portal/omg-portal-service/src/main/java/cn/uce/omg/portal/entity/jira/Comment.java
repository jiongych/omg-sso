package cn.uce.omg.portal.entity.jira;

import java.io.Serializable;

public class Comment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Add add;

	public Add getAdd() {
		return add;
	}

	public void setAdd(Add add) {
		this.add = add;
	}

}
