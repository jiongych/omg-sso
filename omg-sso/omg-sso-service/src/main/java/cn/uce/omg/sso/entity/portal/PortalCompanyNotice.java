package cn.uce.omg.sso.entity.portal;

import cn.uce.base.entity.BaseEntity;

/**
 * 公司公告实体类
 *<pre>
 * PortalCompanyNotice
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月28日下午2:09:40
 */
public class PortalCompanyNotice extends BaseEntity {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
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

    /** 创建人. */
    private String createEmp;

    /** 创建机构. */
    private String createOrg;

    /** 更新人. */
    private String updateEmp;

    /** 更新机构. */
    private String updateOrg;

	/**
	 * 置顶标志
	 */
	private Boolean setTop;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
    
}
