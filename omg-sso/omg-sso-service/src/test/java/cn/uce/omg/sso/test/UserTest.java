/** 
 * @项目名称: FSP
 * @文件名称: UserTest extends BaseJunitTest 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.sso.biz.IEmpBiz;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.sso.cache.EmpCache;
import cn.uce.omg.sso.cache.UserCache;
import cn.uce.omg.sso.service.EmpService;
import cn.uce.omg.sso.service.UserService;
import cn.uce.omg.vo.UserVo;

/**
 * UserTest extends BaseJunitTest  
 * @Description: UserTest extends BaseJunitTest  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UserTest extends BaseJunitTest {

	@Autowired
	private UserService userService;
	@Autowired
	private EmpService empService;
	@Autowired
	private IEmpBiz empBiz;
	@Autowired
	private IUserBiz userBiz;
	@Autowired
	private EmpCache empCache;
	@Autowired
	private UserCache userCache;
	
	
	@Test
	public void updPwdTest(){
		Integer empId = 1023205;
		UserVo userVo = userService.findUserByEmpId(empId);
		userVo.setOldPassword("e05cdba4a2f5cfab7219b2299651ff3a");
		userVo.setPassword("uc123457");
		userVo.setUpdateEmp("0");
		userVo.setUpdateOrg(0);
		userBiz.updateMyUserPassword(userVo);
	}
	
	@Test
	public void findEmpUserById(){
		UserVo user = userBiz.findUserByEmpId(1023207);
		if (user != null && user.getUserRoleRelList() != null) {
			System.out.println(user.getUserRoleRelList().size());
		}
		
	}
	
	
}
