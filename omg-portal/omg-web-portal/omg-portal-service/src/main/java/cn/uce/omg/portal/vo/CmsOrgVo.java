package cn.uce.omg.portal.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class CmsOrgVo extends BaseVo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 机构ID. */
    private Integer orgId;
    /**乾坤机构ID*/
    private Integer cmsOrgId;
    /** 机构编号.机构编号唯一 */
    private String orgCode;

    /** 机构名称（简体）.机构名称唯一 */
    private String orgName;

    /** 机构简称（繁体）.机构名称唯一 */
    private String orgNameTw;

    /** 机构简称（英文）.机构名称唯一 */
    private String orgNameEn;

    /** 网点名称全拼. */
    private String namePy;

    /** 网点名称拼音缩写. */
    private String nameSpy;

    /** 机构类型.总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 */
    private Integer orgType;

    /** 所属机构ID.只有总部允许为空 */
    private Integer parentOrg;

    /** 所属操作中心. */
    private Integer operateCenter;

    /** 所属财务中心. */
    private Integer financeCenter;

    /** 所属管理中心. */
    private Integer manageCenter;

    /** 对帐机构.DEF = ORG_ID  当为承包区时默认为上一级的值 */
    private Integer accOrg;

    /** 币别. */
    private Integer currency;

    /** 状态.新建，审核，正常，暂停，停用 */
    private Integer status;

    /** 网点属性.来自系统参数表:加盟、直营、同行、出货口、代派 */
    private Integer orgProperty;

    /** 生效时间.预留字段 */
    private Date effectTime;

    /** 失效时间.预留字段 */
    private Date expireTime;

    /** 备注. */
    private String remark;

    /** 删除标识.1表示已删除，默认为0 */
    private Boolean deleteFlag;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;

    /** 所属机构基准编码. */
    private String parentOrgCode;
    
    /** 所属操作中心基准编码. */
    private String operateCenterCode;

    /** 所属财务中心基准编码. */
    private String financeCenterCode;    
 
    /** 所属管理中心基准编码. */
    private String manageCenterCode;    

    /** 对帐机构CODE */
    private String accOrgCode;
    
    /** 机构ID全称. */
    private String orgFullCode;
 
    /** 基准编码. */
    private String baseOrgCode;
    
    //REQ add by huangting 20190322 
    private String queryParam;
    
    public CmsOrgVo(){
    	
    }
    public CmsOrgVo(String baseOrgCode,Integer orgType){
    	this.baseOrgCode = baseOrgCode;
    	this.orgType = orgType;
    }
    /** 取得 机构ID. */
    public Integer getOrgId() {
        return this.orgId;
    }

    /** 设置 机构ID. */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    /** 取得 机构编号.机构编号唯一 */
    public String getOrgCode() {
        return this.orgCode;
    }

    /** 设置 机构编号.机构编号唯一 */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    /** 取得 机构名称（简体）.机构名称唯一 */
    public String getOrgName() {
        return this.orgName;
    }

    /** 设置 机构名称（简体）.机构名称唯一 */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    /** 取得 机构简称（繁体）.机构名称唯一 */
    public String getOrgNameTw() {
        return this.orgNameTw;
    }

    /** 设置 机构简称（繁体）.机构名称唯一 */
    public void setOrgNameTw(String orgNameTw) {
        this.orgNameTw = orgNameTw;
    }
    /** 取得 机构简称（英文）.机构名称唯一 */
    public String getOrgNameEn() {
        return this.orgNameEn;
    }

    /** 设置 机构简称（英文）.机构名称唯一 */
    public void setOrgNameEn(String orgNameEn) {
        this.orgNameEn = orgNameEn;
    }
    /** 取得 网点名称全拼. */
    public String getNamePy() {
        return this.namePy;
    }

    /** 设置 网点名称全拼. */
    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }
    /** 取得 网点名称拼音缩写. */
    public String getNameSpy() {
        return this.nameSpy;
    }

    /** 设置 网点名称拼音缩写. */
    public void setNameSpy(String nameSpy) {
        this.nameSpy = nameSpy;
    }
    /** 取得 机构类型.总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 */
    public Integer getOrgType() {
        return this.orgType;
    }

    /** 设置 机构类型.总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 */
    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }
    /** 取得 所属机构ID.只有总部允许为空 */
    public Integer getParentOrg() {
        return this.parentOrg;
    }

    /** 设置 所属机构ID.只有总部允许为空 */
    public void setParentOrg(Integer parentOrg) {
        this.parentOrg = parentOrg;
    }
    /** 取得 所属操作中心. */
    public Integer getOperateCenter() {
        return this.operateCenter;
    }

    /** 设置 所属操作中心. */
    public void setOperateCenter(Integer operateCenter) {
        this.operateCenter = operateCenter;
    }
    /** 取得 所属财务中心. */
    public Integer getFinanceCenter() {
        return this.financeCenter;
    }

    /** 设置 所属财务中心. */
    public void setFinanceCenter(Integer financeCenter) {
        this.financeCenter = financeCenter;
    }
    /** 取得 所属管理中心. */
    public Integer getManageCenter() {
        return this.manageCenter;
    }

    /** 设置 所属管理中心. */
    public void setManageCenter(Integer manageCenter) {
        this.manageCenter = manageCenter;
    }
    /** 取得 对帐机构.DEF = ORG_ID  当为承包区时默认为上一级的值 */
    public Integer getAccOrg() {
        return this.accOrg;
    }

    /** 设置 对帐机构.DEF = ORG_ID  当为承包区时默认为上一级的值 */
    public void setAccOrg(Integer accOrg) {
        this.accOrg = accOrg;
    }
    /** 取得 币别. */
    public Integer getCurrency() {
        return this.currency;
    }

    /** 设置 币别. */
    public void setCurrency(Integer currency) {
        this.currency = currency;
    }
    /** 取得 状态.新建，审核，正常，暂停，停用 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 状态.新建，审核，正常，暂停，停用 */
    public void setStatus(Integer status) {
        this.status = status;
    }
    /** 取得 网点属性.来自系统参数表:加盟、直营、同行、出货口、代派 */
    public Integer getOrgProperty() {
        return this.orgProperty;
    }

    /** 设置 网点属性.来自系统参数表:加盟、直营、同行、出货口、代派 */
    public void setOrgProperty(Integer orgProperty) {
        this.orgProperty = orgProperty;
    }
    /** 取得 生效时间.预留字段 */
    public Date getEffectTime() {
        return this.effectTime;
    }

    /** 设置 生效时间.预留字段 */
    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }
    /** 取得 失效时间.预留字段 */
    public Date getExpireTime() {
        return this.expireTime;
    }

    /** 设置 失效时间.预留字段 */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
    /** 取得 备注. */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注. */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /** 取得 删除标识.1表示已删除，默认为0 */
    public Boolean getDeleteFlag() {
        return this.deleteFlag;
    }

    /** 设置 删除标识.1表示已删除，默认为0 */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    /** 取得 创建人. */
    public String getCreateEmp() {
        return this.createEmp;
    }

    /** 设置 创建人. */
    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }
    /** 取得 创建机构. */
    public String getCreateOrg() {
        return this.createOrg;
    }

    /** 设置 创建机构. */
    public void setCreateOrg(String createOrg) {
        this.createOrg = createOrg;
    }
    /** 取得 更新人. */
    public String getUpdateEmp() {
        return this.updateEmp;
    }

    /** 设置 更新人. */
    public void setUpdateEmp(String updateEmp) {
        this.updateEmp = updateEmp;
    }
    /** 取得 更新机构. */
    public String getUpdateOrg() {
        return this.updateOrg;
    }

    /** 设置 更新机构. */
    public void setUpdateOrg(String updateOrg) {
        this.updateOrg = updateOrg;
    }

	public Integer getCmsOrgId() {
		return cmsOrgId;
	}

	public void setCmsOrgId(Integer cmsOrgId) {
		this.cmsOrgId = cmsOrgId;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getOperateCenterCode() {
		return operateCenterCode;
	}

	public void setOperateCenterCode(String operateCenterCode) {
		this.operateCenterCode = operateCenterCode;
	}

	public String getFinanceCenterCode() {
		return financeCenterCode;
	}

	public void setFinanceCenterCode(String financeCenterCode) {
		this.financeCenterCode = financeCenterCode;
	}

	public String getManageCenterCode() {
		return manageCenterCode;
	}

	public void setManageCenterCode(String manageCenterCode) {
		this.manageCenterCode = manageCenterCode;
	}

	public String getAccOrgCode() {
		return accOrgCode;
	}

	public void setAccOrgCode(String accOrgCode) {
		this.accOrgCode = accOrgCode;
	}

	public String getOrgFullCode() {
		return orgFullCode;
	}

	public void setOrgFullCode(String orgFullCode) {
		this.orgFullCode = orgFullCode;
	}

	public String getBaseOrgCode() {
		return baseOrgCode;
	}

	public void setBaseOrgCode(String baseOrgCode) {
		this.baseOrgCode = baseOrgCode;
	}
	public String getQueryParam() {
		return queryParam;
	}
	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}
}
