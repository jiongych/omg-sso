package cn.uce.omg.sso.dao.portal;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.uce.core.db.IBaseDao;
import cn.uce.omg.sso.entity.portal.PortalHomePageInfo;
import cn.uce.omg.sso.vo.portal.PortalBrowserVo;
import cn.uce.omg.sso.vo.portal.PortalHomePageInfoVo;

/**
 * 各系统首页跳转信息DAO
 *<pre>
 * IHomePageInfoDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年4月2日上午11:13:41
 */
public interface IPortalHomePageInfoDao extends IBaseDao<PortalHomePageInfo, Long> {
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 查询各系统首页跳转信息
	 * </pre>
	 * @return
	 * @return List<PortalHomePageInfoVo>
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年4月2日下午3:50:32
	 */
	List<PortalHomePageInfoVo> findHomePageInfo();

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存浏览器信息
	 * </pre>
	 * @return
	 * @return List<PortalHomePageInfoVo>
	 */
	void saveBrowserInfo(PortalBrowserVo browserVo);

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存浏览器信息
	 * </pre>
	 * @return
	 * @return List<PortalHomePageInfoVo>
	 */
	void saveBatchBrowserInfo(@Param("list") List<Object> msg);
}
