/** 
 * @项目名称: FSP
 * @文件名称: Org extends BaseEntity 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import cn.uce.base.entity.BaseEntity;

/***
 * 机构
 * @author laizd
 * @since 2016-11-204
 */
public class Org extends BaseEntity {

	/**
	 *serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 机构ID. */
	private Integer orgId;
	/** 机构编号.机构编号唯一 */
	private String orgCode;
	/** 机构名称.同一机构下,机构名称唯一 */
	private String orgName;
	/** 机构全称.对于部门标识的,由上级名称加上本级名称 */
	private String orgFullName;
	/** 网点名称全拼. */
	private String namePy;
	/** 网点名称拼音缩写. */
	private String nameSpy;
	/** 机构排序.orgSort */
	private Integer orgSort;
	/** 创建机构 */
	private Integer createOrg;
	/** 机构类型.总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 */
	private Integer orgType;
	/** 部门标识.0表示机构.,1表示部门 */
	private Boolean deptFlag;
	/** 所属机构ID.最上级机构为0 */
	private Integer parentOrg;
	/** 负责人. */
	private Integer principal;
	/** 分管总裁.主要是审批用 */
	private Integer chargePresident;
	/** 标签 */
	private Integer orgLabel;
	/** 状态.新建，审核，正常，暂停，停用 */
	private Integer status;
	/** 备注. */
	private String remark;
	/** 创建人 */
	private String createEmp;
	/** 删除标识 */
	private Boolean deleteFlag;
	/** 更新人 */
	private String updateEmp;
	/** 更新机构 */
	private Integer updateOrg;
	/** 隶属机构名称 */
	private String affiliatedOrgName;
	/** 隶属机构ID */
	private Integer affiliatedOrg;
	/** 机构id全称.  */
	private String orgFullId;
	/**
	 * 是否同步钉钉  add by 崔仁军  2017-10-18
	 */
	private Boolean syncDingFlag;
	
	/**
	 * 是否同步钉钉  add by 崔仁军  2017-10-18
	 */
	public Boolean getSyncDingFlag() {
		return syncDingFlag;
	}
	/**
	 * 是否同步钉钉  add by 崔仁军  2017-10-18
	 */
	public void setSyncDingFlag(Boolean syncDingFlag) {
		this.syncDingFlag = syncDingFlag;
	}
	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @return the orgFullName
	 */
	public String getOrgFullName() {
		return orgFullName;
	}
	/**
	 * @return the namePy
	 */
	public String getNamePy() {
		return namePy;
	}
	/**
	 * @return the nameSpy
	 */
	public String getNameSpy() {
		return nameSpy;
	}
	/**
	 * @return the orgSort
	 */
	public Integer getOrgSort() {
		return orgSort;
	}
	/**
	 * @return the createOrg
	 */
	public Integer getCreateOrg() {
		return createOrg;
	}
	/**
	 * @return the orgType
	 */
	public Integer getOrgType() {
		return orgType;
	}
	/**
	 * @return the deptFlag
	 */
	public Boolean getDeptFlag() {
		return deptFlag;
	}
	/**
	 * @return the parentOrg
	 */
	public Integer getParentOrg() {
		return parentOrg;
	}
	/**
	 * @return the principal
	 */
	public Integer getPrincipal() {
		return principal;
	}
	/**
	 * @return the chargePresident
	 */
	public Integer getChargePresident() {
		return chargePresident;
	}
	/**
	 * @return the orgLabel
	 */
	public Integer getOrgLabel() {
		return orgLabel;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @return the createEmp
	 */
	public String getCreateEmp() {
		return createEmp;
	}
	/**
	 * @return the deleteFlag
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * @return the updateEmp
	 */
	public String getUpdateEmp() {
		return updateEmp;
	}
	/**
	 * @return the updateOrg
	 */
	public Integer getUpdateOrg() {
		return updateOrg;
	}
	/**
	 * @return the affiliatedOrgName
	 */
	public String getAffiliatedOrgName() {
		return affiliatedOrgName;
	}
	/**
	 * @return the affiliatedOrg
	 */
	public Integer getAffiliatedOrg() {
		return affiliatedOrg;
	}
	/**
	 * @return the orgFullId
	 */
	public String getOrgFullId() {
		return orgFullId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @param orgFullName the orgFullName to set
	 */
	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}
	/**
	 * @param namePy the namePy to set
	 */
	public void setNamePy(String namePy) {
		this.namePy = namePy;
	}
	/**
	 * @param nameSpy the nameSpy to set
	 */
	public void setNameSpy(String nameSpy) {
		this.nameSpy = nameSpy;
	}
	/**
	 * @param orgSort the orgSort to set
	 */
	public void setOrgSort(Integer orgSort) {
		this.orgSort = orgSort;
	}
	/**
	 * @param createOrg the createOrg to set
	 */
	public void setCreateOrg(Integer createOrg) {
		this.createOrg = createOrg;
	}
	/**
	 * @param orgType the orgType to set
	 */
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	/**
	 * @param deptFlag the deptFlag to set
	 */
	public void setDeptFlag(Boolean deptFlag) {
		this.deptFlag = deptFlag;
	}
	/**
	 * @param parentOrg the parentOrg to set
	 */
	public void setParentOrg(Integer parentOrg) {
		this.parentOrg = parentOrg;
	}
	/**
	 * @param principal the principal to set
	 */
	public void setPrincipal(Integer principal) {
		this.principal = principal;
	}
	/**
	 * @param chargePresident the chargePresident to set
	 */
	public void setChargePresident(Integer chargePresident) {
		this.chargePresident = chargePresident;
	}
	/**
	 * @param orgLabel the orgLabel to set
	 */
	public void setOrgLabel(Integer orgLabel) {
		this.orgLabel = orgLabel;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @param createEmp the createEmp to set
	 */
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}
	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * @param updateEmp the updateEmp to set
	 */
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}
	/**
	 * @param updateOrg the updateOrg to set
	 */
	public void setUpdateOrg(Integer updateOrg) {
		this.updateOrg = updateOrg;
	}
	/**
	 * @param affiliatedOrgName the affiliatedOrgName to set
	 */
	public void setAffiliatedOrgName(String affiliatedOrgName) {
		this.affiliatedOrgName = affiliatedOrgName;
	}
	/**
	 * @param affiliatedOrg the affiliatedOrg to set
	 */
	public void setAffiliatedOrg(Integer affiliatedOrg) {
		this.affiliatedOrg = affiliatedOrg;
	}
	/**
	 * @param orgFullId the orgFullId to set
	 */
	public void setOrgFullId(String orgFullId) {
		this.orgFullId = orgFullId;
	}
}
