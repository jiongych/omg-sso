package cn.uce.omg.portal.cache;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.core.cache.base.IKeyGenerator;
import cn.uce.core.cache.base.ResultMap;
import cn.uce.omg.portal.service.DictDataService;
import cn.uce.omg.portal.vo.PortalDictDataVo;
/**
 * 
 * @Description: TODO(数据字典缓存实现) 
 * @author XJ
 * @date 2017年4月13日 下午5:40:42
 */
public class DictDataCache extends CacheSupport<List<PortalDictDataVo>> {
	
	@Resource
	private DictDataService dictDataService;

	@Override
	public List<PortalDictDataVo> doGet(String typeClassCode) throws BusinessException {
		return dictDataService.findByTypeClassCode(typeClassCode);
	}

	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}
	
	 /** 
     * 默认空实现初始化数据回调函数
     * 当{@link CacheSupport#lazy}=false时需要重写这个方法
     */
   public ResultMap<String,List<PortalDictDataVo>> doInitialization(IKeyGenerator<String> generator) throws BusinessException {
	   
		List<PortalDictDataVo> dictDataVoList=dictDataService.findAll();
		ResultMap<String, List<PortalDictDataVo>> dictDataCache= new ResultMap<String, List<PortalDictDataVo>>(generator);
		
		for(PortalDictDataVo dictDataVo:dictDataVoList){
			if(dictDataCache.containsKey(generate(dictDataVo.getTypeClassCode()))){
				dictDataCache.get(dictDataVo.getTypeClassCode()).add(dictDataVo);
			}else{
				List<PortalDictDataVo> dictDataVoListItem = new ArrayList<PortalDictDataVo>();
				dictDataVoListItem.add(dictDataVo);
				dictDataCache.put(dictDataVo.getTypeClassCode(), dictDataVoListItem);
			}
		}
		return dictDataCache;
    }
   
}
