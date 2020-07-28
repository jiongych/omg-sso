package cn.uce.omg.portal.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.core.cache.base.IKeyGenerator;
import cn.uce.core.cache.base.ResultMap;
import cn.uce.omg.portal.biz.IPermissionBiz;
import cn.uce.omg.portal.vo.PermissionTreeVo;
import cn.uce.omg.portal.vo.PermissionVo;

public class PermissionTreeCache extends CacheSupport<List<PermissionTreeVo>>{
	
	@Autowired
	private IPermissionBiz fspPermissionBiz;
	
	@Override
	public List<PermissionTreeVo> doGet(String arg0) throws BusinessException {
		PermissionVo permissionVo = new PermissionVo();
		return fspPermissionBiz.findPermissionTree(permissionVo);
	}

	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}
	
	 /** 
     * 默认空实现初始化数据回调函数
     * 当{@link CacheSupport#lazy}=false时需要重写这个方法
     */
   public ResultMap<String,List<PermissionTreeVo>> doInitialization(IKeyGenerator<String> generator) throws BusinessException {
	   	PermissionVo permissionVo = new PermissionVo();
		List<PermissionTreeVo> permissionTreeVoList=fspPermissionBiz.findPermissionTree(permissionVo);
		ResultMap<String, List<PermissionTreeVo>> permissionTreeCache= new ResultMap<String, List<PermissionTreeVo>>(generator);
		permissionTreeCache.put("permissionTree", permissionTreeVoList);
		return permissionTreeCache;
    }
}
