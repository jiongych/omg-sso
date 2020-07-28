package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

/***
 * 区域
 * @author zhangfenghuang 
 * @since 2017-03-21
 */
@Table("omg_area")
public class PortalArea extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /** 地区编号. */
    private String areaCode;

    /** 地区名称(简体). */
    private String areaName;

    /** 地区名称(繁体). */
    private String areaNameTw;

    /** 地区名称(英文). */
    private String areaNameEn;

    /** 地区类型.1国家，2省份，3城市，4县/区 */
    private Integer areaType;

    /** 所属地区. */
    private Long parentArea;

    /** 邮政编号. */
    private String zipCode;

    /** 电话区号. */
    private String telCode;

    /** 是否启用. */
    private Boolean enabled;

    /** 地区名称全拼. */
    private String namePy;

    /** 地区名称拼音缩写. */
    private String nameSpy;

    /** 备注. */
    private String remark;

    /** 删除标识.1表示已删除，默认为0 */
    private Boolean deleteFlag;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private Integer createOrg;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private Integer updateOrg;
    
    /**父级区域编码*/
    private String parentAreaCode;
    
    /** 所属财务中心. */
    private Integer financeCenter;
    
    /** 所属财务中心基准编码. */
    private String financeCenterCode;
    
    
    public String getParentAreaCode() {
		return parentAreaCode;
	}

	public void setParentAreaCode(String parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}

	public Integer getFinanceCenter() {
		return financeCenter;
	}

	public void setFinanceCenter(Integer financeCenter) {
		this.financeCenter = financeCenter;
	}

	public String getFinanceCenterCode() {
		return financeCenterCode;
	}

	public void setFinanceCenterCode(String financeCenterCode) {
		this.financeCenterCode = financeCenterCode;
	}

	/** 取得 地区编号. */
    public String getAreaCode() {
        return this.areaCode;
    }

    /** 设置 地区编号. */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    /** 取得 地区名称(简体). */
    public String getAreaName() {
        return this.areaName;
    }

    /** 设置 地区名称(简体). */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    /** 取得 地区名称(繁体). */
    public String getAreaNameTw() {
        return this.areaNameTw;
    }

    /** 设置 地区名称(繁体). */
    public void setAreaNameTw(String areaNameTw) {
        this.areaNameTw = areaNameTw;
    }
    /** 取得 地区名称(英文). */
    public String getAreaNameEn() {
        return this.areaNameEn;
    }

    /** 设置 地区名称(英文). */
    public void setAreaNameEn(String areaNameEn) {
        this.areaNameEn = areaNameEn;
    }
    /** 取得 地区类型.1国家，2省份，3城市，4县/区 */
    public Integer getAreaType() {
        return this.areaType;
    }

    /** 设置 地区类型.1国家，2省份，3城市，4县/区 */
    public void setAreaType(Integer areaType) {
        this.areaType = areaType;
    }
    /** 取得 所属地区. */
    public Long getParentArea() {
        return this.parentArea;
    }

    /** 设置 所属地区. */
    public void setParentArea(Long parentArea) {
        this.parentArea = parentArea;
    }
    /** 取得 邮政编号. */
    public String getZipCode() {
        return this.zipCode;
    }

    /** 设置 邮政编号. */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    /** 取得 电话区号. */
    public String getTelCode() {
        return this.telCode;
    }

    /** 设置 电话区号. */
    public void setTelCode(String telCode) {
        this.telCode = telCode;
    }
    /** 取得 是否启用. */
    public Boolean getEnabled() {
        return this.enabled;
    }

    /** 设置 是否启用. */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    /** 取得 地区名称全拼. */
    public String getNamePy() {
        return this.namePy;
    }

    /** 设置 地区名称全拼. */
    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }
    /** 取得 地区名称拼音缩写. */
    public String getNameSpy() {
        return this.nameSpy;
    }

    /** 设置 地区名称拼音缩写. */
    public void setNameSpy(String nameSpy) {
        this.nameSpy = nameSpy;
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
