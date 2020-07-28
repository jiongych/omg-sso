/** 
 * @项目名称: FSP
 * @文件名称: IRoleDao extends IBaseDao<Role, Long>
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import java.util.List;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.Role;
import cn.uce.omg.vo.RoleVo;

/**
 * IRoleDao extends IBaseDao<Role, Long> 
 * @Description: IRoleDao extends IBaseDao<Role, Long> 
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IRoleDao extends IBaseDao<Role, Long>{
	
	/**
	 * 根据条件查询角色
	 * @param roleVo
	 * @return
	 */
	public List<RoleVo> findRoleByCondition(RoleVo roleVo);
	
	/**
	 * 根据员工编号查询角色信息
	 * @param empId
	 * @return
	 */
	public List<RoleVo> findRoleByEmpCode(String empCode);

	/**
	 * 查询所有可用角色
	 * @return
	 */
	public List<RoleVo> findAllAvailableRole();

}
