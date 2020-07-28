/** 
 * @项目名称: FSP
 * @文件名称: AuthRoleService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.main.sso.service;

import cn.uce.omg.sso.biz.IAuthRoleBiz;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.ResultRoleVo;
import cn.uce.omg.sso.vo.UserInfoVo;
import cn.uce.omg.sso.vo.UserSearchVo;

/**
 * 认证系统角色接口
 * @author tanchong
 *
 */
public class AuthRoleService {

	private IAuthRoleBiz authRoleBiz;

	/**
	 * 根据用户查询用户角色
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public AuthResultVo findUserRole(UserSearchVo userSearchVo) throws Exception {
		return authRoleBiz.findUserRole(userSearchVo);
	}

	/**
	 * 查询用户相关信息
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	public UserInfoVo findUser(UserSearchVo userSearchVo) throws Exception {
		return authRoleBiz.findUser(userSearchVo);
	}
	
	/**
	 * 根据角色名称或编号查询角色
	 */
	public ResultRoleVo findRole(UserSearchVo userSearchVo) throws Exception {
		return authRoleBiz.findRole(userSearchVo);
	}
	
	/**
	 * 根据员工编号或角色编号查询角色关系
	 */
	public ResultRoleVo findRoleRel(UserSearchVo userSearchVo) throws Exception {
		return authRoleBiz.findRoleRel(userSearchVo);
	}

	public IAuthRoleBiz getAuthRoleBiz() {
		return authRoleBiz;
	}

	public void setAuthRoleBiz(IAuthRoleBiz authRoleBiz) {
		this.authRoleBiz = authRoleBiz;
	}

}
