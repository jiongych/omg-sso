/** 
 * @项目名称: FSP
 * @文件名称: IIpInfoDao extends IBaseDao<IpInfo, Long> 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.dao;

import java.util.List;

import cn.uce.omg.sso.entity.IpInfo;
import cn.uce.omg.sso.vo.IpInfoVo;
import cn.uce.core.db.IBaseDao;

/**
 * IIpInfoDao extends IBaseDao<IpInfo, Long>  
 * @Description: IIpInfoDao extends IBaseDao<IpInfo, Long>  
 * @author automatic 
 * @date 2017年6月23日 下午1:02:26 
 * @version 1.0 
 */
public interface IIpInfoDao extends IBaseDao<IpInfo, Long> {

	/**
	 * 插入数据
	 * @param ipInfoVo ip对象
	 * @return
	 * @throws Exception
	 */
	Integer insertIpInfo(IpInfoVo ipInfoVo) throws Exception;

	/**
	 * 根据查询条件查询数据
	 * @param ipInfoVo
	 * @return
	 * @throws Exception
	 */
	List<IpInfoVo> findIpInfoByCondition(IpInfoVo ipInfoVo) throws Exception;
}
