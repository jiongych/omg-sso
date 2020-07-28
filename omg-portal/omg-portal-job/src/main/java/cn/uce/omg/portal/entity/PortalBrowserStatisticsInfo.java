package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;

public class PortalBrowserStatisticsInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4858686909963440443L;

	private String statisticsDate;
	private Integer loginTimes;
	private Integer loginPeoples;
	private String statisticsType;
	private String statisticsCategory;
	private String statisticsName;
	private String remark;
	public String getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(String statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Integer getLoginPeoples() {
		return loginPeoples;
	}

	public void setLoginPeoples(Integer loginPeoples) {
		this.loginPeoples = loginPeoples;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	public String getStatisticsCategory() {
		return statisticsCategory;
	}

	public void setStatisticsCategory(String statisticsCategory) {
		this.statisticsCategory = statisticsCategory;
	}

	public String getStatisticsName() {
		return statisticsName;
	}

	public void setStatisticsName(String statisticsName) {
		this.statisticsName = statisticsName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
