package cn.uce.omg.portal.vo;


import cn.uce.base.vo.BaseVo;

/**
 * 
 * @Description:(DictDataTreeVo) 
 * @author XJ
 * @date 2017年4月22日 上午8:24:49
 */
public class PortalDictDataTreeVo extends BaseVo {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String text;
	private String typeClassName;
	private Integer sourceType;
	private String remark;
	private Boolean visible;
	private String systemCode;
	
	public String getTypeClassName() {
		return typeClassName;
	}
	public void setTypeClassName(String typeClassName) {
		this.typeClassName = typeClassName;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

}
