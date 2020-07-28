package cn.uce.omg.portal.vo;

import java.util.Date;
import java.util.Map;

import cn.uce.base.vo.BaseVo;

/**
 * 导出文件设置VO
 *<pre>
 * ExportSetVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月19日下午3:58:30
 */
public class PortalExportSetVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String setKey;
	
	private String key;
	
	private Map<String, String> setValue;
	
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSetKey() {
		return setKey;
	}

	public void setSetKey(String setKey) {
		this.setKey = setKey;
	}

	public Map<String, String> getSetValue() {
		return setValue;
	}

	public void setSetValue(Map<String, String> setValue) {
		this.setValue = setValue;
	}
	
}
