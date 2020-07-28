package cn.uce.omg.portal.biz;

import java.util.List;

/**
 * portal首页运单查询
 *<pre>
 * IBillQueryBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年9月6日下午12:29:16
 */
public interface IBillQueryBiz {
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 运单轨迹查询
	 * </pre>
	 * @param billCodeList
	 * @return
	 * @return String
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年9月6日下午12:29:32
	 */
	String queryQkBill(List<String> billCodeList);
}
