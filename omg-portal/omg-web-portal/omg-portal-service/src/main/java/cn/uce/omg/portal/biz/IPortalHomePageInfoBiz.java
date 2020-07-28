package cn.uce.omg.portal.biz;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.entity.PortalHomePageInfo;
import cn.uce.omg.portal.vo.PortalHomePageInfoVo;

/**
 * 各系统首页跳转信息BIZ
 *<pre>
 * IPortalHomePageInfoBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年4月2日下午1:59:44
 */
public interface IPortalHomePageInfoBiz {

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
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 新增各系统首页跳转信息
	 * </pre>
	 * @param portalHomePageInfo
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:01:28
	 */
	int insertHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo);
	
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
	 * @date 2018年4月2日下午2:01:40
	 */
	int updateHomePageInfo(PortalHomePageInfoVo portalHomePageInfoVo);
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 删除各系统首页跳转信息
	 * </pre>
	 * @param id
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午2:01:48
	 */
	int deleteHomePageInfo(Long id);
	
}
