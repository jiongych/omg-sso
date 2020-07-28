/** 
 * @项目名称: FSP
 * @文件名称: SystemInfoService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.omg.sso.dao.ISystemInfoDao;
import cn.uce.omg.sso.entity.SystemInfo;
import cn.uce.omg.vo.SystemInfoVo;

/**
 * 系统维护service
 * @author tanchong
 *
 */
public class SystemInfoService {

	private ISystemInfoDao systemInfoDao;

	/**
	 * 通过系统编号获取系统信息
	 * @param systemCode
	 * @return
	 * @throws Exception
	 */
	public List<SystemInfoVo> findByCondition(SystemInfoVo systemInfoVo) throws Exception {
		return systemInfoDao.findByCondition(systemInfoVo);
	}

	/**
	 * 通过系统编号获取系统信息
	 * @param systemCode
	 * @return
	 * @throws Exception
	 */
	public SystemInfo findSystemBySystemCode(String systemCode) throws Exception {
		SystemInfo systemInfo = systemInfoDao.findSystemBySystemCode(systemCode);
		return systemInfo;
	}

	public void setSystemInfoDao(ISystemInfoDao systemInfoDao) {
		this.systemInfoDao = systemInfoDao;
	}

}
