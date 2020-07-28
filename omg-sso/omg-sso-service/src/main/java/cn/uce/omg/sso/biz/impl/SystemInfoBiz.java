/** 
 * @项目名称: FSP
 * @文件名称: SystemInfoBiz implements ISystemInfoBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz.impl;

import java.util.List;

import cn.uce.omg.sso.biz.ISystemInfoBiz;
import cn.uce.omg.sso.entity.SystemInfo;
import cn.uce.omg.sso.service.SystemInfoService;
import cn.uce.omg.sso.util.ObjectConvertUtil;
import cn.uce.omg.vo.SystemInfoVo;

/**
 * 系统信息BIZ实现类
 * @author huangting
 * @date 2017年6月9日 下午4:11:33
 */
public class SystemInfoBiz implements ISystemInfoBiz {

	private SystemInfoService systemInfoService;

	/**
	 * 通过查询条件查询系统信息
	 * @param systemCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SystemInfoVo> findByCondition(SystemInfoVo systemInfoVo) throws Exception {
		return systemInfoService.findByCondition(systemInfoVo);
	}

	/**
	 * 通过系统编号获取系统信息
	 * @param systemCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public SystemInfoVo findSystemBySystemCode(String systemCode) throws Exception {
		SystemInfo systemInfo = systemInfoService.findSystemBySystemCode(systemCode);
		if (systemInfo != null) {
			SystemInfoVo systemInfoVo = ObjectConvertUtil.convertObject(systemInfo, SystemInfoVo.class);
			return systemInfoVo;
		}
		return null;
	}


	public SystemInfoService getSystemInfoService() {
		return systemInfoService;
	}

	public void setSystemInfoService(SystemInfoService systemInfoService) {
		this.systemInfoService = systemInfoService;
	}


}
