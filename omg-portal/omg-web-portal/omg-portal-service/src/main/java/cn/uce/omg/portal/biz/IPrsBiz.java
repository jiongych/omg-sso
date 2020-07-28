package cn.uce.omg.portal.biz;

import java.util.List;

import cn.uce.prs.prepay.vo.PrepayAccountVo;

public interface IPrsBiz {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * PRS账户预付款余额查询
	 * </pre>
	 * @param prepayAccountVos
	 * @return
	 * @return String
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月20日上午8:59:32
	 */
	String searchPrepayAccount(List<PrepayAccountVo> prepayAccountVos);
}
