package cn.uce.portal.sync.vo;

import java.util.Date;
import java.util.List;

import cn.uce.base.vo.BaseVo;

/**
 * 接收优速通必读权限范围表VO
 *<pre>
 * UctNoticeMustReadScopeVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年8月30日下午4:11:59
 */
public class UctNoticeMustReadScopeVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	private Long id;
	/**
	 * 公告id
	 */
	private Long noticeId;
	
	/**
	 * 员工code或者是机构id
	 */
	private String code;

	/**
	 * 员工姓名或者是机构姓名
	 */
	private String name;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 员工职级
	 */
	private String positionLevel;

	/**
	 * 范围类型。1：部门；2：员工
	 */
	private int type;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private List<String> mustReadList;
	
	private List<String> mustReadEmpCodes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPositionLevel() {
		return positionLevel;
	}

	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<String> getMustReadEmpCodes() {
		return mustReadEmpCodes;
	}

	public void setMustReadEmpCodes(List<String> mustReadEmpCodes) {
		this.mustReadEmpCodes = mustReadEmpCodes;
	}

	public List<String> getMustReadList() {
		return mustReadList;
	}

	public void setMustReadList(List<String> mustReadList) {
		this.mustReadList = mustReadList;
	}
	
}
