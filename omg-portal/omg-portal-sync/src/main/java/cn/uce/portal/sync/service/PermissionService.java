package cn.uce.portal.sync.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.core.db.IBaseDao;
import cn.uce.core.sync.service.AbstractReceivePushMsgListService;
import cn.uce.portal.sync.dao.IPermissionDao;
import cn.uce.portal.sync.entity.CollectPermission;
import cn.uce.portal.sync.entity.Permission;
import cn.uce.portal.sync.vo.PermissionVo;

/**
 * 查询菜单信息
 *<pre>
 * PortalPermissionService
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月11日上午10:48:24
 */
@Service("permissionService")
public class PermissionService extends AbstractReceivePushMsgListService<PermissionVo, Integer, Permission, Long> {

	@Resource
	private IPermissionDao permissionDao;
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据ID查询
	 * </pre>
	 * @param id
	 * @return
	 * @return PortalPermission
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月11日上午10:59:11
	 */
	public Permission findById(Long id) {
		return permissionDao.findById(id);
	}
	
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据条件判断查询
	 * </pre>
	 * @param permissionName
	 * @param systemCode
	 * @return
	 * @return Permission
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月29日下午12:06:19
	 */
	public Permission findByParam(CollectPermission portalCollectPermission) {
		return permissionDao.findByParam(portalCollectPermission);
	}
	
	@Override
	public IBaseDao<Permission, Long> getDao() {
		return permissionDao;
	}

	@Override
	public Class<Permission> getDbEntityClass() {
		return Permission.class;
	}

	@Override
	public Class<PermissionVo> getMsgEntityClass() {
		return PermissionVo.class;
	}

	@Override
	public Long getDbPk(Permission entity) {
		return entity.getId();
	}

}
