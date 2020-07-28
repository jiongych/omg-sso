package cn.uce.omg.portal.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.dao.IHomePageInfoDao;
import cn.uce.omg.portal.entity.PortalHomePageInfo;
import cn.uce.omg.portal.vo.PortalHomePageInfoVo;
import cn.uce.web.common.util.ObjectConvertUtil;

/**
 * 各系统首页跳转信息Service
 *<pre>
 * PortalHomePageInfoService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年4月2日上午11:22:06
 */
@Service("fspPortalHomePageInfoService")
public class PortalHomePageInfoService {

	@Resource
	private IHomePageInfoDao fspHomePageInfoDao;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfoVo
	 * @param page
	 * @return
	 * @return Pagination<PortalHomePageInfoVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日上午11:21:53
	 */
	public Pagination<PortalHomePageInfoVo> findHomeInfoByPage(PortalHomePageInfoVo portalHomePageInfoVo, Page page) {
		return fspHomePageInfoDao.findHomeInfoByPage(portalHomePageInfoVo, page);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午1:47:01
	 */
	public int insertHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo) {
		PortalHomePageInfo portalHomePageInfo = ObjectConvertUtil.convertObject(portalHomePageInfoVo, PortalHomePageInfo.class);
		return fspHomePageInfoDao.insert(portalHomePageInfo);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午1:57:42
	 */
	public int updateHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo) {
		PortalHomePageInfo portalHomePageInfo = ObjectConvertUtil.convertObject(portalHomePageInfoVo, PortalHomePageInfo.class);
		return fspHomePageInfoDao.updateById(portalHomePageInfo);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID删除各系统首页跳转信息
	 * </pre>
	 * @param id
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午1:58:38
	 */
	public int deleteHomePageInfo(Long id) {
		return fspHomePageInfoDao.deleteById(id);
	}
}
