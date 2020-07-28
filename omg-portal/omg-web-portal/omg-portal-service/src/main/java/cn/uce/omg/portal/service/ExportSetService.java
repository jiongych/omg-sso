package cn.uce.omg.portal.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.uce.core.cache.CacheManager;
import cn.uce.core.cache.base.ICache;
import cn.uce.omg.portal.dao.IExportSetDao;
import cn.uce.omg.portal.entity.PortalExportSet;
import cn.uce.omg.portal.exception.DictDataException;
import cn.uce.omg.portal.vo.PortalExportSetVo;
import cn.uce.web.common.util.ObjectConvertUtil;

/**
 * 导出文件工具类Service
 *<pre>
 * ExportUtilService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月19日下午4:22:32
 */
@Service("fspExportSetService")
public class ExportSetService {

	@Resource
	private IExportSetDao fspExportSetDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存导出文件设置
	 * </pre>
	 * @param PortalExportSetVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午4:19:48
	 */
	public int addExportSet(PortalExportSetVo exportSetVo) {
		PortalExportSet exportSet = ObjectConvertUtil.convertObject(exportSetVo, PortalExportSet.class);
		int result = fspExportSetDao.insert(exportSet);
		if(result > 0){
			CacheManager.getInstance().getCache("ExportSetCache").refresh(exportSet.getSetKey());
		}else{
			logger.error("@_@导出设置增加失败");
			throw new DictDataException("add exportSet fail","导出设置增加失败");
		}
		
		return result;
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过key查询导出文件设置
	 * </pre>
	 * @param key
	 * @return
	 * @return ExportSetVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午4:19:51
	 */
	public PortalExportSetVo findExportSetByKey(String key) {
		ICache<String, PortalExportSetVo> exportSetCache = CacheManager.getInstance().getCache("ExportSetCache");
		return exportSetCache.get(key);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过key查询数据，用于缓存类中。
	 * </pre>
	 * @param key
	 * @return
	 * @return ExportSetVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月20日上午11:02:39
	 */
	public PortalExportSetVo findExportSetByKeyCache(String key) {
		return fspExportSetDao.findExportSetByKey(key);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新导出文件设置
	 * </pre>
	 * @param PortalExportSetVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午4:19:54
	 */
	public int updateExportSet(PortalExportSetVo exportSetVo) {
		PortalExportSet exportSet = ObjectConvertUtil.convertObject(exportSetVo, PortalExportSet.class);
		int result = fspExportSetDao.updateById(exportSet);
		if(result > 0){
			CacheManager.getInstance().getCache("ExportSetCache").refresh(exportSet.getSetKey());
		}else{
			logger.error("@_@导出设置更新失败");
			throw new DictDataException("update exportSet fail","导出设置更新失败");
		}
		
		return result;
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询所有的数据
	 * </pre>
	 * @return
	 * @return List<ExportSetVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月20日上午10:42:54
	 */
	public List<PortalExportSetVo> findValidExportSet() {
		List<PortalExportSet> exportSets = fspExportSetDao.findAll();
		List<PortalExportSetVo> exportSetVos =  ObjectConvertUtil.convertList(exportSets, PortalExportSetVo.class);
		return exportSetVos;
	}
}
