package cn.uce.omg.portal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalDictData;
import cn.uce.omg.portal.vo.PortalDictDataVo;

/**
 * @Description: (IDictDataDao) 
 * @author XJ
 * @date 2017年8月2日 下午3:04:45
 */
@Repository("fspDictDataDao")
public interface IDictDataDao extends IBaseDao<PortalDictData, Long>{
	/**
	 * @Description: (分页查询DictData) 
	 * @param dictDataVo
	 * @param page
	 * @return
	 * @author XJ
	 * @date 2017年8月2日 下午3:04:57
	 */
	Pagination<PortalDictDataVo> findDictDataByPage(PortalDictDataVo dictDataVo, Page page);
	
	PortalDictDataVo findOneByCondition(PortalDictDataVo dictDataVo);
	
	List<PortalDictDataVo> findListByTypeClassCode(String typeClassCode);
	
	List<PortalDictDataVo> findListByCondition(PortalDictDataVo dictDataVo);
	
	Integer findMaxTypeIdByTypeClassCode(String typeClassCode);
	
	/**
	 * @Description: (查询typeCode是否存在) 
	 * @param dictDataVo
	 * @return
	 * @author XJ
	 * @date 2017年7月11日 下午4:36:45
	 */
	PortalDictDataVo findExitTypeCode(PortalDictDataVo dictDataVo);
}
