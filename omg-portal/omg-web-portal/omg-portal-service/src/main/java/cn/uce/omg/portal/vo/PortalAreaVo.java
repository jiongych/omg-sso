package cn.uce.omg.portal.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;


/***
 * 区域
 * @author zhangfenghuang 
 * @since 2017-03-21
 */
public class PortalAreaVo extends BaseVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /** 地区ID. */
    private Long id;

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

    /** 所属财务中心. */
    private Integer financeCenter;

    /** 报价区域.只用于结算显示用. */
    private String priceRegion;

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

    /** 版本. */
    private Integer version;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;

    /** 创建时间. */
    private Date createTime;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;

    /** 更新时间. */
    private Date updateTime;

    private String parentAreaCode;
    
    public String getParentAreaCode() {
		return parentAreaCode;
	}

	public void setParentAreaCode(String parentAreaCode) {
		this.parentAreaCode = parentAreaCode;
	}

	/** 取得 地区ID. */
    public Long getId() {
        return this.id;
    }

    /** 设置 地区ID. */
    public void setId(Long id) {
        this.id = id;
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
    /** 取得 所属财务中心. */
    public Integer getFinanceCenter() {
        return this.financeCenter;
    }

    /** 设置 所属财务中心. */
    public void setFinanceCenter(Integer financeCenter) {
        this.financeCenter = financeCenter;
    }
    /** 取得 报价区域.只用于结算显示用. */
    public String getPriceRegion() {
        return this.priceRegion;
    }

    /** 设置 报价区域.只用于结算显示用. */
    public void setPriceRegion(String priceRegion) {
        this.priceRegion = priceRegion;
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
    /** 取得 版本. */
    public Integer getVersion() {
        return this.version;
    }

    /** 设置 版本. */
    public void setVersion(Integer version) {
        this.version = version;
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
    /** 取得 创建时间. */
    public Date getCreateTime() {
        return this.createTime;
    }

    /** 设置 创建时间. */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
    /** 取得 更新时间. */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /** 设置 更新时间. */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



}
