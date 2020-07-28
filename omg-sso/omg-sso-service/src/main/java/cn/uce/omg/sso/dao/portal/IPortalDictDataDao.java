package cn.uce.omg.sso.dao.portal;

import java.util.List;

import cn.uce.omg.sso.vo.portal.PortalDictDataVo;

/**
 * @Description: (IPortalDictDataDao) 
 * @author XJ
 * @date 2018年5月3日 下午6:46:38
 */
public interface IPortalDictDataDao {
	
	/**
	 * @Description: (findDictDataByTypeClassCode) 
	 * @return
	 * @author XJ
	 * @date 2018年5月3日 下午6:46:47
	 */
	public List<PortalDictDataVo> findDictDataByTypeClassCode(String typeClassCode);

}
