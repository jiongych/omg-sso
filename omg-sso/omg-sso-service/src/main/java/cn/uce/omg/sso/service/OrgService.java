/** 
 * @项目名称: FSP
 * @文件名称: OrgService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.omg.sso.dao.IOrgDao;
import cn.uce.omg.vo.OrgVo;

/**
 * OrgService  
 * @Description: OrgService  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public class OrgService {
	
	private IOrgDao orgDao;
	
	/**
	 * 根据orgId查询机构信息
	 * @param OrgId
	 * @return
	 */
	public OrgVo findOrgById(Integer orgId){
		if (orgId != null) {
			OrgVo search = new OrgVo();
			search.setOrgId(orgId);
			return this.orgDao.findOrgByUnique(search);
		}
		return null;
	}
	
	/**
	 * 
	 * 根据机构id集合查询机构
	 * @param orgIdList
	 * @return
	 * @author huangting
	 * @date 2017年5月5日 下午3:29:23
	 */
	public List<OrgVo> findOrgByIds(List<Integer> orgIdList) {
		return  this.orgDao.findOrgByIds(orgIdList);
	}
	
	/**
	 * 根据orgCode查询机构信息
	 * @param orgCode
	 * @return
	 */
	public OrgVo findOrgByCode(String orgCode){
		if (orgCode != null) {
			OrgVo searchOrgVo = new OrgVo();
			searchOrgVo.setOrgCode(orgCode);
			return this.orgDao.findOrgByUnique(searchOrgVo);
		}
		return null;
	}
	
	
	public void setOrgDao(IOrgDao orgDao) {
		this.orgDao = orgDao;
	}

}
