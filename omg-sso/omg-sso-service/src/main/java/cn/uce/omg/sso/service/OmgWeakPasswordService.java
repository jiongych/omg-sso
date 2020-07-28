package cn.uce.omg.sso.service;

import cn.uce.omg.sso.dao.IOmgWeakPasswordDao;
import cn.uce.omg.sso.entity.OmgWeakPassword;

/**
 * @Description: Service类
 * @author UCE-CodeGenerator
 * @date 2018年05月25日 上午10:50:33
 */
public class OmgWeakPasswordService{
	
	/**
	 * 注入Dao
	 */
	private IOmgWeakPasswordDao omgWeakPasswordDao;
	/**
	 * @Description: 
	 * @param omgWeakPasswordVo 
	 * @return 
	 * @author UCE-CodeGenerator
	 * @date 2018年05月25日 上午10:50:33
	 */
	public OmgWeakPassword findByWeakEncryptPassword(String weakEncryptPassword) {
		OmgWeakPassword omgWeakPassword = omgWeakPasswordDao.findByWeakEncryptPassword(weakEncryptPassword);
		return omgWeakPassword;
	}
	public void setOmgWeakPasswordDao(IOmgWeakPasswordDao omgWeakPasswordDao) {
		this.omgWeakPasswordDao = omgWeakPasswordDao;
	}
	
}