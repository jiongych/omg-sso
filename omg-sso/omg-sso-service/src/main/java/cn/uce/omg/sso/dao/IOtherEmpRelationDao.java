/** 
 * @项目名称: FSP
 * @文件名称: IOtherEmpRelationDao extends IBaseDao<OtherEmpRelation, Long> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import java.util.List;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.OtherEmpRelation;
import cn.uce.omg.vo.OtherEmpRelationVo;

/**
 * IOtherEmpRelationDao extends IBaseDao<OtherEmpRelation, Long>  
 * @Description: IOtherEmpRelationDao extends IBaseDao<OtherEmpRelation, Long>  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IOtherEmpRelationDao extends IBaseDao<OtherEmpRelation, Long> {
	
	/**
	 * 根据条件查询绑定关系
	 * @param otherEmpRelationVo
	 * @return
	 */
	public List<OtherEmpRelationVo> findOtherEmpRelationByCondition(OtherEmpRelationVo otherEmpRelationVo);
}
