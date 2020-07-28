package cn.uce.omg.portal.entity.jira;

import java.io.Serializable;
import java.util.List;

public class Fields implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> clausenames;
	private boolean custom;
	private String id;
	private String name;
	private boolean navigable;
	private boolean orderable;
	private Schema schema;
	private boolean searchable;
	public List<String> getClausenames() {
		return clausenames;
	}
	public void setClausenames(List<String> clausenames) {
		this.clausenames = clausenames;
	}
	public boolean isCustom() {
		return custom;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isNavigable() {
		return navigable;
	}
	public void setNavigable(boolean navigable) {
		this.navigable = navigable;
	}
	public boolean isOrderable() {
		return orderable;
	}
	public void setOrderable(boolean orderable) {
		this.orderable = orderable;
	}
	public Schema getSchema() {
		return schema;
	}
	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

}
