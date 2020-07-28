package cn.uce.omg.portal.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalEmp;
import cn.uce.omg.portal.vo.EmpVo;

/**
 * 
 *<pre>
 * IEmpDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年3月29日上午9:42:11
 */
@Repository("empDao")
public interface IEmpDao extends IBaseDao<PortalEmp, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据工号查询
	 * </pre>
	 * @param empCode
	 * @return
	 * @return EmpVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年3月29日上午9:42:16
	 */
	EmpVo findByEmpCode(String empCode);
}
