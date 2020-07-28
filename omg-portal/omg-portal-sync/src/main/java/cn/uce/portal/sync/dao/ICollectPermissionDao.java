package cn.uce.portal.sync.dao;

import org.springframework.stereotype.Repository;

import cn.uce.core.sync.dao.IBaseSyncDao;
import cn.uce.portal.sync.entity.CollectPermission;

/**
 * Portal首页点击菜单搜集 DAO
 *<pre>
 * IPortalCollectPermissionDao
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月10日下午4:29:42
 */
@Repository("collectPermissionDao")
public interface ICollectPermissionDao extends IBaseSyncDao<CollectPermission, Long> {

}
