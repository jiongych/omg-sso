/** 
 * @项目名称: FSP
 * @文件名称: EmpService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.omg.sso.dao.ICmsOrgDao;
import cn.uce.omg.vo.CmsOrgVo;

/**
 * EmpService  
 * @Description: EmpService  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class CmsOrgService {
	private ICmsOrgDao cmsOrgDao;

	public void setCmsOrgDao(ICmsOrgDao cmsOrgDao) {
		this.cmsOrgDao = cmsOrgDao;
	}
    /**
     * 根据条件查询乾坤机构
     * @param cmsOrgIds
     * @return
     */
	public List<CmsOrgVo> findByCmsOrgIds(List<Integer> cmsOrgIds){
		return cmsOrgDao.findByCmsOrgIds(cmsOrgIds);
	}


}
