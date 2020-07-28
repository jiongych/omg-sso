package cn.uce.omg.portal.entity.jira;

import java.io.Serializable;

public class JsonRootBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Update update;
	private Transition transition;

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public Transition getTransition() {
		return transition;
	}

	public void setTransition(Transition transition) {
		this.transition = transition;
	}

}
