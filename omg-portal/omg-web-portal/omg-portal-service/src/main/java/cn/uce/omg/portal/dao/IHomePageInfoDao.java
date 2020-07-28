package cn.uce.omg.portal.dao;

import org.springframework.stereotype.Repository;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.core.db.IBaseDao;
import cn.uce.omg.portal.entity.PortalHomePageInfo;
import cn.uce.omg.portal.vo.PortalHomePageInfoVo;

/**
 * 各系统首页跳转信息DAO
 *<pre>
 * IHomePageInfoDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年4月2日上午11:13:41
 */
@Repository("fspHomePageInfoDao")
public interface IHomePageInfoDao extends IBaseDao<PortalHomePageInfo, Long> {

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
	 * @date 2018年4月2日上午11:15:28
	 */
	Pagination<PortalHomePageInfoVo> findHomeInfoByPage(PortalHomePageInfoVo portalHomePageInfoVo, Page page);
}
