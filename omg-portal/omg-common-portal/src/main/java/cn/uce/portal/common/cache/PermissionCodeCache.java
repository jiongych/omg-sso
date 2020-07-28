package cn.uce.portal.common.cache;

import java.util.List;

import javax.annotation.Resource;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.portal.common.biz.ICommonBiz;

/**
 * @Description: (PermissionCodeCache) 
 * @author XJ
 * @date 2018年4月8日 下午2:12:46
 */
public class PermissionCodeCache extends CacheSupport<List<String>>{
	
	@Resource
	private ICommonBiz commonBiz;
	
	/**
	 * (非 Javadoc) 
	* <p>Title: doGet</p> 
	* <p>Description: 根据用户获取权限码</p> 
	* @param empCode
	* @return
	* @throws BusinessException 
	* @see cn.uce.core.cache.base.CacheCallBack#doGet(java.lang.Object)
	 */
	@Override
	public List<String> doGet(String empCode) throws BusinessException {
		return commonBiz.findPermissionCodeByUser(empCode);
	}
	
	/**
	 * (非 Javadoc) 
	* <p>Title: getCacheId</p> 
	* <p>Description: 获取缓存ID</p> 
	* @return 
	* @see cn.uce.core.cache.base.ICache#getCacheId()
	 */
	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}

}
