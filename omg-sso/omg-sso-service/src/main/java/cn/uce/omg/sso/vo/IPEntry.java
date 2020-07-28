/** 
 * @项目名称: FSP
 * @文件名称: IPEntry 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

/**
 * * 一条IP范围记录，不仅包括国家和区域，也包括起始IP和结束IP *
 * @author
 */
public class IPEntry {
	/** 开始ip段 */
	public String beginIp;
	/** 截止ip段 */
	public String endIp;
	/** 国家 */
	public String country;
	/** 地区 */
	public String area;

	/**
	 * 构造函数
	 */
	public IPEntry() {
		beginIp = endIp = country = area = "";
	}

	/**
	 * @return the beginIp
	 */
	public String getBeginIp() {
		return beginIp;
	}

	/**
	 * @param beginIp the beginIp to set
	 */
	public void setBeginIp(String beginIp) {
		this.beginIp = beginIp;
	}

	/**
	 * @return the endIp
	 */
	public String getEndIp() {
		return endIp;
	}

	/**
	 * @param endIp the endIp to set
	 */
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	public String toString() {
		return this.area + "  " + this.country + "IP范围:" + this.beginIp + "-" + this.endIp;
	}
}
