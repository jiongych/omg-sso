/** 
 * @项目名称: FSP
 * @文件名称: SysTypeVo extends BaseVo 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * SysType
 * @Description: SysType
 * @author automatic 
 * @date 2018年9月5日 
 * @version 1.0 
 */
public class SysType implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/** 流水号. */
	private Integer id;
	/** 类型分类. */
	private String typeClassCode;
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
	/** 是否删除. */
	private Boolean deleteFlag;
	/** 备注. */
	private String remark;
	/** 更新人 */
	private String createEmp;
	/** 更新机构. */
	private Integer createOrg;
	/** 更新时间 */
	private Date createTime;
	/** 更新人 */
	private String updateEmp;
	/** 更新机构. */
	private Integer updateOrg;
	/** 更新时间 */
	private Date updateTime;
	/** 系统版本VERSION */
	private Integer version;
	/** 开始更新时间 */
	private Date beginUpdateTime;
	/** 结束更新时间 */
	private Date endUpdateTime;
    /** 数据类型.2.自定义,1.公有 */
    private Integer sourceType;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @return the typeClassCode
	 */
	public String getTypeClassCode() {
		return typeClassCode;
	}
	/**
	 * @return the typeId
	 */
	public Integer getTypeId() {
		return typeId;
	}
	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @return the fldS1
	 */
	public String getFldS1() {
		return fldS1;
	}
	/**
	 * @return the fldS2
	 */
	public String getFldS2() {
		return fldS2;
	}
	/**
	 * @return the fldN1
	 */
	public Double getFldN1() {
		return fldN1;
	}
	/**
	 * @return the fldN2
	 */
	public Double getFldN2() {
		return fldN2;
	}
	/**
	 * @return the deleteFlag
	 */
	public Boolean getDeleteFlag() {
		return deleteFlag;
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
	 * @return the createOrg
	 */
	public Integer getCreateOrg() {
		return createOrg;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
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
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}
	/**
	 * @return the beginUpdateTime
	 */
	public Date getBeginUpdateTime() {
		return beginUpdateTime;
	}
	/**
	 * @return the endUpdateTime
	 */
	public Date getEndUpdateTime() {
		return endUpdateTime;
	}
	/**
	 * @return the sourceType
	 */
	public Integer getSourceType() {
		return sourceType;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @param typeClassCode the typeClassCode to set
	 */
	public void setTypeClassCode(String typeClassCode) {
		this.typeClassCode = typeClassCode;
	}
	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	/**
	 * @param fldS1 the fldS1 to set
	 */
	public void setFldS1(String fldS1) {
		this.fldS1 = fldS1;
	}
	/**
	 * @param fldS2 the fldS2 to set
	 */
	public void setFldS2(String fldS2) {
		this.fldS2 = fldS2;
	}
	/**
	 * @param fldN1 the fldN1 to set
	 */
	public void setFldN1(Double fldN1) {
		this.fldN1 = fldN1;
	}
	/**
	 * @param fldN2 the fldN2 to set
	 */
	public void setFldN2(Double fldN2) {
		this.fldN2 = fldN2;
	}
	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
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
	 * @param createOrg the createOrg to set
	 */
	public void setCreateOrg(Integer createOrg) {
		this.createOrg = createOrg;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}
	/**
	 * @param beginUpdateTime the beginUpdateTime to set
	 */
	public void setBeginUpdateTime(Date beginUpdateTime) {
		this.beginUpdateTime = beginUpdateTime;
	}
	/**
	 * @param endUpdateTime the endUpdateTime to set
	 */
	public void setEndUpdateTime(Date endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}
	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
}
