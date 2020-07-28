package cn.uce.omg.portal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalDictType;
import cn.uce.omg.portal.vo.PortalDictTypeVo;

/**
 * @Description: (IDictTypeDao) 
 * @author XJ
 * @date 2017年8月8日 下午5:01:06
 */
@Repository("fspDictTypeDao")
public interface IDictTypeDao extends IBaseDao<PortalDictType, Integer> {
	
	/**
	 * @Description: (条件查询DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月8日 下午5:03:36
	 */
	List<PortalDictTypeVo> findDictTypeList(PortalDictTypeVo dictTypeVo);
	
	/**
	 * @Description: (根据typeClassCode查询DictType) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月8日 下午5:03:56
	 */
	PortalDictTypeVo findByTypeClassCode(String typeClassCode);
	
	/**
	 * @Description: (根据typeClassCode删除DictType) 
	 * @param typeClassCode
	 * @return
	 * @author XJ
	 * @date 2017年8月8日 下午5:04:30
	 */
	int deleteByTypeClassCode(String typeClassCode);
	
	/**
	 * @Description: (根据id更新DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月8日 下午5:05:11
	 */
	int updateById(PortalDictTypeVo dictTypeVo);
	
	/**
	 * @Description: (根据code更新DictType) 
	 * @param dictTypeVo
	 * @return
	 * @author XJ
	 * @date 2017年8月8日 下午5:05:47
	 */
	int updateByCode(PortalDictTypeVo dictTypeVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过empcode查询所属权限对应的系统编码
	 * </pre>
	 * @param empCode
	 * @return
	 * @return List<String>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月28日上午9:26:08
	 */
	List<String> findPermissionByEmpCode(String empCode);
}
