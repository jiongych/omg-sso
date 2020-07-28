package cn.uce.omg.portal.entity;

import java.util.Date;

import cn.uce.base.vo.BaseVo;
import cn.uce.core.db.annotion.Table;

/**
 * Portal首页点击菜单搜集
 *<pre>
 * PortalCollectPermissionVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月10日上午11:25:55
 */
@Table("omg_portal_collect_click_permission")
public class PortalCollectPermission extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	/**
	 * 员工工号
	 */
	private String empCode;
	
	/**
	 * 员工姓名
	 */
	private String empName;
	
	/**
	 * 菜单CODE
	 */
	private String permissionCode;
	/**
	 * 菜单名称
	 */
	private String permissionName;
	
	/**
	 * 菜单ID
	 */
	private Long permissionId;
	
	/**
	 * 数据来源:1.左侧菜单 2.常用菜单 3....
	 */
	private Integer source;
	
	/**
	 * 所属系统
	 */
	private String systemCode;
	/**
	 * 跳转地址
	 */
	private String url;
	/**
	 * 创建人员
	 */
	private String createEmp;
	/**
	 * 创建机构
	 */
	private String createOrg;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public Long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
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
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getPermissionCode() {
		return permissionCode;
	}
	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
