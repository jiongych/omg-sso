package cn.uce.portal.common.biz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.portal.common.base.CurrentUser;
import cn.uce.portal.common.biz.ICommonBiz;
import cn.uce.portal.common.service.CommonService;


/**
 * @Description: (CommonBiz) 
 * @author XJ
 * @date 2018年4月8日 下午2:06:29
 */
@Service("commonBiz")
public class CommonBiz implements ICommonBiz {
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getPermissionCodeByUser</p> 
	* <p>Description: 根据用户从缓存中获取权限码</p> 
	* @param empCode
	* @return 
	* @see cn.uce.portal.common.biz.ICommonBiz#getPermissionCodeByUser(java.lang.String)
	 */
	@Override
	public List<String> getPermissionCodeByUser(String empCode){
		ICache<String,List<String>> permissionCodeCache = CacheManager.getInstance().getCache("PermissionCodeCache");
		return permissionCodeCache.get(empCode);	
	}
	/**
	 * (非 Javadoc) 
	* <p>Title: findPermissionCodeByUser</p> 
	* <p>Description: 根据用户查询权限码</p> 
	* @param empCode
	* @return 
	* @see cn.uce.portal.common.biz.ICommonBiz#findPermissionCodeByUser(java.lang.String)
	 */
	@Override
	public List<String> findPermissionCodeByUser(String empCode) {
		return commonService.findPermissionCodeByUser(empCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: findCurrentUser</p> 
	* <p>Description: 查询当前用户</p> 
	* @param empCode
	* @return 
	* @see cn.uce.portal.common.biz.ICommonBiz#findCurrentUser(java.lang.String)
	 */
	@Override
	public CurrentUser findCurrentUser(String empCode) {
		return commonService.findCurrentUser(empCode);
	}

}
