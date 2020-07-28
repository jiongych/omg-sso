package cn.uce.portal.common.model;

import java.io.Serializable;

public class EasyUIPage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer currentPage;
	private Integer pageSize;
	private Integer totalRows;
	private String orderColumn;
	private boolean desc;

	public Integer getCurrentPage() {
		return this.currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String getOrderColumn() {
		return this.orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public boolean isDesc() {
		return this.desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}
}
