/** 
 * @项目名称: FSP
 * @文件名称: FindRoleTest extends BaseJunitTest
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test;

import java.text.SimpleDateFormat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.omg.sso.api.IAuthRoleApi;
import cn.uce.omg.sso.biz.IAuthBiz;
import cn.uce.omg.sso.vo.AuthResultVo;
import cn.uce.omg.sso.vo.UserSearchVo;
	
public class FindRoleTest extends BaseJunitTest{

	@Autowired
	private IAuthBiz authBiz;
	@Autowired
	private IAuthRoleApi authRoleBiz;
	
	//先登录获取tokenid然后,通过tokenid获取过期时间.
	@Test
	public void findRoles(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		UserSearchVo userSearch = new UserSearchVo();
		userSearch.setUserName("0001230w");
		//userSearch.setUserName("00012349");
		AuthResultVo authResultVo = null;
		try {
			authResultVo = authRoleBiz.findUserRole(userSearch);
			System.out.println("查询角色列表");
			System.out.println("tokenid" + authResultVo.getTokenId());
			System.out.println("角色列表 " + authResultVo.getUserRoleList());
		} catch (Exception e) {
			System.out.println("查询角色列表失败" + e.getMessage());
		}
	}
}
