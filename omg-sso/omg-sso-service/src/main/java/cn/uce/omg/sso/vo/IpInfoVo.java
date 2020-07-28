/** 
 * @项目名称: FSP
 * @文件名称: IpInfoVo extends BaseVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.vo;

import java.util.Date;
import cn.uce.base.vo.BaseVo;

/**
 * ip信息vo
 * @author huangting
 * @date 2017年6月8日 下午2:47:24
 */
public class IpInfoVo extends BaseVo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** ipID. */
    private Long id;

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

    /** 版本. */
    private Integer version;

    /** 备注. */
    private String remark;

    /** 创建时间. */
    private Date createTime;

    /** 修改时间. */
    private Date updateTime;



    /** 取得 ipID. */
    public Long getId() {
        return this.id;
    }

    /** 设置 ipID. */
    public void setId(Long id) {
        this.id = id;
    }
    /** 取得 起始段. */
    public String getStartSection() {
        return this.startSection;
    }

    /** 设置 起始段. */
    public void setStartSection(String startSection) {
        this.startSection = startSection;
    }
    /** 取得 截止段. */
    public String getEndSection() {
        return this.endSection;
    }

    /** 设置 截止段. */
    public void setEndSection(String endSection) {
        this.endSection = endSection;
    }
    /** 取得 起始数值.ip地址转换成long类型的地址 */
    public Long getStartNum() {
        return this.startNum;
    }

    /** 设置 起始数值.ip地址转换成long类型的地址 */
    public void setStartNum(Long startNum) {
        this.startNum = startNum;
    }
    /** 取得 截止数值. */
    public Long getEndNum() {
        return this.endNum;
    }

    /** 设置 截止数值. */
    public void setEndNum(Long endNum) {
        this.endNum = endNum;
    }
    /** 取得 国家.国外地址可能比较长，全部显示同一个即可 */
    public String getCountry() {
        return this.country;
    }

    /** 设置 国家.国外地址可能比较长，全部显示同一个即可 */
    public void setCountry(String country) {
        this.country = country;
    }
    /** 取得 省份. */
    public String getProvince() {
        return this.province;
    }

    /** 设置 省份. */
    public void setProvince(String province) {
        this.province = province;
    }
    /** 取得 城市. */
    public String getCity() {
        return this.city;
    }

    /** 设置 城市. */
    public void setCity(String city) {
        this.city = city;
    }
    /** 取得 区/县. */
    public String getCounty() {
        return this.county;
    }

    /** 设置 区/县. */
    public void setCounty(String county) {
        this.county = county;
    }
    /** 取得 地址.纯真库的所有地址综合 */
    public String getAddr() {
        return this.addr;
    }

    /** 设置 地址.纯真库的所有地址综合 */
    public void setAddr(String addr) {
        this.addr = addr;
    }
    /** 取得 运营商.运营商地址，可能会比较长 */
    public String getOperator() {
        return this.operator;
    }

    /** 设置 运营商.运营商地址，可能会比较长 */
    public void setOperator(String operator) {
        this.operator = operator;
    }
    /** 取得 版本. */
    public Integer getVersion() {
        return this.version;
    }

    /** 设置 版本. */
    public void setVersion(Integer version) {
        this.version = version;
    }
    /** 取得 备注. */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注. */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /** 取得 创建时间. */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间. */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /** 取得 修改时间. */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /** 设置 修改时间. */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



}
