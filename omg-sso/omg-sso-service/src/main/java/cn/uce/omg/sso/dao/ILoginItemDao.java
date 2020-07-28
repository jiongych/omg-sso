/** 
 * @项目名称: FSP
 * @文件名称: ILoginItemDao extends IBaseDao<LoginItem, Long> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.LoginItem;
import cn.uce.omg.vo.LoginItemVo;

/**
 * ILoginItemDao extends IBaseDao<LoginItem, Long>  
 * @Description: ILoginItemDao extends IBaseDao<LoginItem, Long>  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface ILoginItemDao extends IBaseDao<LoginItem, Long> {

	/**
	 * 插入明细数据
	 * @param loginItemVo
	 * @return
	 * @throws Exception
	 */
	public Integer insertItem(LoginItemVo loginItemVo) throws Exception;
}
