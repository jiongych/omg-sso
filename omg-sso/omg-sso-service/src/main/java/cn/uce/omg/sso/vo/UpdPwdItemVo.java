package cn.uce.omg.sso.vo;

import cn.uce.omg.sso.entity.UpdPwdItem;

/**
 * 密码历史信息vo
 * @author LH
 * @date 2019年3月1日 下午2:47:00
 */
public class UpdPwdItemVo extends UpdPwdItem {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 密码校验历史数
	 */
	private int historyTotal;

	public int getHistoryTotal() {
		return historyTotal;
	}

	public void setHistoryTotal(int historyTotal) {
		this.historyTotal = historyTotal;
	}
	
	
}
