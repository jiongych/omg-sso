package cn.uce.portal.common.biz;

import java.util.List;

import cn.uce.portal.common.base.CurrentUser;

/**
 * @Description: (ICommonBiz) 
 * @author XJ
 * @date 2018年4月8日 下午2:05:34
 */
public interface ICommonBiz {
	
	/**
	 * @Description: (findPermissionCodeByUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月8日 下午2:05:43
	 */
	List<String> findPermissionCodeByUser(String empCode);
	
	/**
	 * @Description: (getPermissionCodeByUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月8日 下午2:18:31
	 */
	List<String> getPermissionCodeByUser(String empCode);
	
	/**
	 * @Description: (findCurrentUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月10日 下午5:04:41
	 */
	CurrentUser findCurrentUser(String empCode);

}
