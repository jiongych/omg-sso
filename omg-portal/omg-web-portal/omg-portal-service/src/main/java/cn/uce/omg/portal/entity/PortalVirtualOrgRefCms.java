package cn.uce.omg.portal.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;

public class PortalVirtualOrgRefCms extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** 虚拟机构ID. */
    private Integer virtualOrgId;

    /** 虚拟机构编号. */
    private String virtualOrgCode;

    /** 乾坤机构ID. */
    private Integer cmsOrgId;

    /** 备注. */
    private String remark;

    /** 状态.正常、失效 */
    private Integer status;

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
    /** 删除标识.1表示已删除，默认为0 */
    private Boolean deleteFlag;

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/** 取得 虚拟机构ID. */
    public Integer getVirtualOrgId() {
        return this.virtualOrgId;
    }

    /** 设置 虚拟机构ID. */
    public void setVirtualOrgId(Integer virtualOrgId) {
        this.virtualOrgId = virtualOrgId;
    }
    /** 取得 虚拟机构编号. */
    public String getVirtualOrgCode() {
        return this.virtualOrgCode;
    }

    /** 设置 虚拟机构编号. */
    public void setVirtualOrgCode(String virtualOrgCode) {
        this.virtualOrgCode = virtualOrgCode;
    }
    /** 取得 乾坤机构ID. */
    public Integer getCmsOrgId() {
        return this.cmsOrgId;
    }

    /** 设置 乾坤机构ID. */
    public void setCmsOrgId(Integer cmsOrgId) {
        this.cmsOrgId = cmsOrgId;
    }
    /** 取得 备注. */
    public String getRemark() {
        return this.remark;
    }

    /** 设置 备注. */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /** 取得 状态.正常、失效 */
    public Integer getStatus() {
        return this.status;
    }

    /** 设置 状态.正常、失效 */
    public void setStatus(Integer status) {
        this.status = status;
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

}
