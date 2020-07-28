/**
 * 
 */
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.base.exception.BusinessException;
import cn.uce.omg.sso.dao.IFspDictDataDao;
import cn.uce.omg.vo.SysTypeVo;

/**
 * @author zhangRb
 *
 */
public class FspDictDataService {
	
	/**
	 * 数据字典DAO
	 */
	private IFspDictDataDao fspDictDataDao;

	/**
	 * 根据条件查询数据
	 * @param typeClassCode
	 * @return
	 * @author zhangRb
	 * @date 2018年9月5日
	 */
	public List<SysTypeVo> findListByTypeClassCode(String typeClassCode) throws BusinessException {
		return fspDictDataDao.findListByTypeClassCode(typeClassCode);
	}

	/**
	 * 设置 数据字典DAO
	 * @param fspDictDataDao 数据字典DAO
	 */
	public void setFspDictDataDao(IFspDictDataDao fspDictDataDao) {
		this.fspDictDataDao = fspDictDataDao;
	}
}
