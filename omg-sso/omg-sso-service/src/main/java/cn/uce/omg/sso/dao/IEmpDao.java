/** 
 * @项目名称: FSP
 * @文件名称: IEmpDao extends IBaseDao<Emp, Long> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.Emp;
import cn.uce.omg.sso.vo.EmpOrgInfoVo;
import cn.uce.omg.vo.EmpVo;

/**
 * IEmpDao extends IBaseDao<Emp, Long>  
 * @Description: IEmpDao extends IBaseDao<Emp, Long>  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IEmpDao extends IBaseDao<Emp, Long> {
	

	/**
	 * 根据条件查询单个员工
	 * @param empVo
	 * @return
	 */
	public EmpVo findOneEmpByCondition(EmpVo empVo);
	
	/**
	 * Description: 根据员工手机号查询员工信息
	 * @param mobile
	 * @return
	 * @author zhangRenbing
	 * @date 2018年9月5日
	 */
	public EmpVo findEmpByMobile(String mobile);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据员工编号查询员工姓名、所属机构、所属机构名称
	 * </pre>
	 * @param empCode
	 * @return
	 * @return EmpVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年7月18日下午6:09:58
	 */
	public EmpOrgInfoVo findEmpAndOrgByEmpCode(String empCode);
}
