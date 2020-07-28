package cn.uce.portal.sync.entity;

import java.util.Date;

import cn.uce.base.vo.BaseVo;
import cn.uce.core.db.annotion.Table;

/**
 * 接收优速通文件路径及文件相关的数据表实体类
 *<pre>
 * UctNoticeFiles
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:20:42
 */
@Table("omg_portal_uct_notice_files")
public class UctNoticeFiles extends BaseVo {

	/**
	 * serialVersionUID
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
	 * 路径id
	 */
	private String fileId;
	/**
	 * 文件的完整路径
	 */
	private String filePath;
	/**
	 * 
	 */
	private String fileName;
	/**
	 * 删除标识，1是未删除，0是已删除
	 */
	private Integer deleteFlag;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 文件类别1为附件，2为图片
	 */
	private int fileType;
	/**
	 * 略缩图
	 */
	private String smallPath;
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
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public String getSmallPath() {
		return smallPath;
	}
	public void setSmallPath(String smallPath) {
		this.smallPath = smallPath;
	}
	
}
