package cn.uce.omg.sso.vo;

import cn.uce.base.vo.BaseVo;

/**
 * 二级公告类型信息
 *<pre>
 * NoticeTwoLeveInfoVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月19日下午2:56:16
 */
public class NoticeTwoLeveInfoVo extends BaseVo {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 类型名称
	 */
	private String twoLeveTypeName;

	/**
	 * 二级ID
	 */
	private Long twoLeveTypeId;

	public String getTwoLeveTypeName() {
		return twoLeveTypeName;
	}

	public void setTwoLeveTypeName(String twoLeveTypeName) {
		this.twoLeveTypeName = twoLeveTypeName;
	}

	public Long getTwoLeveTypeId() {
		return twoLeveTypeId;
	}

	public void setTwoLeveTypeId(Long twoLeveTypeId) {
		this.twoLeveTypeId = twoLeveTypeId;
	}

}
