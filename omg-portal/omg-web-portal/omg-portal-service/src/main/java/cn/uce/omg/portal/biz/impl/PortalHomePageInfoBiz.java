package cn.uce.omg.portal.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.biz.IPortalHomePageInfoBiz;
import cn.uce.omg.portal.service.PortalHomePageInfoService;
import cn.uce.omg.portal.vo.PortalHomePageInfoVo;

/**
 * 各系统首页跳转信息BIZ实现类
 *<pre>
 * PortalHomePageInfoBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年4月2日下午2:02:29
 */
@Service(value = "fspPortalHomePageInfoBiz")
@Transactional(readOnly = true,propagation=Propagation.SUPPORTS)
public class PortalHomePageInfoBiz implements IPortalHomePageInfoBiz {

	@Resource
	private PortalHomePageInfoService fspPortalHomePageInfoService;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 分页查询各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfoVo
	 * @param page
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:04:13
	 */
	@Override
	public Pagination<PortalHomePageInfoVo> findHomeInfoByPage(PortalHomePageInfoVo portalHomePageInfoVo, Page page) {
		
		return fspPortalHomePageInfoService.findHomeInfoByPage(portalHomePageInfoVo, page);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 新增各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:04:45
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int insertHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo) {
		return fspPortalHomePageInfoService.insertHomePageInfo(portalHomePageInfoVo);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 更新各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfo
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:05:24
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int updateHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo) {
		return fspPortalHomePageInfoService.updateHomePageInfo(portalHomePageInfoVo);
	}

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID删除各系统首页跳转信息
	 * </pre>
	 * @param id
	 * @return
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:05:21
	 */
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	public int deleteHomePageInfo(Long id) {
		return fspPortalHomePageInfoService.deleteHomePageInfo(id);
	}

}
