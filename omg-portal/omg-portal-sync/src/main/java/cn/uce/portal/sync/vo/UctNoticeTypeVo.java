package cn.uce.portal.sync.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

/**
 * 接收优速通公告类型表VO
 *<pre>
 * UctNoticeTypeVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:26:01
 */
public class UctNoticeTypeVo extends BaseVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 类型ID
	 */
	private Long typeId;
	/**
	 * 父集ID
	 */
	private Long parentId;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 创建人.
	 */
	private String createEmp;
	/**
	 * 创建机构.
	 */
	private String createOrg;
	/**
	 * 更新人.
	 */
	private String updateEmp;
	/**
	 * 更新机构.
	 */
	private String updateOrg;
	/**
	 * 更新时间.
	 */
	private Date updateTime;
	/**
	 * 是否启用.0/1 默认为0
	 */
	private Integer enabled;
	/**
	 * 2是发文部门 1是公告类别
	 */
	private int searchType;
	/**
	 * 部门code
	 */
	private String orgCode;
	/**
	 * 部门id
	 */
	private Long orderId;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getUpdateEmp() {
		return updateEmp;
	}
	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}
	public String getUpdateOrg() {
		return updateOrg;
	}
	public void setUpdateOrg(String updateOrg) {
		this.updateOrg = updateOrg;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public int getSearchType() {
		return searchType;
	}
	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
