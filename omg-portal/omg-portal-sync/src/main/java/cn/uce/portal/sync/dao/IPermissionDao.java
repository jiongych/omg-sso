package cn.uce.portal.sync.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.sync.dao.IBaseSyncDao;
import cn.uce.portal.sync.entity.CollectPermission;
import cn.uce.portal.sync.entity.Permission;

/**
 * 查询菜单信息
 *<pre>
 * IPortalPermissionDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月11日上午10:56:50
 */
@Repository("permissionDao")
public interface IPermissionDao extends IBaseSyncDao<Permission, Long> {

	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 根据条件判断
	 * </pre>
	 * @param permissionName
	 * @param systemCode
	 * @return
	 * @return Permission
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月29日下午12:05:46
	 */
	Permission findByParam(CollectPermission portalCollectPermission);
}
