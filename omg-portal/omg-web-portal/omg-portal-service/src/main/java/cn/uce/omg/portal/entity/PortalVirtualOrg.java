package cn.uce.omg.portal.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;

public class PortalVirtualOrg extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 机构ID. */
    private Integer orgId;
    
    /** 基准编号 */
    private String baseOrgCode;

    /** 机构编号.机构编号唯一,自动生成，V开头 */
    private String orgCode;

    /** 机构名称（简体）. */
    private String orgName;

    /** 机构简称（繁体）. */
    private String orgNameTw;

    /** 机构简称（英文）. */
    private String orgNameEn;

    /** 机构名称全拼. */
    private String namePy;

    /** 机构名称拼音缩写. */
    private String nameSpy;

    /** 机构类型.1000-大区、2000-省区 */
    private Integer orgType;

    /** 所属机构ID. */
    private String parentOrg;

    /** 状态.正常，暂停，停用 */
    private Integer status;
    
    /** 机构id全称 */
    private String orgFullCode;

    /** 备注. */
    private String remark;

    /** 删除标识.1表示已删除，默认为0 */
    private Boolean deleteFlag;

    /** 生效时间.预留字段 */
    private Date effectTime;

    /** 失效时间.预留字段 */
    private Date expireTime;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;
    
    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private Integer updateOrg;

    /** 取得 机构ID. */
    public Integer getOrgId() {
        return this.orgId;
    }

    /** 设置 机构ID. */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    /** 取得 机构编号.机构编号唯一,自动生成，V开头 */
    public String getOrgCode() {
        return this.orgCode;
    }

    /** 设置 机构编号.机构编号唯一,自动生成，V开头 */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    /** 取得 机构名称（简体）. */
    public String getOrgName() {
        return this.orgName;
    }

    /** 设置 机构名称（简体）. */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    /** 取得 机构简称（繁体）. */
    public String getOrgNameTw() {
        return this.orgNameTw;
    }

    /** 设置 机构简称（繁体）. */
    public void setOrgNameTw(String orgNameTw) {
        this.orgNameTw = orgNameTw;
    }
    /** 取得 机构简称（英文）. */
    public String getOrgNameEn() {
        return this.orgNameEn;
    }

    /** 设置 机构简称（英文）. */
    public void setOrgNameEn(String orgNameEn) {
        this.orgNameEn = orgNameEn;
    }
    /** 取得 机构名称全拼. */
    public String getNamePy() {
        return this.namePy;
    }

    /** 设置 机构名称全拼. */
    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }
    /** 取得 机构名称拼音缩写. */
    public String getNameSpy() {
        return this.nameSpy;
    }

    /** 设置 机构名称拼音缩写. */
    public void setNameSpy(String nameSpy) {
        this.nameSpy = nameSpy;
    }
    /** 取得 机构类型.1000-大区、2000-省区 */
    public Integer getOrgType() {
        return this.orgType;
    }

    /** 设置 机构类型.1000-大区、2000-省区 */
    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }
    /** 取得 所属机构ID. */
    public String getParentOrg() {
        return this.parentOrg;
    }

    /** 设置 所属机构ID. */
    public void setParentOrg(String parentOrg) {
        this.parentOrg = parentOrg;
    }
    /** 取得 状态.正常，暂停，停用 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 状态.正常，暂停，停用 */
    public void setStatus(Integer status) {
        this.status = status;
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
    /** 取得 创建人. */
    public String getCreateEmp() {
        return this.createEmp;
    }

    /** 设置 创建人. */
    public void setCreateEmp(String createEmp) {
        this.createEmp = createEmp;
    }
    /** 取得 创建机构. */
    public Integer getCreateOrg() {
        return this.createOrg;
    }

    /** 设置 创建机构. */
    public void setCreateOrg(Integer createOrg) {
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
    public Integer getUpdateOrg() {
        return this.updateOrg;
    }

    /** 设置 更新机构. */
    public void setUpdateOrg(Integer updateOrg) {
        this.updateOrg = updateOrg;
    }

	public String getBaseOrgCode() {
		return baseOrgCode;
	}

	public void setBaseOrgCode(String baseOrgCode) {
		this.baseOrgCode = baseOrgCode;
	}

	public String getOrgFullCode() {
		return orgFullCode;
	}

	public void setOrgFullCode(String orgFullCode) {
		this.orgFullCode = orgFullCode;
	}

}
