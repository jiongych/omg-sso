/** 
 * @项目名称: FSP
 * @文件名称: UpdPwdItemService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.omg.sso.dao.IUpdPwdItemDao;
import cn.uce.omg.sso.entity.UpdPwdItem;
import cn.uce.omg.sso.vo.UpdPwdItemVo;


/**
 * UpdPwdItemService  
 * @Description: UpdPwdItemService  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class UpdPwdItemService {
	private IUpdPwdItemDao updPwdItemDao;
	/**
	 * 新增数据
	 * @param updPwdItem
	 * @return
	 */
	public int insertUpdPwdItem(UpdPwdItem updPwdItem) {
		int result = updPwdItemDao.insert(updPwdItem);
		return result;
	}
	
	/**
	 * 查询密码历史明细
	 * @param updPwdItem
	 * @return
	 */
	public List<UpdPwdItem> findByThrDay(UpdPwdItemVo updPwdItemVo) {
		return updPwdItemDao.findByThrDay(updPwdItemVo);
	}

	public void setUpdPwdItemDao(IUpdPwdItemDao updPwdItemDao) {
		this.updPwdItemDao = updPwdItemDao;
	}
	

	
}
