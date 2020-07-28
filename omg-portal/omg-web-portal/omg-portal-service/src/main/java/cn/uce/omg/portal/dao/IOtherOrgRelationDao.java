package cn.uce.omg.portal.dao;


import org.springframework.stereotype.Repository;

import cn.uce.omg.portal.vo.OtherOrgRelationVo;


/**
 * @Description: (ICmsOrgDao) 
 * @author XJ
 * @date 2017年8月2日 下午4:27:29
 */
@Repository("otherOrgRelationDao")
public interface IOtherOrgRelationDao {
	
	OtherOrgRelationVo findOtherOrgRelationByOrgId(String orgId);
}
