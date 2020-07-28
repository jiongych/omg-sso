package cn.uce.omg.portal.biz.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.omg.portal.biz.IExportSetBiz;
import cn.uce.omg.portal.service.ExportSetService;
import cn.uce.omg.portal.vo.PortalExportSetVo;

/**
 * 导出文件工具类Biz
 *<pre>
 * ExportSetBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年3月19日下午4:21:52
 */
@Service(value = "fspExportSetBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class ExportSetBiz implements IExportSetBiz {

	@Resource
	private ExportSetService fspExportSetService;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存导出文件设置
	 * </pre>
	 * @param PortalExportSetVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午4:21:16
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int addExportSet(PortalExportSetVo exportSetVo) {
		return fspExportSetService.addExportSet(exportSetVo);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 通过key查询导出文件设置
	 * </pre>
	 * @param key
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午4:21:20
	 */
	@Override
	public PortalExportSetVo findExportSetByKey(String key) {
		return fspExportSetService.findExportSetByKey(key);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新导出文件设置
	 * </pre>
	 * @param PortalExportSetVo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午4:21:24
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int updateExportSet(PortalExportSetVo exportSetVo) {
		return fspExportSetService.updateExportSet(exportSetVo);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 获取前台传入数据，新增或更新数据
	 * </pre>
	 * @param request
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年3月19日下午5:23:17
	 */
	@Override
	public int addOrUpdateExportSet(String key,String value) {
		int result = 0;
		if (null != key && key.length() > 0) {
			//根据key查询数据库中是否己经存在
			PortalExportSetVo exportSetVo = findExportSetByKey(key);
			//如果存在，更新该数据
			if (null != exportSetVo) {
				exportSetVo.setValue(value);
				exportSetVo.setUpdateEmp("");//TODO
				exportSetVo.setUpdateOrg("");//TODO
				exportSetVo.setUpdateTime(new Date());
				result = updateExportSet(exportSetVo);
			} else {
				//如果不存在，保存该数据
				exportSetVo = new PortalExportSetVo();
				exportSetVo.setSetKey(key);
				exportSetVo.setValue(value);
				exportSetVo.setCreateEmp("");//TODO
				exportSetVo.setCreateOrg("");//TODE
				exportSetVo.setCreateTime(new Date());
				result = addExportSet(exportSetVo);
			}
		}
		return result;
	}

}
