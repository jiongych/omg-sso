package cn.uce.portal.sync.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.db.IBaseDao;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.dao.IUctNoticeMustReadScopeDao;
import cn.uce.portal.sync.entity.UctNoticeMustReadScope;
import cn.uce.portal.sync.vo.UctNoticeMustReadScopeVo;

/**
 * 优速通公告必读范围Service
 *<pre>
 * UctNoticeMustReadScopeService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2018年7月24日下午3:41:09
 */
@Service("uctNoticeMustReadScopeService")
public class UctNoticeMustReadScopeService extends AbstractReceivePushMsgListService<UctNoticeMustReadScopeVo, Integer, UctNoticeMustReadScope, Long> {

	@Resource
	private IUctNoticeMustReadScopeDao uctNoticeMustReadScopeDao;
	
	public int saveNoticeMustReadScope(UctNoticeMustReadScope uctNoticeMustReadScope) {
		return uctNoticeMustReadScopeDao.insert(uctNoticeMustReadScope);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据noticeId更新
	 * </pre>
	 * @param uctNoticeMustReadScope
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2018年7月25日下午5:12:26
	 */
	public int updateNoticeMustReadScope(UctNoticeMustReadScope uctNoticeMustReadScope) {
		return uctNoticeMustReadScopeDao.updateNoticeMustReadScopeById(uctNoticeMustReadScope);
	}
	
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
	 * @date 2018年7月25日下午5:12:15
	 */
	public int deleteNoticeMustReadScope(Long id) {
		return uctNoticeMustReadScopeDao.deleteNoticeMustReadScopeById(id);
	}
	
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
	 * @date 2018年7月25日下午5:13:11
	 */
	public UctNoticeMustReadScope findNoticeMustReadScopeById(Long id) {
		return uctNoticeMustReadScopeDao.findNoticeMustReadScopeById(id);
	}
	
	@Override
	public IBaseDao<UctNoticeMustReadScope, Long> getDao() {
		return uctNoticeMustReadScopeDao;
	}

	@Override
	public Class<UctNoticeMustReadScope> getDbEntityClass() {
		return UctNoticeMustReadScope.class;
	}

	@Override
	public Class<UctNoticeMustReadScopeVo> getMsgEntityClass() {
		return UctNoticeMustReadScopeVo.class;
	}

	@Override
	public Long getDbPk(UctNoticeMustReadScope entity) {
		return entity.getNoticeId();
	}

}
