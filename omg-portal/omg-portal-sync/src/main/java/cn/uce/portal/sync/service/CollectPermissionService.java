package cn.uce.portal.sync.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.db.IBaseDao;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.dao.ICollectPermissionDao;
import cn.uce.portal.sync.entity.CollectPermission;
import cn.uce.portal.sync.vo.CollectPermissionVo;

/**
 * Portal首页点击菜单搜集 Service
 *<pre>
 * PortalCollectPermissionService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月10日下午4:29:56
 */
@Service("collectPermissionService")
public class CollectPermissionService extends AbstractReceivePushMsgListService<CollectPermissionVo, Integer, CollectPermission, Long> {

	@Resource
	private ICollectPermissionDao collectPermissionDao;

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 保存
	 * </pre>
	 * @param portalCollectPermission
	 * @return
	 * @return int
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月10日下午4:56:34
	 */
	public int saveInfo(CollectPermission collectPermission) {
		return collectPermissionDao.insert(collectPermission);
	}
	
	@Override
	public IBaseDao<CollectPermission, Long> getDao() {
		return collectPermissionDao;
	}

	@Override
	public Class<CollectPermission> getDbEntityClass() {
		return CollectPermission.class;
	}

	@Override
	public Class<CollectPermissionVo> getMsgEntityClass() {
		return CollectPermissionVo.class;
	}

	@Override
	public Long getDbPk(CollectPermission entity) {
		return entity.getId();
	}
	
}
