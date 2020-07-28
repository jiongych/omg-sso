package cn.uce.portal.common.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.portal.common.base.CurrentUser;

/**
 * @Description: (ICommonDao) 
 * @author XJ
 * @date 2018年4月8日 下午1:41:08
 */
@Repository("commonDao")
public interface ICommonDao {
	
	/**
	 * @Description: (findPermissionCodeByUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月8日 下午1:41:23
	 */
	List<String> findPermissionCodeByUser(String empCode);
	
	/**
	 * @Description: (findCurrentUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月10日 下午5:02:39
	 */
	CurrentUser findCurrentUser(String empCode);

}
