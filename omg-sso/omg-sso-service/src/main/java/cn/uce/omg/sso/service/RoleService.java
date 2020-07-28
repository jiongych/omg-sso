/** 
 * @项目名称: FSP
 * @文件名称: RoleService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.base.page.Page;
import cn.uce.omg.sso.dao.IRoleDao;
import cn.uce.omg.sso.dao.IUserRoleRelDao;
import cn.uce.omg.sso.entity.UserRoleRel;
import cn.uce.omg.sso.util.ObjectConvertUtil;
import cn.uce.omg.vo.RoleVo;
import cn.uce.omg.vo.UserRoleRelVo;

/**
 * RoleService  
 * @Description: RoleService  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class RoleService {
	
	private IRoleDao roleDao;
	
	private IUserRoleRelDao userRoleRelDao;
	
	/**
	 * 查询所有角色
	 * @param roleDto
	 * @return
	 */
	public List<RoleVo> findAllAvailableRole(){
		return this.roleDao.findAllAvailableRole();
	}
	
	/**
	 * 根据条件查询角色
	 * @param roleVo
	 * @return
	 */
	public List<RoleVo> findRoleByCondition(RoleVo roleVo){
		return this.roleDao.findRoleByCondition(roleVo);
	}
	
	/**
	 * 根据员工编号查询角色信息
	 * @param empId
	 * @return
	 */
	public List<RoleVo> findRoleByEmpCode(String empCode){
		return this.roleDao.findRoleByEmpCode(empCode);
	}
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	
	/*------------------------------用户角色----------------------------------------*/
	/**
	 * 查询用户角色关系
	 * @param userRole
	 * @return
	 */
	public List<UserRoleRelVo> findAllUserRoleRel(){
		List<UserRoleRel> userRoleRelEntityList = this.userRoleRelDao.findAll();
		List<UserRoleRelVo> userRoleRelVoList = ObjectConvertUtil.convertList(userRoleRelEntityList, UserRoleRelVo.class);
		return userRoleRelVoList;
	}
	
	/**
	 * 根据条件查询用户角色关系
	 * @param userRole
	 * @return
	 */
	public List<UserRoleRelVo> findUserRoleRelByCondition(UserRoleRelVo userRole){
		if (userRole != null && userRole.getPage() != null) {
			Page page = userRole.getPage();
			return this.userRoleRelDao.findUserRoleRelByCondition(userRole, page);
		} else {
			return this.userRoleRelDao.findUserRoleRelByCondition(userRole);
		}
		
	}
 
	public void setUserRoleRelDao(IUserRoleRelDao userRoleRelDao) {
		this.userRoleRelDao = userRoleRelDao;
	}
}
