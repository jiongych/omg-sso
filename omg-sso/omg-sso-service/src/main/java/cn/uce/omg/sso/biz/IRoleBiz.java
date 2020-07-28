/** 
 * @项目名称: FSP
 * @文件名称: IRoleBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.omg.vo.RoleVo;
import cn.uce.omg.vo.UserRoleRelVo;

/**
 * IRoleBiz  
 * @Description: IRoleBiz  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IRoleBiz {
	
	/**
	 * 查询所有可用角色
	 * @return
	 */
	public List<RoleVo> findAllAvailableRole();
	
	/**
	 * 根据条件查询角色
	 * @param roleDto
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
	 * 查询用户角色关系
	 * @param userRoleRelVo
	 * @return
	 */
	public List<UserRoleRelVo> findAllUserRoleRel();
	
	/**
	 * 根据条件查询用户角色关系
	 * @param userRoleRelVo
	 * @return
	 */
	public List<UserRoleRelVo> findUserRoleRelByCondition(UserRoleRelVo userRoleRelVo);
	
}
