package cn.uce.omg.portal.vo;

import java.util.List;

import cn.uce.base.vo.BaseVo;

public class OrgVo extends BaseVo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    /** 机构ID. */
    private String orgId;
    //乾坤机构ID
    private String cmsOrgId;
	/** 机构编号.机构编号唯一 */
    private String orgCode;
    //机构基准编码
    private String baseOrgCode;
    /** 机构名称.同一机构下,机构名称唯一 */
    private String orgName;
    private String orgFullName;
    /** 机构类型.总部-10,财务中心-20,操作中心-21,网点-30,承包区-40 */
    private Integer orgType;
	/** 上一级机构ID */
    private String parentOrg;
    //机构标识
    private String orgFlag;
    //机构类型列表
    private List<String> orgTypeList;
    //机构状态列表
    private List<String> orgStatusList;
    //机构属性
    private List<String> orgPropertyList;
    //是否包含自己
    private Boolean iself;
    
    private String empCode;
    
    private String status;
    
    private String orgProperty;
    
	public String getCmsOrgId() {
		return cmsOrgId;
	}

	public void setCmsOrgId(String cmsOrgId) {
		this.cmsOrgId = cmsOrgId;
	}

	public String getBaseOrgCode() {
		return baseOrgCode;
	}

	public void setBaseOrgCode(String baseOrgCode) {
		this.baseOrgCode = baseOrgCode;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public Boolean getIself() {
		return iself;
	}

	public void setIself(Boolean iself) {
		this.iself = iself;
	}

	public List<String> getOrgStatusList() {
		return orgStatusList;
	}

	public void setOrgStatusList(List<String> orgStatusList) {
		this.orgStatusList = orgStatusList;
	}

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public List<String> getOrgTypeList() {
		return orgTypeList;
	}

	public void setOrgTypeList(List<String> orgTypeList) {
		this.orgTypeList = orgTypeList;
	}

	public List<String> getOrgPropertyList() {
		return orgPropertyList;
	}

	public void setOrgPropertyList(List<String> orgPropertyList) {
		this.orgPropertyList = orgPropertyList;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
    public String getOrgFullName() {
		return orgFullName;
	}

	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	public void setParentOrg(String parentOrg) {
		this.parentOrg = parentOrg;
	}

	public String getParentOrg() {
		return parentOrg;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
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
    /** 取得 机构名称.同一机构下,机构名称唯一 */
    public String getOrgName() {
        return this.orgName;
    }

    /** 设置 机构名称.同一机构下,机构名称唯一 */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgProperty() {
		return orgProperty;
	}

	public void setOrgProperty(String orgProperty) {
		this.orgProperty = orgProperty;
	}
}
