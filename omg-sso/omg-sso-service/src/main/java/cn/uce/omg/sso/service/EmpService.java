/** 
 * @项目名称: FSP
 * @文件名称: EmpService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import cn.uce.omg.sso.dao.IEmpDao;
import cn.uce.omg.sso.vo.EmpOrgInfoVo;
import cn.uce.omg.vo.EmpVo;

/**
 * EmpService  
 * @Description: EmpService  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class EmpService {
	private IEmpDao empDao;

	/**
	 * 根据员工id查找员工信息
	 * @param empId
	 * @return
	 */
	public EmpVo findEmpByEmpId(Integer empId){
		EmpVo searchEmpVo = new EmpVo();
		searchEmpVo.setEmpId(empId);
		return this.empDao.findOneEmpByCondition(searchEmpVo);
	}
	
	/**
	 * 根据员工编号查询员工信息
	 * @param empCode
	 * @return
	 */
	public EmpVo findEmpByEmpCode(String empCode){
		EmpVo searchEmpVo = new EmpVo();
		searchEmpVo.setEmpCode(empCode);
		return this.empDao.findOneEmpByCondition(searchEmpVo);
	}
	
	/**
	 * Description: 根据员工手机号查询员工信息
	 * @param mobile
	 * @return
	 * @author zhangRenbing
	 * @date 2018年9月5日
	 */
	public EmpVo findEmpByMobile(String mobile) {
		return empDao.findEmpByMobile(mobile);
	}

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

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
	 * @date 2019年7月18日下午6:12:14
	 */
	public EmpOrgInfoVo findEmpAndOrgByEmpCode(String empCode) {
		return empDao.findEmpAndOrgByEmpCode(empCode);
	}

}
