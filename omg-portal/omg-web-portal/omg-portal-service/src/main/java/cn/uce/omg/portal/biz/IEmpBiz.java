package cn.uce.omg.portal.biz;

import cn.uce.omg.portal.vo.EmpVo;

/**
 * 人员查询
 *<pre>
 * IEmpBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年3月29日上午9:34:07
 */
public interface IEmpBiz {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过工号查询人员
	 * </pre>
	 * @param empCode
	 * @return
	 * @return EmpVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月29日上午9:33:57
	 */
	EmpVo findByEmpCode(String empCode);
}
