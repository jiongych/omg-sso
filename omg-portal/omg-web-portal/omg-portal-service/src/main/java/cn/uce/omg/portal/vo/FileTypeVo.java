package cn.uce.omg.portal.vo;

import java.io.Serializable;
import java.util.List;

import cn.uce.uct.app.entity.ConfigureResult;
//文件类型
public class FileTypeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	//文件类型名称
	private String twoTypeName;
	//文件类型code
	private String fileTypeCode;

	private List<ConfigureResult> fileTypeVoList;
	
	public String getTwoTypeName() {
		return twoTypeName;
	}

	public void setTwoTypeName(String twoTypeName) {
		this.twoTypeName = twoTypeName;
	}

	public String getFileTypeCode() {
		return fileTypeCode;
	}

	public void setFileTypeCode(String fileTypeCode) {
		this.fileTypeCode = fileTypeCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ConfigureResult> getFileTypeVoList() {
		return fileTypeVoList;
	}

	public void setFileTypeVoList(List<ConfigureResult> fileTypeVoList) {
		this.fileTypeVoList = fileTypeVoList;
	}

}
