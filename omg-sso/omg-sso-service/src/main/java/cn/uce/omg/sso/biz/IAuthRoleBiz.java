/** 
 * @项目名称: FSP
 * @文件名称: IAuthRoleBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.ResultRoleVo;
import cn.uce.omg.sso.vo.UserInfoVo;
import cn.uce.omg.sso.vo.UserSearchVo;

/**
 * 权限相关接口
 * @author tanchong
 *
 */
public interface IAuthRoleBiz {

	/**
	 * 根据用户查询用户角色
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	AuthResultVo findUserRole(UserSearchVo userSearchVo) throws Exception;

	/**
	 * 查询用户相关信息
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	UserInfoVo findUser(UserSearchVo userSearchVo) throws Exception;
	
	/**
	 * 根据角色名称或编号查询角色
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	ResultRoleVo findRole(UserSearchVo userSearchVo) throws Exception;
	
	/**
	 * 根据员工ID或角色编号查询角色关系
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 */
	ResultRoleVo findRoleRel(UserSearchVo userSearchVo) throws Exception;
}
