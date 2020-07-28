/** 
 * @项目名称: FSP
 * @文件名称: ISystemInfoBiz 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.biz;

import java.util.List;

import cn.uce.omg.vo.SystemInfoVo;


/**
 * ISystemInfoBiz  
 * @Description: ISystemInfoBiz  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface ISystemInfoBiz {

	/**
	 * 通过查询条件查询系统信息
	 * @param systemCode
	 * @return
	 * @throws Exception
	 */
	List<SystemInfoVo> findByCondition(SystemInfoVo systemInfoVo) throws Exception;

	/**
	 * 通过系统编号获取系统信息
	 * @param systemCode
	 * @return
	 * @throws Exception
	 */
	SystemInfoVo findSystemBySystemCode(String systemCode) throws Exception;
	
}
