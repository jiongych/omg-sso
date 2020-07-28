package cn.uce.omg.sso.dao;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.OmgWeakPassword;

/**
 * @Description: DAO接口类
 * @author UCE-CodeGenerator
 * @date 2018年05月25日 上午10:50:33
 */
public interface IOmgWeakPasswordDao extends IBaseDao<OmgWeakPassword, Long> {
	/**
	 * @Description: 根据密文查询
	 * @param omgWeakPasswordVo 
	 * @return 
	 * @author UCE-CodeGenerator
	 * @date 2018年05月25日 上午10:50:33
	 */
	public OmgWeakPassword findByWeakEncryptPassword(String weakEncryptPassword);
	
}