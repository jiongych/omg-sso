/** 
 * @项目名称: FSP
 * @文件名称: IOrgDao extends IBaseDao<Org, Long>
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import java.util.List;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.Org;
import cn.uce.omg.vo.OrgVo;

/**
 * IOrgDao extends IBaseDao<Org, Long> 
 * @Description: IOrgDao extends IBaseDao<Org, Long> 
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IOrgDao extends IBaseDao<Org, Long>{
	
	/**
	 * 根据条件查询单个机构信息
	 * @param OrgId
	 * @return
	 */
	public OrgVo findOrgByUnique(OrgVo orgVo);
	
	/**
	 * 根据机构id集合查询机构
	 * @param orgvo
	 * @return
	 */
	public List<OrgVo> findOrgByIds(List<Integer> orgIdList);
}
