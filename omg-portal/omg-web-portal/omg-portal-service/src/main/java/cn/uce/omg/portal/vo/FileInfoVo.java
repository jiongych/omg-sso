package cn.uce.omg.portal.vo;

import java.io.Serializable;
import java.util.Date;
//文件信息
public class FileInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	//类型code
	private String typeCode;
	
	//类型name
	private String typeName;
	
	//文件名称
	private String fileName;
	//文件名称前缀
	private String fileNameBefore;
	//是否收藏
	private int collectFlag;
	//是否收藏
	private int viewFlag;
	//创建时间
	private Date createTime;
	//生效时间
	private Date effectTime;
	//阅读量
	private int viewCount;
	//链接地址
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNameBefore() {
		return fileNameBefore;
	}

	public void setFileNameBefore(String fileNameBefore) {
		this.fileNameBefore = fileNameBefore;
	}

	public int getCollectFlag() {
		return collectFlag;
	}

	public void setCollectFlag(int collectFlag) {
		this.collectFlag = collectFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}
	
}
