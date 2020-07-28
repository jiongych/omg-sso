package cn.uce.omg.sso.vo;

import java.util.List;

import cn.uce.base.vo.BaseVo;

/**
 * 一级公告类型信息
 *<pre>
 * NoticeParentInfoVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月19日下午2:56:36
 */
public class NoticeParentInfoVo extends BaseVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 类型名称
	 */
	private String typeName;

	/**
	 * 父集ID
	 */
	private Long parentId;

	/**
	 * 子级信息
	 */
	private List<NoticeTwoLeveInfoVo> towTwoLeveInfoList;
	
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<NoticeTwoLeveInfoVo> getTowTwoLeveInfoList() {
		return towTwoLeveInfoList;
	}

	public void setTowTwoLeveInfoList(List<NoticeTwoLeveInfoVo> towTwoLeveInfoList) {
		this.towTwoLeveInfoList = towTwoLeveInfoList;
	}
	
}
