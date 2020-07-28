/** 
 * @项目名称: FSP
 * @文件名称: UserTest extends BaseJunitTest 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.test.biz;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.sso.entity.UpdPwdItem;
import cn.uce.omg.sso.test.BaseJunitTest;
import cn.uce.omg.vo.LoginItemVo;
import cn.uce.omg.vo.UserRoleRelVo;
import cn.uce.omg.vo.UserVo;
import cn.uce.utils.FastJsonUtil;

/**
 * UserTest extends BaseJunitTest  
 * @Description: UserTest extends BaseJunitTest  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UserTest extends BaseJunitTest {
	
	@Resource
	private IUserBiz userBiz;
	
	@Test
	public void findUserByEmpIdTest(){
		UserVo userVo = userBiz.findUserByEmpId(123);
		System.out.println("findUserByEmpId, 返回的结果为: " + FastJsonUtil.toJsonString(userVo));
	}
	
	@Test
	public void findUserByEmpCodeTest(){
		UserVo userVo = userBiz.findUserByEmpCode("sfdf");
		System.out.println("findUserByEmpCode, 返回的结果为: " + FastJsonUtil.toJsonString(userVo));
	}

	@Test
	public void findUserByMobileTest(){
		UserVo userVo = userBiz.findUserByMobile("15101033233");
		System.out.println("findUserByMobile, 返回的结果为: " + FastJsonUtil.toJsonString(userVo));
	}
	
	@Test
	public void findUserByEmailTest(){
		UserVo userVo = userBiz.findUserByEmail("7hihuihw04@qq.com");
		System.out.println("findUserByEmail, 返回的结果为: " + FastJsonUtil.toJsonString(userVo));
	}
	
	@Test
	public void updPwdTest(){
		Integer empId = 102275;
		UserVo userVo = userBiz.findUserByEmpId(empId);
		if(userVo != null){
			userVo.setOldPassword("e05cdba4a2f5cfab7219b2299651ff3a");
			userVo.setPassword("uc123457");
			userVo.setUpdateEmp("0");
			userVo.setUpdateOrg(0);
			boolean flag = userBiz.updateMyUserPassword(userVo);
			System.out.println("updateMyUserPassword, 结果为" + flag);
		}
	}
	
	@Test
	public void updateUserPasswordTest(){
		UserVo userVo = new UserVo();
		userVo.setEmpCode("erf23413");
		userVo.setPassword("23432423");
		boolean result = userBiz.updateUserPassword(userVo,true);
		System.out.println("updateUserPassword, 结果为" + result);
	}
	
	@Test
	public void updateMyUserPasswordTest(){
		UserVo userVo = new UserVo();
		userVo.setEmpCode("erf23413");
		userVo.setPassword("23432423");
		boolean result = userBiz.updateMyUserPassword(userVo);
		System.out.println("updateMyUserPassword, 结果为" + result);
	}
	
	@Test
	public void insertUpdPwdItemTest(){
		UpdPwdItem item = new UpdPwdItem();
		item.setCreateTime(new Date());
		item.setEmpId(12313);
		item.setPassword("3234234");
		item.setUserName("23423df");
		item.setOperateEmp("23123");
		item.setSystemCode("3242");
		item.setSystemCode("2312");
		item.setCreateTime(new Date());
		item.setOperateTime(new Date());
		item.setUpdateTime(new Date());
		int result = userBiz.insertUpdPwdItem(item);
		System.out.println("insertUpdPwdItem, 结果为" + result);
	}

	@Test
	public void insertLoginItemTest() throws Exception{
		LoginItemVo loginItemVo = new LoginItemVo();
		loginItemVo.setEmpId(1231);
		loginItemVo.setUserName("sdad");
		loginItemVo.setSystemCode("2312");
		loginItemVo.setCreateTime(new Date());
		loginItemVo.setOperateTime(new Date());
		loginItemVo.setUpdateTime(new Date());
		loginItemVo.setOperateType("login");
		int result = userBiz.insertLoginItem(loginItemVo);
		System.out.println("insertLoginItem, 结果为" + result);
	}
	
	@Test
	public void updateUserLockStateTest() throws Exception{
		boolean result = userBiz.updateUserLockState("234234", true);
		System.out.println("updateUserLockState, 结果为" + result);
	}
	
	@Test
	public void findEmpUserByIdTest(){
		UserVo user = userBiz.findUserByEmpId(1023207);
		System.out.println("findUserByEmpId, 结果为" +  FastJsonUtil.toJsonString(user));
	}
}
