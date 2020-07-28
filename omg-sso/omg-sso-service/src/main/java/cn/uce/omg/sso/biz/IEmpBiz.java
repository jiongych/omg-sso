/** 
 * @项目名称: FSP
 * @文件名称: IEmpBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.omg.sso.vo.EmpOrgInfoVo;
import cn.uce.omg.vo.EmpVo;
import cn.uce.omg.vo.OtherEmpRelationVo;

/**
 * 
 * @author laizd
 * @since 2016-11-24 
 */
public interface IEmpBiz {
	
	/**
	 * 根据员工id查找员工信息
	 * @param empId
	 * @return
	 */
	public EmpVo findEmpByEmpId(Integer empId);
	
	/**
	 * 根据员工编号查询员工信息
	 * @param empCode
	 * @return
	 */
	public EmpVo findEmpByEmpCode(String empCode);
	
	/**
	 * 根据条件查询绑定关系
	 * @param otherEmpRelationVo
	 * @return
	 */
	public List<OtherEmpRelationVo> findQkEmpRelationByEmpId(Integer empId);
	
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
	 * @date 2019年7月18日下午6:07:31
	 */
	public EmpOrgInfoVo findEmpAndOrgByEmpCode(String empCode);
}