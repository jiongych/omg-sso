/** 
 * @项目名称: FSP
 * @文件名称: AuthRoleBizTest extends BaseJunitTest 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test.biz;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.omg.sso.biz.IAuthRoleBiz;
import cn.uce.omg.sso.test.BaseJunitTest;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.ResultRoleVo;
import cn.uce.omg.sso.vo.UserInfoVo;
import cn.uce.omg.sso.vo.UserSearchVo;

public class AuthRoleBizTest extends BaseJunitTest {

	@Autowired
	private IAuthRoleBiz authRoleBiz;
	
	@Test
	public void findUserRoleTest() {
		try {
			UserSearchVo userSearchVo = new UserSearchVo();
			userSearchVo.setEmpCode("00006230");
			AuthResultVo resultVo = authRoleBiz.findUserRole(userSearchVo);
			if (resultVo != null) {
				System.out.println("根据用户查询用户角色:" + resultVo.getEmpCode());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void findUserTest() {
		try {
			UserSearchVo userSearchVo = new UserSearchVo();
			userSearchVo.setEmpCode("00006230");
			UserInfoVo resultVo = authRoleBiz.findUser(userSearchVo);
			if (resultVo != null) {
				System.out.println("查询用户相关信息:" + resultVo.getEmpCode());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void findRoleTest() {
		try {
			UserSearchVo userSearchVo = new UserSearchVo();
			userSearchVo.setEmpCode("00006230");
			ResultRoleVo resultVo = authRoleBiz.findRole(userSearchVo);
			if (resultVo != null) {
				System.out.println("根据角色名称或编号查询角色:" + resultVo.getUserName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	@Test
	public void findRoleRelTest() {
		try {
			UserSearchVo userSearchVo = new UserSearchVo();
			userSearchVo.setEmpCode("00006230");
			ResultRoleVo resultVo = authRoleBiz.findRoleRel(userSearchVo);
			if (resultVo != null) {
				System.out.println("根据员工ID或角色编号查询角色关系:" + resultVo.getUserName());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
