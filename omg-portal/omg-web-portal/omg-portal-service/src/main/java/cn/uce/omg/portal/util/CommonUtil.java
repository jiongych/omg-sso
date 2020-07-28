package cn.uce.omg.portal.util;

import java.util.List;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.vo.PortalDictDataVo;

public class CommonUtil {
	
	public static boolean isSuperAdmin(String empCode){
		//从数据字典获取超级管理员列表
		ICache<String, List<PortalDictDataVo>> dictDataCache =CacheManager.getInstance().getCache("DictDataCache");
		List<PortalDictDataVo> dictDataList = dictDataCache.get("SUPER_ADMIN");
		
		for(PortalDictDataVo dictDataVo : dictDataList){
			if(empCode.equals(dictDataVo.getTypeCode())){
				return true;
			}
		}
		return false;
	}

}
