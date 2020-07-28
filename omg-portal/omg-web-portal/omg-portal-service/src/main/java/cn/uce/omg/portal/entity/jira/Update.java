package cn.uce.omg.portal.entity.jira;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Update implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("customfield_11213")
	private List<Customfield11213> customfield_11213;
	@JsonProperty("customfield_11211")
	private List<Customfield11211> customfield_11211;
	@JsonProperty("customfield_11214")
	private List<Customfield11214> customfield_11214;
	private List<Comment> comment;

	public List<Customfield11213> getCustomfield_11213() {
		return customfield_11213;
	}

	public void setCustomfield_11213(List<Customfield11213> customfield_11213) {
		this.customfield_11213 = customfield_11213;
	}

	public List<Customfield11211> getCustomfield_11211() {
		return customfield_11211;
	}

	public void setCustomfield_11211(List<Customfield11211> customfield_11211) {
		this.customfield_11211 = customfield_11211;
	}

	public List<Customfield11214> getCustomfield_11214() {
		return customfield_11214;
	}

	public void setCustomfield_11214(List<Customfield11214> customfield_11214) {
		this.customfield_11214 = customfield_11214;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

}
