/** 
 * @项目名称: FSP
 * @文件名称: LoginItemService  
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import cn.uce.omg.sso.dao.ILoginItemDao;
import cn.uce.omg.vo.LoginItemVo;



/**
 * LoginItemService   
 * @Description: LoginItemService   
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class LoginItemService  {
	private ILoginItemDao loginItemDao;
	/**
	 * 插入明细数据
	 * @param loginItemVo
	 * @return
	 * @throws Exception
	 */
	public int insertLoginItem(LoginItemVo loginItemVo) throws Exception {
		int result = this.loginItemDao.insertItem(loginItemVo);
		return result;
	}

	public void setLoginItemDao(ILoginItemDao loginItemDao) {
		this.loginItemDao = loginItemDao;
	}
	
}
