package cn.uce.omg.portal.entity;

import java.util.Date;

import cn.uce.base.entity.BaseEntity;
import cn.uce.core.db.annotion.Table;

/**
 * 导出文件设置实体类
 *<pre>
 * ExportSet
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月19日下午3:58:09
 */
@Table("fsp_export_set")
public class PortalExportSet extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String setKey;
	
	private String value;

    /** 更新人. */
    private String updateEmp;

    /** 更新时间. */
    private Date updateTime;

    /** 更新机构. */
    private String updateOrg;

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;

    /** 创建时间. */
    private Date createTime;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSetKey() {
		return setKey;
	}

	public void setSetKey(String setKey) {
		this.setKey = setKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateOrg() {
		return updateOrg;
	}

	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public String getCreateOrg() {
		return createOrg;
	}

	public void setCreateOrg(String createOrg) {
		this.createOrg = createOrg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
