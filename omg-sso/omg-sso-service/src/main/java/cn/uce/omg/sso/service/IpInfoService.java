/** 
 * @项目名称: FSP
 * @文件名称: IpInfoService 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.service;

import java.util.List;

import cn.uce.omg.sso.dao.IIpInfoDao;
import cn.uce.omg.sso.vo.IpInfoVo;

/**
 * ip信息操作
 * @author tanchong
 *
 */
public class IpInfoService {

	private IIpInfoDao ipInfoDao;

	/**
	 * 插入数据
	 * @param ipInfoVo ip对象
	 * @return
	 * @throws Exception
	 */
	public Integer insertIpInfo(IpInfoVo ipInfoVo) throws Exception {
		return ipInfoDao.insertIpInfo(ipInfoVo);
	}

	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	public List<IpInfoVo> findAllIpInfoList() throws Exception {
		return findIpInfoByCondition(new IpInfoVo());
	}

	/**
	 * 根据查询条件查询数据
	 * @param ipInfoVo
	 * @return
	 * @throws Exception
	 */
	public List<IpInfoVo> findIpInfoByCondition(IpInfoVo ipInfoVo) throws Exception {
		return ipInfoDao.findIpInfoByCondition(ipInfoVo);
	}
	/**
	 * @param ipInfoDao the ipInfoDao to set
	 */
	public void setIpInfoDao(IIpInfoDao ipInfoDao) {
		this.ipInfoDao = ipInfoDao;
	}
}
