package cn.uce.omg.sso.vo.portal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.uce.base.vo.BaseVo;

/**
 * 公司公告VO
 *<pre>
 * PortalCompanyNoticeVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月28日下午2:14:52
 */
public class PortalCompanyNoticeVo extends BaseVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id.
	 */
	private Long id;
	
	/**
	 * 公告标题.
	 */
	private String title;

	/**
	 * 公告内容.
	 */
	private String content;
	
	/**
	 * 公告作者
	 */
	private String author;
	
	/**
	 * 公告附件.
	 */
	private String attachment;
	
	/** 
	 * 备注. 
	 */
    private String remark;
    
    /** 删除标识.1表示已删除，默认为0 */
    private Boolean deleteFlag;
    
    /** 阅读标识，1表示己阅读，默认为0*/
    private Boolean viewFlag = false;
    
    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;
    
    /**
     * 创建机构名称
     */
    private String createOrgName;
    
    /** 创建时间. */
    private Date createTime;
    
    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;
    
    /** 更新时间. */
    private Date updateTime;

    /** 是否是三天内的*/
    private Boolean newNotice;
    
	/**
	 * 置顶标志
	 */
	private Boolean setTop;
	
	/**
	 * 置顶标志
	 */
	private Boolean setAlert;
	
	/**
	 * 查看人数
	 */
	private int viewCount;
	
	/**
	 * 类型ID
	 */
	private Long parentId;
	
	private String typeName;
	
	private String oneName;
	
	private String twoName;
	
	private List<Map<String,Object>> noticeFiles;
	/**
	 * @return the setAlert
	 */
	public Boolean getSetAlert() {
		return setAlert;
	}

	/**
	 * @param setAlert the setAlert to set
	 */
	public void setSetAlert(Boolean setAlert) {
		this.setAlert = setAlert;
	}

	/**
	 * 排序
	 */
	private Integer sort;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Boolean getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(Boolean viewFlag) {
		this.viewFlag = viewFlag;
	}

	public Boolean getNewNotice() {
		return newNotice;
	}

	public void setNewNotice(Boolean newNotice) {
		this.newNotice = newNotice;
	}

	public Boolean getSetTop() {
		return setTop;
	}

	public void setSetTop(Boolean setTop) {
		this.setTop = setTop;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Map<String, Object>> getNoticeFiles() {
		return noticeFiles;
	}

	public void setNoticeFiles(List<Map<String, Object>> noticeFiles) {
		this.noticeFiles = noticeFiles;
	}

	public String getOneName() {
		return oneName;
	}

	public void setOneName(String oneName) {
		this.oneName = oneName;
	}

	public String getTwoName() {
		return twoName;
	}

	public void setTwoName(String twoName) {
		this.twoName = twoName;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
    
}
