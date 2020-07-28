/** 
 * @项目名称: FSP
 * @文件名称: ISystemInfoDao extends IBaseDao<SystemInfo, Long> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import java.util.List;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.SystemInfo;
import cn.uce.omg.vo.SystemInfoVo;

/**
 * ISystemInfoDao extends IBaseDao<SystemInfo, Long>  
 * @Description: ISystemInfoDao extends IBaseDao<SystemInfo, Long>  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface ISystemInfoDao extends IBaseDao<SystemInfo, Long> {

	/**
	 * 通过系统编号获取系统信息
	 * @param systemCode
	 * @return
	 * @throws ExceptionO
	 */
	public List<SystemInfoVo> findByCondition(SystemInfoVo systemInfoVo) throws Exception;

	/**
	 * 通过系统编号获取系统信息
	 * @param systemCode
	 * @return
	 * @throws Exception
	 */
	public SystemInfo findSystemBySystemCode(String systemCode) throws Exception;
}
