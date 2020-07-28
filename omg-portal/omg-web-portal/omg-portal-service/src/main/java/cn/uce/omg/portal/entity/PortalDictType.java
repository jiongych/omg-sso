package cn.uce.omg.portal.entity;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Id;
import cn.uce.core.db.annotion.Table;
import cn.uce.core.db.annotion.Transient;

@Table("omg_portal_dict_type")
public class PortalDictType  extends BaseEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    @Transient
    private Long id;

    /** 类型分类ID. */
    @Id
    private Integer typeClassId;

    /** 类型分类. */
    private String typeClassCode;

    /** 类型名称(简体). */
    private String typeClassName;

    /** 是否显示.是否在系统类型维护界面显示 */
    private Boolean visible;

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


    /** 数据来源.1.omg同步,2.自定义 */
    private Integer sourceType;

    /** 所属系统. */
    private String systemCode;

	/** 取得 类型分类ID. */
    public Integer getTypeClassId() {
        return this.typeClassId;
    }

    /** 设置 类型分类ID. */
    public void setTypeClassId(Integer typeClassId) {
        this.typeClassId = typeClassId;
    }
    /** 取得 类型分类. */
    public String getTypeClassCode() {
        return this.typeClassCode;
    }

    /** 设置 类型分类. */
    public void setTypeClassCode(String typeClassCode) {
        this.typeClassCode = typeClassCode;
    }
    /** 取得 类型名称(简体). */
    public String getTypeClassName() {
        return this.typeClassName;
    }

    /** 设置 类型名称(简体). */
    public void setTypeClassName(String typeClassName) {
        this.typeClassName = typeClassName;
    }
    /** 取得 是否显示.是否在系统类型维护界面显示 */
    public Boolean getVisible() {
        return this.visible;
    }

    /** 设置 是否显示.是否在系统类型维护界面显示 */
    public void setVisible(Boolean visible) {
        this.visible = visible;
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

}
