package cn.uce.omg.portal.vo;
import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class VirtualOrgRefCmsVo extends BaseVo {

    private static final long serialVersionUID = 1L;

    private String virtualBaseOrgCode;
    private String cmsBaseOrgCode;
    private String remark;
    private Integer status;
    private Date effectTime;
    private Date expireTime;
    private String createEmp;
    private String createOrg;
    private String updateEmp;
    private String updateOrg;
    private Boolean deleteFlag;

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

    public String getVirtualBaseOrgCode() {
		return virtualBaseOrgCode;
	}

	public void setVirtualBaseOrgCode(String virtualBaseOrgCode) {
		this.virtualBaseOrgCode = virtualBaseOrgCode;
	}

	public String getCmsBaseOrgCode() {
		return cmsBaseOrgCode;
	}

	public void setCmsBaseOrgCode(String cmsBaseOrgCode) {
		this.cmsBaseOrgCode = cmsBaseOrgCode;
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

}
