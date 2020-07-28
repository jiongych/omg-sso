package cn.uce.omg.portal.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

public class PortalDictDataVo extends BaseVo {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    
    /** 类型分类. */
    private String typeClassCode;
    
    //分类名称
    private String typeClassName;

    /** 类型ID.不允许修改 */
    private Integer typeId;

    /** 类型编号. */
    private String typeCode;

    /** 类型名称(简体). */
    private String typeName;

    /** 字符型保留字段1. */
    private String fldS1;

    /** 字符型保留字段2. */
    private String fldS2;

    /** 数值型保留字段1. */
    private Double fldN1;

    /** 数值型保留字段2. */
    private Double fldN2;

    /** 备注. */
    private String remark;

    /** 删除标识.1表示已删除，默认为0 */
    private Boolean deleteFlag;

    /** 创建人. */
    private String createEmp;

    /** 创建时间. */
    private Date createTime;
    
    /** 创建机构. */
    private String createOrg;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;

	/** 更新时间. */
    private Date updateTime;

    /** 数据来源.1.omg同步,2.自定义 */
    private Integer sourceType;
    
    private String systemCode;
    
	public String getTypeClassName() {
		return typeClassName;
	}

	public void setTypeClassName(String typeClassName) {
		this.typeClassName = typeClassName;
	}

	/** 取得 类型分类. */
    public String getTypeClassCode() {
        return this.typeClassCode;
    }

    /** 设置 类型分类. */
    public void setTypeClassCode(String typeClassCode) {
        this.typeClassCode = typeClassCode;
    }
    /** 取得 类型ID.不允许修改 */
    public Integer getTypeId() {
        return this.typeId;
    }

    /** 设置 类型ID.不允许修改 */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
    /** 取得 类型编号. */
    public String getTypeCode() {
        return this.typeCode;
    }

    /** 设置 类型编号. */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    /** 取得 类型名称(简体). */
    public String getTypeName() {
        return this.typeName;
    }

    /** 设置 类型名称(简体). */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    /** 取得 字符型保留字段1. */
    public String getFldS1() {
        return this.fldS1;
    }

    /** 设置 字符型保留字段1. */
    public void setFldS1(String fldS1) {
        this.fldS1 = fldS1;
    }
    /** 取得 字符型保留字段2. */
    public String getFldS2() {
        return this.fldS2;
    }

    /** 设置 字符型保留字段2. */
    public void setFldS2(String fldS2) {
        this.fldS2 = fldS2;
    }
    /** 取得 数值型保留字段1. */
    public Double getFldN1() {
        return this.fldN1;
    }

    /** 设置 数值型保留字段1. */
    public void setFldN1(Double fldN1) {
        this.fldN1 = fldN1;
    }
    /** 取得 数值型保留字段2. */
    public Double getFldN2() {
        return this.fldN2;
    }

    /** 设置 数值型保留字段2. */
    public void setFldN2(Double fldN2) {
        this.fldN2 = fldN2;
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
    
    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
