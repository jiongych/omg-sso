/** 
 * @项目名称: FSP
 * @文件名称: ICmsOrgDao extends BaseDao<CmsOrgDto> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import java.util.List;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.CmsOrg;
import cn.uce.omg.vo.CmsOrgVo;

/**
 * 乾坤机构Dao
 * @author huangting
 * @date 2017年4月5日
 */
public interface ICmsOrgDao extends IBaseDao<CmsOrg, Integer> {
	
	/**
	 * 根据条件查询数据
	 * @param cmsOrgDto
	 * @param page
	 * @return
	 * @author crj
	 * @date 2018年7月9日 下午7:41:07
	 */
	List<CmsOrgVo> findByCmsOrgIds(List<Integer> cmsOrgIds);
}
