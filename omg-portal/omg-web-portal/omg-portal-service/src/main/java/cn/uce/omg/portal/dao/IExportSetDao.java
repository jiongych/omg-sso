package cn.uce.omg.portal.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalExportSet;
import cn.uce.omg.portal.vo.PortalExportSetVo;

/**
 * 导出文件工具类Dao
 *<pre>
 * ExportSetDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月19日下午5:48:21
 */
@Repository("fspExportSetDao")
public interface IExportSetDao extends IBaseDao<PortalExportSet, Integer> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过key查询导出文件设置
	 * </pre>
	 * @param key
	 * @return
	 * @return ExportSetDaoVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午4:18:29
	 */
	PortalExportSetVo findExportSetByKey(String key);
	
}
