package cn.uce.portal.sync.vo;

import java.io.Serializable;

/**
 * 判断MQ接收到的数据类别
 *<pre>
 * UctNoticeFlagVo
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午5:15:47
 */
public class UctNoticeFlagVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 同步数据操作表（1001DATA表 1002TYPE表 1003FILE表）
	 */
	private Integer tableCode;
	
	/**
	 * 同步数据操作类型(1增，2删，3改)
	 */
	private Integer dealType;

	public Integer getTableCode() {
		return tableCode;
	}

	public void setTableCode(Integer tableCode) {
		this.tableCode = tableCode;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	
}
