package cn.uce.portal.sync.vo;

import java.util.Date;

import cn.uce.base.vo.BaseVo;

/**
 * 优速通公告主数据VO
 *<pre>
 * UctNoticeDataVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:08:58
 */
public class UctNoticeDataVo extends BaseVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 父集ID
	 */
	private Long parentId;
	/**
	 * 子集ID
	 */
	private Long sonId;
	/**
	 * 类型ID
	 */
	private Long noticeId;
	/**
	 * 创建人
	 */
	private String createEmp;
	/**
	 * 创建机构
	 */
	private String createOrg;
	/**
	 * 更新人
	 */
	private String updateEmp;
	/**
	 * 更新机构
	 */
	private String updateOrg;
	/**
	 * 是否启用.0/1 默认为0
	 */
	private Integer enabled;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 1:提交 0：未提交
	 */
	private Integer isCommit;
	/**
	 * 公告范围,多个组织或者用户用英文逗号隔开,如果为空,为全网公告
	 */
	private String codeScope;
	/**
	 * 0表示部门，1表示个人
	 */
	private int codeMark;
	/**
	 * 浏览量
	 */
	private int pageView;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 *
	 */
	private String summary;
	/**
	 *  发布范围部门名称，多个部门用逗号隔开
	 */
	private String nameScope;
	/**
	 * 置顶天数
	 */
	private int istopDay;
	/**
	 * 置顶时间
	 */
	private Date istopTime;
	
	/**
	 * 公告来源
	 */
	private String noticeSource;
	
	/**
	 * 是否必读
	 */
	private int mustReadFlag;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getSonId() {
		return sonId;
	}
	public void setSonId(Long sonId) {
		this.sonId = sonId;
	}
	public Long getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
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
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsCommit() {
		return isCommit;
	}
	public void setIsCommit(Integer isCommit) {
		this.isCommit = isCommit;
	}
	public String getCodeScope() {
		return codeScope;
	}
	public void setCodeScope(String codeScope) {
		this.codeScope = codeScope;
	}
	public int getCodeMark() {
		return codeMark;
	}
	public void setCodeMark(int codeMark) {
		this.codeMark = codeMark;
	}
	public int getPageView() {
		return pageView;
	}
	public void setPageView(int pageView) {
		this.pageView = pageView;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getNameScope() {
		return nameScope;
	}
	public void setNameScope(String nameScope) {
		this.nameScope = nameScope;
	}
	public int getIstopDay() {
		return istopDay;
	}
	public void setIstopDay(int istopDay) {
		this.istopDay = istopDay;
	}
	public Date getIstopTime() {
		return istopTime;
	}
	public void setIstopTime(Date istopTime) {
		this.istopTime = istopTime;
	}
	public String getNoticeSource() {
		return noticeSource;
	}
	public void setNoticeSource(String noticeSource) {
		this.noticeSource = noticeSource;
	}
	public int getMustReadFlag() {
		return mustReadFlag;
	}
	public void setMustReadFlag(int mustReadFlag) {
		this.mustReadFlag = mustReadFlag;
	}

}
