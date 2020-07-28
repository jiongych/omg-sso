/*package cn.uce.omg.portal.biz.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.omg.portal.biz.IBillQueryBiz;

import com.uc56.core.server.csc.service.ITraceService;

*//**
 * 首页订单运单查询
 *<pre>
 * BillQueryBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年9月6日下午12:29:58
 *//*
@Service("billQueryBiz")
public class BillQueryBiz implements IBillQueryBiz {

	@Resource
	private ITraceService billQueryService;
	
	*//**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 调取乾坤dubbo接口
	 * </pre>
	 * @param billCodeList
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年9月6日下午12:30:14
	 *//*
	@Override
	public String queryQkBill(List<String> billCodeList) {
		
		try {
			return billQueryService.queryTraceForProtal(billCodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
*/