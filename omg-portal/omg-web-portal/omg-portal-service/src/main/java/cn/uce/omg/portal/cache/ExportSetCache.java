package cn.uce.omg.portal.cache;

import java.util.List;

import javax.annotation.Resource;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.core.cache.base.IKeyGenerator;
import cn.uce.core.cache.base.ResultMap;
import cn.uce.omg.portal.service.ExportSetService;
import cn.uce.omg.portal.vo.PortalExportSetVo;

/**
 * 
 *<pre>
 * ExportSetCache
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月20日上午10:45:14
 */
public class ExportSetCache extends CacheSupport<PortalExportSetVo> {

	@Resource
	private ExportSetService fspExportSetService;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 回调方法
	 * </pre>
	 * @param key
	 * @return
	 * @throws BusinessException
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月20日上午10:45:23
	 */
	@Override
	public PortalExportSetVo doGet(String key) throws BusinessException {
		PortalExportSetVo exportSetVo = fspExportSetService.findExportSetByKeyCache(key);
		return exportSetVo;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 获取CacheId
	 * </pre>
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月20日上午10:45:43
	 */
	@Override
	public String getCacheId() {
		return getClass().getSimpleName();
	}
	/** 
     * 默认空实现初始化数据回调函数
     * 当{@link CacheSupport#lazy}=false时需要重写这个方法
     */
	public ResultMap<String, PortalExportSetVo> doInitialization(IKeyGenerator<String> generator) throws BusinessException {
		ResultMap<String, PortalExportSetVo> resultMap = new ResultMap<String, PortalExportSetVo>(generator);
		List<PortalExportSetVo> exportSetVos = fspExportSetService.findValidExportSet();
		if (null != exportSetVos) {
			for (PortalExportSetVo exportSetVo : exportSetVos) {
				resultMap.put(exportSetVo.getSetKey(), exportSetVo);
			}
		}
		return resultMap;
	}

}
