package cn.uce.omg.portal.biz;

import cn.uce.omg.portal.vo.PortalExportSetVo;


/**
 * 导出文件工具类BIZ接口
 *<pre>
 * IExportSetBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月19日下午3:43:46
 */
public interface IExportSetBiz {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存导出文件设置
	 * </pre>
	 * @param exportSetVo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午3:49:35
	 */
	int addExportSet(PortalExportSetVo exportSetVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过key查询导出文件设置
	 * </pre>
	 * @param key
	 * @return
	 * @return exportSetVo
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午3:50:29
	 */
	PortalExportSetVo findExportSetByKey(String key);
	
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
	 * @date 2018年3月19日下午3:57:45
	 */
	int updateExportSet(PortalExportSetVo exportSetVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 获取前台传入数据，新增或更新数据
	 * </pre>
	 * @param request
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午5:22:06
	 */
	int addOrUpdateExportSet(String key,String value);
}
