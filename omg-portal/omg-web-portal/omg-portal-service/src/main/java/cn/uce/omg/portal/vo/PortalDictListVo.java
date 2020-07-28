package cn.uce.omg.portal.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 数据字典类型数据列表Vo
 * @author zd
 * @version V1.0 2017-6-21
 */
public class PortalDictListVo  implements Serializable {

	private static final long serialVersionUID = -1855791586450507520L;
	//数据字典类型
	private String dictType;
	
	//数据字典list
	private List<PortalDictDataVo> dictDataList;
	
	public PortalDictListVo() { }
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public List<PortalDictDataVo> getDictDataList() {
		return dictDataList;
	}

	public void setDictDataList(List<PortalDictDataVo> dictDataList) {
		this.dictDataList = dictDataList;
	}

}
