/** 
 * @项目名称: FSP
 * @文件名称: IpInfo extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;

/**
 * ip信息
 * @author huangting
 * @date 2017年6月9日 下午3:26:14
 */
public class IpInfo extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 起始段. */
    private String startSection;

    /** 截止段. */
    private String endSection;

    /** 起始数值.ip地址转换成long类型的地址 */
    private Long startNum;

    /** 截止数值. */
    private Long endNum;

    /** 国家.国外地址可能比较长，全部显示同一个即可 */
    private String country;

    /** 省份. */
    private String province;

    /** 城市. */
    private String city;

    /** 区/县. */
    private String county;

    /** 地址.纯真库的所有地址综合 */
    private String addr;

    /** 运营商.运营商地址，可能会比较长 */
    private String operator;

    /** 备注. */
    private String remark;

	/**
	 * @return the startSection
	 */
	public String getStartSection() {
		return startSection;
	}

	/**
	 * @return the endSection
	 */
	public String getEndSection() {
		return endSection;
	}

	/**
	 * @return the startNum
	 */
	public Long getStartNum() {
		return startNum;
	}

	/**
	 * @return the endNum
	 */
	public Long getEndNum() {
		return endNum;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param startSection the startSection to set
	 */
	public void setStartSection(String startSection) {
		this.startSection = startSection;
	}

	/**
	 * @param endSection the endSection to set
	 */
	public void setEndSection(String endSection) {
		this.endSection = endSection;
	}

	/**
	 * @param startNum the startNum to set
	 */
	public void setStartNum(Long startNum) {
		this.startNum = startNum;
	}

	/**
	 * @param endNum the endNum to set
	 */
	public void setEndNum(Long endNum) {
		this.endNum = endNum;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
