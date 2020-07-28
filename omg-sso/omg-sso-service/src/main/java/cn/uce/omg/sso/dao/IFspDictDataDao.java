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
import cn.uce.omg.sso.entity.SysType;
import cn.uce.omg.vo.SysTypeVo;

/**
 * 数据字典DAO
 * @author zhangRb
 * @date 2018年9月5日
 */
public interface IFspDictDataDao extends IBaseDao<SysType, Integer> {
	
	/**
	 * 根据条件查询数据
	 * @param typeClassCode
	 * @return
	 * @author zhangRb
	 * @date 2018年9月5日
	 */
	List<SysTypeVo> findListByTypeClassCode(String typeClassCode);
}
