package cn.uce.portal.sync.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.sync.dao.IBaseSyncDao;
import cn.uce.portal.sync.entity.UctNoticeMustReadScope;

/**
 * 优速通公告必读Dao
 *<pre>
 * IUctNoticeMustReadScopeDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:41:30
 */
@Repository("uctNoticeMustReadScopeDao")
public interface IUctNoticeMustReadScopeDao extends IBaseSyncDao<UctNoticeMustReadScope, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询
	 * </pre>
	 * @param id
	 * @return
	 * @return UctNoticeMustReadScope
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:24:47
	 */
	UctNoticeMustReadScope findNoticeMustReadScopeById(Long id);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据noticeId更新
	 * </pre>
	 * @param UctNoticeMustReadScope
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:01:26
	 */
	int updateNoticeMustReadScopeById(UctNoticeMustReadScope uctNoticeMustReadScope);
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID删除
	 * </pre>
	 * @param id
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午4:21:23
	 */
	int deleteNoticeMustReadScopeById(Long id);
}
