/** 
 * @项目名称: FSP
 * @文件名称: IUpdPwdItemDao extends IBaseDao<UpdPwdItem, Long> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import java.util.List;


import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.UpdPwdItem;
import cn.uce.omg.sso.vo.UpdPwdItemVo;



/**
 * IUpdPwdItemDao extends IBaseDao<UpdPwdItem, Long>  
 * @Description: IUpdPwdItemDao extends IBaseDao<UpdPwdItem, Long>  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IUpdPwdItemDao extends IBaseDao<UpdPwdItem, Long> {
	
	/**
	 * 查询密码历史明细
	 * @param updPwdItem
	 * @return
	 */
	public List<UpdPwdItem> findByThrDay(UpdPwdItemVo updPwdItemVo);
	
}
