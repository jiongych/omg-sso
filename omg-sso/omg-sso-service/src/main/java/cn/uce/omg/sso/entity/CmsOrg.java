/** 
 * @项目名称: FSP
 * @文件名称: CmsOrg implements Serializable 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 乾坤机构
 * @author huangting
 * @date 2017年4月5日
 */
public class CmsOrg implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /**
     * 机构ID
     */
    private Integer orgId;
    /**
     * 机构编号.机构编号唯一 
     */
    private String orgCode;
    /**
     * 乾坤机构id
     */
    private Integer cmsOrgId;
    /**
     * 基准编号
     */
    private String baseOrgCode;
    /**
     * 机构名称（简体）.机构名称唯一
     */
    private String orgName;
    /**
     * 机构简称（繁体）.机构名称唯一
     */
    private String orgNameTw;
    /**
     * 机构简称（英文）.机构名称唯一
     */
    private String orgNameEn;
    /**
     * 网点名称全拼
     */
    private String namePy;
    /**
     * 网点名称拼音缩写
     */
    private String nameSpy;
    /**
     * 机构类型.总部-10,财务中心-20,操作中心-21,网点-30,承包区-40
     */
    private Integer orgType;
    /**
     * 所属机构ID.只有总部允许为空
     */
    private Integer parentOrg;
    /**
     * 所属操作中心
     */
    private Integer operateCenter;
    /**
     * 所属财务中心
     */
    private Integer financeCenter;
    /**
     * 所属管理中心
     */
    private Integer manageCenter;
    /**
     * 对帐机构.DEF = ORG_ID  当为承包区时默认为上一级的值
     */
    private Integer accOrg;
    /**
     * 所属机构编码
     */
    private String parentOrgCode;
    /**
     * 所属操作中心
     */
    private String operateCenterCode;
    /**
     * 所属财务中心
     */
    private String financeCenterCode;
    /**
     * 所属管理中心
     */
    private String manageCenterCode;
    /**
     * 对帐机构
     */
    private String accOrgCode;
    /**
     * 币别
     */
    private Integer currency;
    /**
     * 状态.新建，审核，正常，暂停，停用
     */
    private Integer status;
    /**
     * 网点属性.来自系统参数表:加盟、直营、同行、出货口、代派
     */
    private Integer orgProperty;
    /**
     * 生效时间.预留字段 
     */
    private Date effectTime;
    /**
     * 失效时间.预留字段
     */
    private Date expireTime;
    /**
     * 机构id全称
     */
    private String orgFullId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除标识.1表示已删除，默认为0
     */
    private Boolean deleteFlag;
    /**
     * 创建人
     */
    private String createEmp;
    /**
     * 创建机构
     */
    private Integer createOrg;
    /**
     * 更新人
     */
    private String updateEmp;
    /**
     * 更新机构
     */
    private Integer updateOrg;
    /**
     * 状态更新描述
     */
    private String statusUpdateDesc;
    /**
     * 状态更新时间
     */
    private Date statusUpdateTime;
    /**
     * 编号全称
     */
    /*private String fullOrgCode;*/

	/**
	 * 版本
	 */
	private Integer version;

    /**
     * 编号全称
     */
    private String orgFullCode;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	private Date syncTime;
	
	//add by huangting 20180523
	/**
	 * 是否测试机构
	 */
	private Boolean testFlag;
	
	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the cmsOrgId
	 */
	public Integer getCmsOrgId() {
		return cmsOrgId;
	}

	/**
	 * @param cmsOrgId the cmsOrgId to set
	 */
	public void setCmsOrgId(Integer cmsOrgId) {
		this.cmsOrgId = cmsOrgId;
	}

	/**
	 * @return the baseOrgCode
	 */
	public String getBaseOrgCode() {
		return baseOrgCode;
	}

	/**
	 * @param baseOrgCode the baseOrgCode to set
	 */
	public void setBaseOrgCode(String baseOrgCode) {
		this.baseOrgCode = baseOrgCode;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the orgNameTw
	 */
	public String getOrgNameTw() {
		return orgNameTw;
	}

	/**
	 * @param orgNameTw the orgNameTw to set
	 */
	public void setOrgNameTw(String orgNameTw) {
		this.orgNameTw = orgNameTw;
	}

	/**
	 * @return the orgNameEn
	 */
	public String getOrgNameEn() {
		return orgNameEn;
	}

	/**
	 * @param orgNameEn the orgNameEn to set
	 */
	public void setOrgNameEn(String orgNameEn) {
		this.orgNameEn = orgNameEn;
	}

	/**
	 * @return the namePy
	 */
	public String getNamePy() {
		return namePy;
	}

	/**
	 * @param namePy the namePy to set
	 */
	public void setNamePy(String namePy) {
		this.namePy = namePy;
	}

	/**
	 * @return the nameSpy
	 */
	public String getNameSpy() {
		return nameSpy;
	}

	/**
	 * @param nameSpy the nameSpy to set
	 */
	public void setNameSpy(String nameSpy) {
		this.nameSpy = nameSpy;
	}

	/**
	 * @return the orgType
	 */
	public Integer getOrgType() {
		return orgType;
	}

	/**
	 * @param orgType the orgType to set
	 */
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}

	/**
	 * @return the parentOrg
	 */
	public Integer getParentOrg() {
		return parentOrg;
	}

	/**
	 * @param parentOrg the parentOrg to set
	 */
	public void setParentOrg(Integer parentOrg) {
		this.parentOrg = parentOrg;
	}

	/**
	 * @return the operateCenter
	 */
	public Integer getOperateCenter() {
		return operateCenter;
	}

	/**
	 * @param operateCenter the operateCenter to set
	 */
	public void setOperateCenter(Integer operateCenter) {
		this.operateCenter = operateCenter;
	}

	/**
	 * @return the financeCenter
	 */
	public Integer getFinanceCenter() {
		return financeCenter;
	}

	/**
	 * @param financeCenter the financeCenter to set
	 */
	public void setFinanceCenter(Integer financeCenter) {
		this.financeCenter = financeCenter;
	}

	/**
	 * @return the manageCenter
	 */
	public Integer getManageCenter() {
		return manageCenter;
	}

	/**
	 * @param manageCenter the manageCenter to set
	 */
	public void setManageCenter(Integer manageCenter) {
		this.manageCenter = manageCenter;
	}

	/**
	 * @return the accOrg
	 */
	public Integer getAccOrg() {
		return accOrg;
	}

	/**
	 * @param accOrg the accOrg to set
	 */
	public void setAccOrg(Integer accOrg) {
		this.accOrg = accOrg;
	}

	/**
	 * @return the parentOrgCode
	 */
	public String getParentOrgCode() {
		return parentOrgCode;
	}

	/**
	 * @param parentOrgCode the parentOrgCode to set
	 */
	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	/**
	 * @return the operateCenterCode
	 */
	public String getOperateCenterCode() {
		return operateCenterCode;
	}

	/**
	 * @param operateCenterCode the operateCenterCode to set
	 */
	public void setOperateCenterCode(String operateCenterCode) {
		this.operateCenterCode = operateCenterCode;
	}

	/**
	 * @return the financeCenterCode
	 */
	public String getFinanceCenterCode() {
		return financeCenterCode;
	}

	/**
	 * @param financeCenterCode the financeCenterCode to set
	 */
	public void setFinanceCenterCode(String financeCenterCode) {
		this.financeCenterCode = financeCenterCode;
	}

	/**
	 * @return the manageCenterCode
	 */
	public String getManageCenterCode() {
		return manageCenterCode;
	}

	/**
	 * @param manageCenterCode the manageCenterCode to set
	 */
	public void setManageCenterCode(String manageCenterCode) {
		this.manageCenterCode = manageCenterCode;
	}

	/**
	 * @return the accOrgCode
	 */
	public String getAccOrgCode() {
		return accOrgCode;
	}

	/**
	 * @param accOrgCode the accOrgCode to set
	 */
	public void setAccOrgCode(String accOrgCode) {
		this.accOrgCode = accOrgCode;
	}

	/**
	 * @return the currency
	 */
	public Integer getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the orgProperty
	 */
	public Integer getOrgProperty() {
		return orgProperty;
	}

	/**
	 * @param orgProperty the orgProperty to set
	 */
	public void setOrgProperty(Integer orgProperty) {
		this.orgProperty = orgProperty;
	}

	/**
	 * @return the effectTime
	 */
	public Date getEffectTime() {
		return effectTime;
	}

	/**
	 * @param effectTime the effectTime to set
	 */
	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	/**
	 * @return the expireTime
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return the orgFullId
	 */
	public String getOrgFullId() {
		return orgFullId;
	}

	/**
	 * @param orgFullId the orgFullId to set
	 */
	public void setOrgFullId(String orgFullId) {
		this.orgFullId = orgFullId;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the deleteFlag
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the createEmp
	 */
	public String getCreateEmp() {
		return createEmp;
	}

	/**
	 * @param createEmp the createEmp to set
	 */
	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	/**
	 * @return the createOrg
	 */
	public Integer getCreateOrg() {
		return createOrg;
	}

	/**
	 * @param createOrg the createOrg to set
	 */
	public void setCreateOrg(Integer createOrg) {
		this.createOrg = createOrg;
	}

	/**
	 * @return the updateEmp
	 */
	public String getUpdateEmp() {
		return updateEmp;
	}

	/**
	 * @param updateEmp the updateEmp to set
	 */
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	/**
	 * @return the updateOrg
	 */
	public Integer getUpdateOrg() {
		return updateOrg;
	}

	/**
	 * @param updateOrg the updateOrg to set
	 */
	public void setUpdateOrg(Integer updateOrg) {
		this.updateOrg = updateOrg;
	}

	/**
	 * @return the statusUpdateDesc
	 */
	public String getStatusUpdateDesc() {
		return statusUpdateDesc;
	}

	/**
	 * @param statusUpdateDesc the statusUpdateDesc to set
	 */
	public void setStatusUpdateDesc(String statusUpdateDesc) {
		this.statusUpdateDesc = statusUpdateDesc;
	}

	/**
	 * @return the statusUpdateTime
	 */
	public Date getStatusUpdateTime() {
		return statusUpdateTime;
	}

	/**
	 * @param statusUpdateTime the statusUpdateTime to set
	 */
	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}

	/**
	 * @return the fullOrgCode
	 */
	/*public String getFullOrgCode() {
		return fullOrgCode;
	}*/

	/**
	 * @param fullOrgCode the fullOrgCode to set
	 */
	/*public void setFullOrgCode(String fullOrgCode) {
		this.fullOrgCode = fullOrgCode;
	}*/

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getOrgFullCode() {
		return orgFullCode;
	}

	public void setOrgFullCode(String orgFullCode) {
		this.orgFullCode = orgFullCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getTestFlag() {
		return testFlag;
	}

	public void setTestFlag(Boolean testFlag) {
		this.testFlag = testFlag;
	}
}
