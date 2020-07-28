/** 
 * @项目名称: FSP
 * @文件名称: IAuthRoleApi 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.api;

import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.UserInfoVo;
import cn.uce.omg.sso.vo.UserSearchVo;

/**
 * 权限相关接口
 * @author tanchong
 *
 */
public interface IAuthRoleApi {

	
	/**
	 * 查询用户相关信息
	 * @param empId
	 * @return
	 * @throws Exception
	 */
	UserInfoVo findUser(UserSearchVo userSearchVo) throws Exception;
	
	/**
	 * 
	 * @Description:查询用户角色
	 * @param userSearchVo
	 * @return
	 * @throws Exception
	 * @author huangting
	 * @date 2017年4月10日 下午8:15:04
	 */
	AuthResultVo findUserRole(UserSearchVo userSearchVo) throws Exception;
	
}
